package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockGeneric;
import net.minecraft.block.BlockOldLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

public class BlockStrippedOldLog extends BlockOldLog implements IConfigurable, ISubBlocksBlock {

	public BlockStrippedOldLog() {
		setBlockName(Utils.getUnlocalisedName("log_stripped"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.field_150167_a = new IIcon[field_150168_M.length];
		this.field_150166_b = new IIcon[field_150168_M.length];

		for (int i = 0; i < this.field_150167_a.length; ++i) {
			this.field_150167_a[i] = p_149651_1_.registerIcon("stripped_" + field_150168_M[i] + "_log");
			this.field_150166_b[i] = p_149651_1_.registerIcon("stripped_" + field_150168_M[i] + "_log" + "_top");
		}
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableStrippedLogs;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

}
