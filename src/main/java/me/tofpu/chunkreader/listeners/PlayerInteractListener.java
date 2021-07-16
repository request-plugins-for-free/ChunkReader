package me.tofpu.chunkreader.listeners;

import me.tofpu.chunkreader.ChatUtils;
import me.tofpu.chunkreader.ChunkReader;
import me.tofpu.chunkreader.config.CachedConfig;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event){
        final ItemStack item = event.getItem();
        if (item == null || (item.getType().isBlock() || !event.getAction().name().contains("AIR"))) return;

        final Material material = item.getType();
        if (!CachedConfig.MATERIALS.contains(material)) return;
        ChunkReader.getChunkReader().getServer().broadcast(
                ChatUtils.colorizeWithPrefix(CachedConfig.detectedItemMessage
                        .replace("%item%", item.getType() + "")
                        .replace("%player%", event.getPlayer().getName())), "chunkreader.detected");

        event.getPlayer().sendMessage(ChatUtils.colorize(
                CachedConfig
                        .notAllowedItemMessage
                        .replace("%item%", WordUtils.capitalize(material.name()
                                .replace("_", " ").toLowerCase()))
        ));

        event.setCancelled(true);
        item.setType(Material.DEAD_BUSH);
    }
}
