package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDyedCauldron extends BlockCauldronTileEntity implements IConfigurable, ISubBlocksBlock {

	protected BlockDyedCauldron() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
