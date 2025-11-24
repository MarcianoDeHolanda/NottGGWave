package com.nottabaker.nottggwave.managers;

import com.nottabaker.nottggwave.NottGGWave;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GGWaveManager {

    private final NottGGWave plugin;
    private final Set<UUID> participants;
    private boolean waveActive;
    private BukkitTask waveTask;
    private int formatIndex;
    
    // Precomputed messages for optimization
    private Component startMessage;
    private Component endMessage;
    private List<Component> precomputedGGFormats;

    public GGWaveManager(NottGGWave plugin) {
        this.plugin = plugin;
        this.participants = new HashSet<>();
        this.waveActive = false;
        this.formatIndex = 0;
        precomputeMessages();
    }

    private void precomputeMessages() {
        // Precompute start message
        String startMsg = plugin.getConfig().getString("ggwave.start-message", 
            "<green>Empezó el GG Wave, pon GG en el chat para ganar recompensas!");
        this.startMessage = plugin.getMiniMessage().deserialize(startMsg);
        
        // Precompute end message template
        this.endMessage = plugin.getMiniMessage().deserialize(
            "<yellow>La GG Wave ha terminado. ¡Gracias a todos los %participants% participantes!");
        
        // Precompute GG formats
        List<String> ggFormats = plugin.getConfig().getStringList("ggwave.gg-formats");
        if (ggFormats.isEmpty()) {
            ggFormats = Arrays.asList("<gradient:red:yellow><b>%player_name% » GG!</gradient>");
        }
        
        this.precomputedGGFormats = new ArrayList<>();
        for (String format : ggFormats) {
            // Create template with placeholder
            String template = format.replace("%player_name%", "%player_name%");
            this.precomputedGGFormats.add(plugin.getMiniMessage().deserialize(template));
        }
    }

    public void startWave() {
        if (waveActive) {
            return;
        }

        waveActive = true;
        participants.clear();
        formatIndex = 0; // Resetear índice al iniciar wave

        // Enviar mensaje de inicio (precomputed)
        Bukkit.broadcast(startMessage);

        // Reproducir sonido de inicio
        String startSound = plugin.getConfig().getString("ggwave.start-sound", "block.note_block.harp");
        if (!startSound.isEmpty()) {
            try {
                Sound sound = matchSound(startSound);
                if (sound != null) {
                    float volume = (float) plugin.getConfig().getDouble("ggwave.start-sound-volume", 1.0);
                    float pitch = (float) plugin.getConfig().getDouble("ggwave.start-sound-pitch", 1.0);
                    Bukkit.getOnlinePlayers().forEach(player -> 
                        player.playSound(player.getLocation(), sound, volume, pitch));
                } else {
                    plugin.getLogger().warning("Sonido de inicio no válido: " + startSound);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Error al reproducir sonido de inicio: " + startSound);
            }
        }

        // Programar el fin de la wave
        int duration = plugin.getConfig().getInt("ggwave.duration", 30);
        waveTask = Bukkit.getScheduler().runTaskLater(plugin, this::stopWave, duration * 20L);

        plugin.getLogger().info("GG Wave iniciada por " + duration + " segundos");
    }

    public void stopWave() {
        if (!waveActive) {
            return;
        }

        waveActive = false;
        
        if (waveTask != null) {
            waveTask.cancel();
            waveTask = null;
        }

        // Enviar mensaje de fin (precomputed with dynamic participant count)
        String endText = ((TextComponent) endMessage).content()
            .replace("%participants%", String.valueOf(participants.size()));
        Component finalEndMessage = Component.text(endText, endMessage.color());
        Bukkit.broadcast(finalEndMessage);

        plugin.getLogger().info("GG Wave finalizada. Participantes: " + participants.size());
    }

    public boolean isWaveActive() {
        return waveActive;
    }

    public boolean hasPlayerAlreadyParticipated(UUID playerUUID) {
        return participants.contains(playerUUID);
    }

    public void addPlayerToParticipants(UUID playerUUID) {
        participants.add(playerUUID);
    }

    public void applyEffects(Player player) {
        // Aplicar sonido
        String soundName = plugin.getConfig().getString("ggwave.effects.sound", "");
        if (!soundName.isEmpty()) {
            try {
                Sound sound = matchSound(soundName);
                if (sound != null) {
                    float volume = (float) plugin.getConfig().getDouble("ggwave.effects.sound-volume", 1.0);
                    float pitch = (float) plugin.getConfig().getDouble("ggwave.effects.sound-pitch", 1.0);
                    player.playSound(player.getLocation(), sound, volume, pitch);
                } else {
                    plugin.getLogger().warning("Sonido no válido: " + soundName);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Error al reproducir sonido: " + soundName);
            }
        }

        // Aplicar partículas
        String particleName = plugin.getConfig().getString("ggwave.effects.particle", "");
        if (!particleName.isEmpty()) {
            try {
                Particle particle = Particle.valueOf(particleName.toUpperCase());
                int count = plugin.getConfig().getInt("ggwave.effects.particle-count", 10);
                player.spawnParticle(particle, player.getLocation().add(0, 1, 0), count, 0.5, 0.5, 0.5, 0.1);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Partícula no válida: " + particleName);
            }
        }

        // Enviar mensaje personalizado al jugador
        String playerMessage = plugin.getConfig().getString("ggwave.effects.player-message", "");
        if (!playerMessage.isEmpty()) {
            player.sendMessage(plugin.getMiniMessage().deserialize(playerMessage));
        }

        // Ejecutar comandos de recompensa en el thread principal
        List<String> rewardCommands = plugin.getConfig().getStringList("rewards.commands");
        if (!rewardCommands.isEmpty()) {
            for (String command : rewardCommands) {
                String formattedCommand = command.replace("%player_name%", player.getName());
                String finalFormattedCommand = formattedCommand;
                Bukkit.getScheduler().runTask(plugin, () -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalFormattedCommand);
                });
            }
        }
    }

    public int getParticipantCount() {
        return participants.size();
    }

    public Set<UUID> getParticipants() {
        return new HashSet<>(participants);
    }

    public int getNextFormatIndex() {
        return formatIndex++; // Incrementar y devolver el valor actual
    }

    public Component getOptimizedGGMessage(String playerName) {
        if (precomputedGGFormats.isEmpty()) {
            return Component.text(playerName + " » GG!");
        }
        
        int index = getNextFormatIndex();
        Component template = precomputedGGFormats.get(index % precomputedGGFormats.size());
        
        // Use replaceText to maintain gradient formatting
        return template.replaceText(builder -> 
            builder.matchLiteral("%player_name%").replacement(playerName));
    }

    public void reloadMessages() {
        precomputeMessages();
    }

    private Sound matchSound(String soundName) {
        // Intentar con el nombre exacto primero
        try {
            return Sound.valueOf(soundName.toUpperCase().replace(".", "_"));
        } catch (IllegalArgumentException e) {
            // Si no funciona, intentar con algunos sonidos comunes
            switch (soundName.toLowerCase().replace(".", "_")) {
                case "block_note_block_pling":
                    return Sound.valueOf("BLOCK_NOTE_BLOCK_PLING");
                case "block_note_block_harp":
                    return Sound.valueOf("BLOCK_NOTE_BLOCK_HARP");
                case "block_note_block_bass":
                    return Sound.valueOf("BLOCK_NOTE_BLOCK_BASS");
                case "ui_button_click":
                    return Sound.valueOf("UI_BUTTON_CLICK");
                case "entity_experience_orb_pickup":
                    return Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP");
                default:
                    return null;
            }
        }
    }
}
