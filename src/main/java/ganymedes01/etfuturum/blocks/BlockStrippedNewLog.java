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
import net.minecraft.block.BlockNewLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

public class BlockStrippedNewLog extends BlockNewLog implements IConfigurable, ISubBlocksBlock, IBurnableBlock {

    public static final String[] field_150169_M = new String[] {"acacia", "dark_oak"};

    public BlockStrippedNewLog() {
        setBlockName(Utils.getUnlocalisedName("log2_stripped"));
        setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.field_150167_a = new IIcon[field_150169_M.length];
        this.field_150166_b = new IIcon[field_150169_M.length];

        for (int i = 0; i < this.field_150167_a.length; ++i) {
            this.field_150167_a[i] = iconRegister.registerIcon("stripped_" + field_150169_M[i] + "_log");
            this.field_150166_b[i] = iconRegister.registerIcon("stripped_" + field_150169_M[i] + "_log" + "_top");
        }
    }

    @Override
    public boolean isEnabled() {
        return ConfigurationHandler.enableStrippedLogs;
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
