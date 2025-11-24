package com.nottabaker.nottggwave;

import com.nottabaker.nottggwave.commands.GGWaveCommand;
import com.nottabaker.nottggwave.listeners.ChatListener;
import com.nottabaker.nottggwave.managers.GGWaveManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public final class NottGGWave extends JavaPlugin {

    private GGWaveManager ggWaveManager;
    private MiniMessage miniMessage;

    @Override
    public void onEnable() {
        // Inicializar MiniMessage
        this.miniMessage = MiniMessage.miniMessage();
        
        // Guardar configuración por defecto
        saveDefaultConfig();
        
        // Inicializar manager
        this.ggWaveManager = new GGWaveManager(this);
        
        // Registrar comandos
        getCommand("nottggwave").setExecutor(new GGWaveCommand(this, ggWaveManager));
        
        // Registrar eventos
        getServer().getPluginManager().registerEvents(new ChatListener(this, ggWaveManager), this);
        
        getLogger().info("NottGGWave se ha habilitado correctamente!");
    }

    @Override
    public void onDisable() {
        if (ggWaveManager != null) {
            ggWaveManager.stopWave();
        }
        getLogger().info("NottGGWave se ha deshabilitado correctamente!");
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public GGWaveManager getGGWaveManager() {
        return ggWaveManager;
    }
}
