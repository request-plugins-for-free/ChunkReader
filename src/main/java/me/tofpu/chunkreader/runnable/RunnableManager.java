package me.tofpu.chunkreader.runnable;

import me.tofpu.chunkreader.ChunkReader;
import me.tofpu.chunkreader.config.CachedConfig;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class RunnableManager {
    private final ChunkRunnable chunkRunnable;
    private BukkitTask task;

    public RunnableManager() {
        this.chunkRunnable = new ChunkRunnable();
    }

    public void start(){
        if (task != null && !task.isCancelled()){
            ChunkReader.getChunkReader().getLogger().info("Cancelled the running task due to reloading. creating a new task now!");
            task.cancel();
        }
        this.task = Bukkit.getScheduler().runTaskTimer(ChunkReader.getChunkReader(), chunkRunnable, 0, CachedConfig.interval_ticks);
    }

    public void cancel(){
        if (task != null && !task.isCancelled()) task.cancel();
        ChunkReader.getChunkReader().getLogger().info("Stopped the running task!");
    }

    public BukkitTask getTask() {
        return task;
    }
}
