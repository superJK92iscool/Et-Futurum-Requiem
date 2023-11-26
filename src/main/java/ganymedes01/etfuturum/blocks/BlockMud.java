package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMud extends BaseBlock {
	public BlockMud() {
		super(Material.ground);
		setBlockSound(ModSounds.soundMud);
		setNames("mud");
		setHardness(0.5F);
		setResistance(0.5F);
		setCreativeTab(EtFuturum.creativeTabBlocks);
		setHarvestLevel("shovel", 0);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		return direction == ForgeDirection.UP && plantable.getPlantType(world, x, y, z) == EnumPlantType.Plains;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		float f = 0.125F;
		return AxisAlignedBB.getBoundingBox(p_149668_2_, p_149668_3_, p_149668_4_, p_149668_2_ + 1, p_149668_3_ + 1 - f, p_149668_4_ + 1);
	}
}
