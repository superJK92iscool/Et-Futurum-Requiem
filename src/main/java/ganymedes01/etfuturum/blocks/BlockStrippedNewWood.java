package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.blocks.itemblocks.ItemBlockGeneric;
import net.minecraft.block.BlockNewLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

public class BlockStrippedNewWood extends BlockNewLog implements IConfigurable, ISubBlocksBlock {
	
	public static final String[] icon_names_stripped = new String[] {"acacia", "dark_oak"};
	
	public BlockStrippedNewWood() {
		setBlockName(Utils.getUnlocalisedName("wood2_stripped"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.field_150167_a = new IIcon[icon_names_stripped.length];
		this.field_150166_b = new IIcon[icon_names_stripped.length];

		for (int i = 0; i < this.field_150167_a.length; ++i) {
			this.field_150167_a[i] = iconRegister.registerIcon("stripped_" + icon_names_stripped[i] + "_log");
			this.field_150166_b[i] = iconRegister.registerIcon("stripped_" + icon_names_stripped[i] + "_log");
		}
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableStrippedLogs && ConfigBlocksItems.enableBarkLogs;
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

}
