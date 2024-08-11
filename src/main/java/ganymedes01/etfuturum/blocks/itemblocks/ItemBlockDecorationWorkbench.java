package ganymedes01.etfuturum.blocks.itemblocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBlockDecorationWorkbench extends ItemBlock {

	public ItemBlockDecorationWorkbench(Block p_i45328_1_) {
		super(p_i45328_1_);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> lore, boolean f3h) {
		lore.add("\u00a7o" + StatCollector.translateToLocal("efr.decoration.only"));
	}

}
