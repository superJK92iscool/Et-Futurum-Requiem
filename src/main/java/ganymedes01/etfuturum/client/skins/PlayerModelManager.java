package ganymedes01.etfuturum.client.skins;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

@SideOnly(Side.CLIENT)
public class PlayerModelManager {

	public static final String MODEL_KEY = Reference.MOD_ID + "_model";
	
	public static Map<UUID, Boolean> alexCache = new HashMap<UUID, Boolean>();

	public static boolean isPlayerModelAlex(EntityPlayer player) {
		if(player == null || player.getUniqueID() == null)
			return false;
//      alexCache.clear();
		Boolean isAlex = alexCache.get(player.getUniqueID());
		if(isAlex == null) {
//          System.out.println("test");
			NBTTagCompound nbt = player.getEntityData();
			if(nbt.hasKey(MODEL_KEY, Constants.NBT.TAG_BYTE))
				nbt.removeTag(MODEL_KEY);
			ThreadCheckAlex skinthread = new ThreadCheckAlex();
			skinthread.startWithArgs(player.getUniqueID());
			isAlex = false;
		}
		return isAlex;
	}
}