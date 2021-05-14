package ganymedes01.etfuturum.blocks;

import org.apache.commons.lang3.tuple.ImmutablePair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks.IBurnableBlock;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockGeneric;
import net.minecraft.block.BlockOldLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

public class BlockWoodBarkOld extends BlockOldLog implements IConfigurable, ISubBlocksBlock, IBurnableBlock {
	
	public BlockWoodBarkOld() {
		setBlockName(Utils.getUnlocalisedName("bark"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.field_150167_a = new IIcon[field_150168_M.length];
		this.field_150166_b = new IIcon[field_150168_M.length];

		for (int i = 0; i < this.field_150167_a.length; ++i) {
			this.field_150167_a[i] = iconRegister.registerIcon("log_" + field_150168_M[i]);
			this.field_150166_b[i] = iconRegister.registerIcon("log_" + field_150168_M[i]);
		}
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableBarkLogs;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}

	@Override
	public ImmutablePair getFireInfo() {
		return new ImmutablePair(5, 5);
	}
}
