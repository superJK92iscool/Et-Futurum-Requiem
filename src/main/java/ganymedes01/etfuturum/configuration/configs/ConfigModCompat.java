package ganymedes01.etfuturum.configuration.configs;

import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigModCompat extends ConfigBase {

    public static int elytraBaublesExpandedCompat;

    static final String catPlayer = "players";
    static final String catBlocksItems = "blocks and items";
    static final String catEntity = "entities";
    static final String catMisc = "misc";
    static final String catAmbient = "ambient";
    public static final String PATH = ConfigBase.configDir + File.separator + "modcompat.cfg";
    public static final ConfigModCompat configInstance = new ConfigModCompat(new File(Launch.minecraftHome, PATH));

    public ConfigModCompat(File file) {
        super(file);
        setCategoryComment(catMisc, "Sounds that players make.");

        configCats.add(getCategory(catPlayer));
    }


    //TODO: Move Iron Chest checks here
    @Override
    protected void syncConfigOptions() {
        Configuration cfg = configInstance;
        elytraBaublesExpandedCompat = cfg.getInt("elytraBaublesExpandedCompat", catMisc, 1, 0, 2, "Adds compat for Baubles Expanded. Does nothing for standard baubles, this REQUIRES Baubles Expanded! It uses the new \"wings\" slot added by the expanded version. https://modrinth.com/mod/baubles-expanded\nWhen enabled, this allows the player to equip an elytra with a chestplate, by placing the elytra in a \"wings\" slot instead of the chestplate slot. Note that the player can only equip one elytra at a time.\n0 = No compat, do not allow the elytra to be placed in a wings slot.\n1 = Elytra will be placeable in a wings slot. Will enable the slot, if it isn't there.\n2 = The elytra can ONLY go in the wings slots, not the chestplate slot.");
    }
}
