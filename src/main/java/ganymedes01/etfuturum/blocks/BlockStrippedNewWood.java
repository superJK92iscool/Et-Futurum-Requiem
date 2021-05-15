package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockGeneric;
import net.minecraft.block.BlockNewLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

public class BlockStrippedNewWood extends BlockNewLog implements IConfigurable, ISubBlocksBlock {
	
	public static final String[] field_150169_M = new String[] {"acacia", "dark_oak"};
	
	public BlockStrippedNewWood() {
		setBlockName(Utils.getUnlocalisedName("wood2_stripped"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.field_150167_a = new IIcon[field_150169_M.length];
		this.field_150166_b = new IIcon[field_150169_M.length];

		for (int i = 0; i < this.field_150167_a.length; ++i) {
			this.field_150167_a[i] = iconRegister.registerIcon("stripped_" + field_150169_M[i] + "_log");
			this.field_150166_b[i] = iconRegister.registerIcon("stripped_" + field_150169_M[i] + "_log");
		}
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableStrippedLogs && ConfigurationHandler.enableBarkLogs;
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

}
