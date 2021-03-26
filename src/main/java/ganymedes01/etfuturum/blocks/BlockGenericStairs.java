package ganymedes01.etfuturum.blocks;

import java.lang.reflect.Field;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockGenericStairs extends BlockStairs implements IConfigurable {

	protected Block base;
	protected final int meta;
	
	public BlockGenericStairs(Block p_i45428_1_, int p_i45428_2_) {
		super(p_i45428_1_, p_i45428_2_);
		Field field = ReflectionHelper.findField(BlockStairs.class, "field_150149_b");
		field.setAccessible(true);
		try {
			base = (Block) field.get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
        useNeighborBrightness = true;
        meta = p_i45428_2_;
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@Override
	public boolean isEnabled() {
		return base instanceof IConfigurable ? ((IConfigurable)base).isEnabled() : ConfigurationHandler.enableGenericStairs;
	}

}
