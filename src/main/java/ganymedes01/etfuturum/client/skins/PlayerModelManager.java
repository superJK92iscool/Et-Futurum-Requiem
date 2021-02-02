package ganymedes01.etfuturum.client.skins;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

@SideOnly(Side.CLIENT)
public class PlayerModelManager {

    public static final String MODEL_KEY = Reference.MOD_ID + "_model";
    
    public static Map<EntityPlayer, Boolean> alexCache = new HashMap<EntityPlayer, Boolean>();

    public static boolean isPlayerModelAlex(EntityPlayer player) {
        Boolean isAlex = alexCache.get(player);
		NBTTagCompound nbt = player.getEntityData();
		if(isAlex == null){
			if(nbt.hasKey(MODEL_KEY, Constants.NBT.TAG_BYTE))
				nbt.removeTag(MODEL_KEY);
			ThreadCheckAlex skinthread = new ThreadCheckAlex();
			skinthread.startWithArgs(player);
			isAlex = false;
		}
        return isAlex;
    }
}