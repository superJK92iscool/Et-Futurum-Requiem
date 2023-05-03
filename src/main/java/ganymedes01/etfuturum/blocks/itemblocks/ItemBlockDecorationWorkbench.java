package ganymedes01.etfuturum.blocks.itemblocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockDecorationWorkbench extends ItemBlock {

	public ItemBlockDecorationWorkbench(Block p_i45328_1_) {
		super(p_i45328_1_);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List lore, boolean f3h) {
		lore.add("\u00a7oDecoration only.");
	}

}
