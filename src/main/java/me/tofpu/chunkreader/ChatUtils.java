package me.tofpu.chunkreader;

import me.tofpu.chunkreader.config.CachedConfig;
import org.bukkit.ChatColor;

public class ChatUtils {
    public static String colorize(String content){
        return ChatColor.translateAlternateColorCodes('&', content);
    }

    public static String colorizeWithPrefix(String content){
        return colorize(CachedConfig.prefix + content);
    }
}
