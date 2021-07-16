package me.tofpu.chunkreader.config;

import me.tofpu.chunkreader.ChunkReader;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class CachedConfig {
    private final static String SETTINGS = "settings.";
    private final static String BLACKLIST = "blacklist.";
    private final static String MESSAGES = "messages.";

    public static int player_radius;
    public static int interval;
    public static int interval_ticks;

    public static boolean disable;
    public final static List<Material> MATERIALS = new ArrayList<>();

    public static String prefix;
    public static String detectedBlockMessage;
    public static String detectedItemMessage;
    public static String notAllowedBlockMessage;
    public static String notAllowedItemMessage;

    public static void initialize(){
        final FileConfiguration configuration = ChunkReader.getChunkReader().getConfig();

        player_radius = configuration.getInt(SETTINGS + "player-radius", 10);
        interval = configuration.getInt(SETTINGS + "interval", 2);
        interval_ticks = 20 * interval;

        disable = configuration.getBoolean(BLACKLIST + "disable", false);
        for (String value : configuration.getStringList(BLACKLIST + "materials")){
            final Material material = Material.matchMaterial(value);
            if (material == null){
                ChunkReader.getChunkReader().getLogger().warning(String.format("Material %s does not exist, check again!", value));
                continue;
            }
            MATERIALS.add(material);
        }

        prefix = configuration.getString(MESSAGES + "prefix");
        detectedBlockMessage = configuration.getString(MESSAGES + "detected-block", "Found an illegal block&4(%block%) &cat &4%x%&c, &4%y%&c, &4%z% &cthrough &4%player%&c!");
        detectedItemMessage = configuration.getString(MESSAGES + "detected-item", "Found an illegal item&4(%item%) &cthrough &4%player% &cinventory!");

        notAllowedBlockMessage = configuration.getString(MESSAGES + "not-allowed-block", "&cYou're not allowed to place an illegal block.");
        notAllowedItemMessage = configuration.getString(MESSAGES + "not-allowed-item", "&cYou're not allowed to use an illegal item.");
    }

    public static void reload(){
        MATERIALS.clear();
        initialize();
    }
}
