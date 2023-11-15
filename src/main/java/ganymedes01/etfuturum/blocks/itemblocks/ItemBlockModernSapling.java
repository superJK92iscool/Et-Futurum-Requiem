package ganymedes01.etfuturum.blocks.itemblocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ItemBlockModernSapling extends BaseItemBlock {

	private IIcon propaguleInvIcon;

	public ItemBlockModernSapling(Block block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		propaguleInvIcon = p_94581_1_.registerIcon("mangrove_propagule");
	}

	/**
	 * Gets an icon index based on an item's damage value
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int p_77617_1_) {
		if (p_77617_1_ % 8 == 0) {
			return propaguleInvIcon;
		}
		return super.getIconFromDamage(p_77617_1_);
	}
}
