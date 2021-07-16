package me.tofpu.chunkreader.runnable;

import me.tofpu.chunkreader.ChatUtils;
import me.tofpu.chunkreader.ChunkReader;
import me.tofpu.chunkreader.config.CachedConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ChunkRunnable implements Runnable {

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        for (Player player : ChunkReader.getChunkReader().getServer().getOnlinePlayers()){
            final Location playerLocation = player.getLocation().clone();
            final double playerX = playerLocation.getX() - ((double) CachedConfig.player_radius / 2);
            final double playerY = playerLocation.getY() - ((double) CachedConfig.player_radius / 2);
            final double playerZ = playerLocation.getZ() - ((double) CachedConfig.player_radius / 2);

            for (double x = playerX; x <= playerX + CachedConfig.player_radius; x++){
                for (double y = playerY; y <= playerY + CachedConfig.player_radius; y++){
                    for (double z = playerX; z <= playerZ + CachedConfig.player_radius; z++) {
                        playerLocation.setX(x);
                        playerLocation.setY(y);
                        playerLocation.setZ(z);
                        final Block block = playerLocation.getBlock();
                        if (CachedConfig.MATERIALS.contains(block.getType())) {
                            ChunkReader.getChunkReader().getServer().broadcast(
                                    ChatUtils.colorizeWithPrefix(CachedConfig
                                            .detectedBlockMessage
                                            .replace("%x%", String.format("%.2f", x))
                                            .replace("%y%", String.format("%.2f", y))
                                            .replace("%z%", String.format("%.2f", z))
                                            .replace("%block%", block.getType() + "")
                                            .replace("%player%", player.getName())), "chunkreader.detected");
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
