package ganymedes01.etfuturum.configuration.configs;

import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigSounds extends ConfigBase {

    public static boolean newCombatSounds;

    static final String catPlayer = "players";
    static final String catBlocksItems = "blocks and items";
    static final String catEntity = "entities";
    static final String catMisc = "misc";
    public static final String PATH = ConfigBase.configDir + File.separator + "sounds.cfg";
    public static final ConfigSounds configInstance = new ConfigSounds(new File(Launch.minecraftHome, PATH));

    public ConfigSounds(File file) {
        super(file);
        setCategoryComment(catBlocksItems, "Sounds for blocks and items.");
        setCategoryComment(catBlocksItems, "Sounds for blocks and items.");
        setCategoryComment(catEntity, "Sounds for entities.");
        setCategoryComment(catMisc, "Sounds that don't fit in any other category.");

        configCats.add(getCategory(catMisc));
    }

    @Override
    protected void syncConfigOptions() {
        Configuration cfg = configInstance;
        newCombatSounds = cfg.getBoolean("newCombatSounds", catPlayer, true, "New sounds for player attacking.");
    }
}
