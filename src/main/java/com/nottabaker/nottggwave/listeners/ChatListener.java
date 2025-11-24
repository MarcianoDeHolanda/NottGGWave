package com.nottabaker.nottggwave.listeners;

import com.nottabaker.nottggwave.NottGGWave;
import com.nottabaker.nottggwave.managers.GGWaveManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
    public void onPlayerChat(AsyncPlayerChatEvent event) {
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
        
        // Usar mensaje optimizado precomputado
        Component optimizedMessage = ggWaveManager.getOptimizedGGMessage(playerName);
        
        // Cancelar el evento original para enviar nuestro mensaje personalizado
        event.setCancelled(true);
        
        // Enviar el mensaje optimizado a todos los jugadores
        plugin.getServer().broadcast(optimizedMessage);
        
        // Aplicar efectos y recompensas solo la primera vez
        if (isFirstTime) {
            ggWaveManager.applyEffects(player);
        }
    }
}
