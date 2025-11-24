package com.nottabaker.nottggwave.listeners;

import com.nottabaker.nottggwave.NottGGWave;
import com.nottabaker.nottggwave.managers.GGWaveManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class ChatListener implements Listener {

    private final NottGGWave plugin;
    private final GGWaveManager ggWaveManager;

    public ChatListener(NottGGWave plugin, GGWaveManager ggWaveManager) {
        this.plugin = plugin;
        this.ggWaveManager = ggWaveManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncChatEvent event) {
        if (!ggWaveManager.isWaveActive()) {
            return;
        }

        Player player = event.getPlayer();
        String playerName = player.getName();
        boolean isFirstTime = !ggWaveManager.hasPlayerAlreadyParticipated(player.getUniqueId());
        
        // Si es la primera vez, añadir a participantes y aplicar recompensas
        if (isFirstTime) {
            ggWaveManager.addPlayerToParticipants(player.getUniqueId());
        }
        
        // Transformar el mensaje al formato GG (siempre)
        List<String> ggFormats = plugin.getConfig().getStringList("ggwave.gg-formats");
        if (ggFormats.isEmpty()) {
            // Fallback a formato único si la lista está vacía
            ggFormats = Arrays.asList("<gradient:red:yellow><b>%player_name% » GG!</gradient>");
        }
        
        // Obtener el siguiente formato cíclicamente
        int formatIndex = ggWaveManager.getNextFormatIndex();
        String ggFormat = ggFormats.get(formatIndex % ggFormats.size());
        String formattedMessage = ggFormat.replace("%player_name%", playerName);
        
        // Cancelar el evento original para enviar nuestro mensaje personalizado
        event.setCancelled(true);
        
        // Enviar el mensaje personalizado a todos los jugadores
        plugin.getServer().broadcast(plugin.getMiniMessage().deserialize(formattedMessage));
        
        // Aplicar efectos y recompensas solo la primera vez
        if (isFirstTime) {
            ggWaveManager.applyEffects(player);
        }
    }
}
