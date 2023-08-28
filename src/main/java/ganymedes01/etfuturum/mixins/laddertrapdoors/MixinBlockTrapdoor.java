package ganymedes01.etfuturum.mixins.laddertrapdoors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockTrapDoor.class)
public class MixinBlockTrapdoor extends Block {
	protected MixinBlockTrapdoor(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		return world.getBlockMetadata(x, y, z) % 8 > 3 && world.getBlock(x, y - 1, z) instanceof BlockLadder;
	}
}
