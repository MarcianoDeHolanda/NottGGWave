package com.nottabaker.nottggwave.commands;

import com.nottabaker.nottggwave.NottGGWave;
import com.nottabaker.nottggwave.managers.GGWaveManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class GGWaveCommand implements CommandExecutor, TabCompleter {

    private final NottGGWave plugin;
    private final GGWaveManager ggWaveManager;

    public GGWaveCommand(NottGGWave plugin, GGWaveManager ggWaveManager) {
        this.plugin = plugin;
        this.ggWaveManager = ggWaveManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission("nottggwave.admin")) {
            sender.sendMessage(plugin.getMiniMessage().deserialize("<red>No tienes permiso para usar este comando."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getMiniMessage().deserialize("<yellow>Uso: /nottggwave <start|reload>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            if (ggWaveManager.isWaveActive()) {
                sender.sendMessage(plugin.getMiniMessage().deserialize("<red>Ya hay una GG Wave activa."));
                return true;
            }

            ggWaveManager.startWave();
            sender.sendMessage(plugin.getMiniMessage().deserialize("<green>GG Wave iniciada correctamente."));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(plugin.getMiniMessage().deserialize("<green>Configuración recargada correctamente."));
            plugin.getLogger().info("Configuración recargada por " + sender.getName());
            return true;
        }

        sender.sendMessage(plugin.getMiniMessage().deserialize("<red>Subcomando desconocido. Usa /nottggwave <start|reload>"));
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (!sender.hasPermission("nottggwave.admin")) {
            return Arrays.asList();
        }

        if (args.length == 1) {
            return Arrays.asList("start", "reload");
        }

        return Arrays.asList();
    }
}
