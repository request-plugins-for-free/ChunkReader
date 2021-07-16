package me.tofpu.chunkreader.listeners;

import me.tofpu.chunkreader.ChatUtils;
import me.tofpu.chunkreader.ChunkReader;
import me.tofpu.chunkreader.config.CachedConfig;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onBlockPlace(BlockPlaceEvent event) {
        final Block block = event.getBlock();
        final Material material = block.getType();

        if (!CachedConfig.MATERIALS.contains(material)) return;
        ChunkReader.getChunkReader().getServer().broadcast(
                ChatUtils.colorizeWithPrefix(CachedConfig
                        .detectedBlockMessage
                        .replace("%x%", String.format("%.2f", block.getLocation().getX()))
                        .replace("%y%", String.format("%.2f", block.getLocation().getY()))
                        .replace("%z%", String.format("%.2f", block.getLocation().getZ()))
                        .replace("%block%", material + "")
                        .replace("%player%", event.getPlayer().getName())), "chunkreader.detected");

        event.getPlayer().sendMessage(ChatUtils.colorize(
                CachedConfig
                        .notAllowedBlockMessage
                        .replace("%type%", WordUtils.capitalize(material.name()
                                .replace("_", " ").toLowerCase()))
        ));
        event.setCancelled(true);
    }
}
