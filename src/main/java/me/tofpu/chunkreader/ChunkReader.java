package me.tofpu.chunkreader;

import me.tofpu.chunkreader.config.CachedConfig;
import me.tofpu.chunkreader.listeners.BlockPlaceListener;
import me.tofpu.chunkreader.listeners.PlayerInteractListener;
import me.tofpu.chunkreader.runnable.RunnableManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChunkReader extends JavaPlugin {
    private static ChunkReader CHUNK_READER;

    public static ChunkReader getChunkReader() {
        return CHUNK_READER;
    }

    private RunnableManager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        CHUNK_READER = this;

        saveDefaultConfig();
        CachedConfig.initialize();

        this.manager = new RunnableManager();
        this.manager.start();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.manager.cancel();
    }

    // TODO: Create a command that reloads the config.
    public void reload(){
        CachedConfig.reload();
    }

    public RunnableManager getManager() {
        return manager;
    }
}
