package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Map;
import java.util.WeakHashMap;

public class BlockSlime extends BaseBlock {

	private static final Map<Entity, ImmutablePair<Double, Integer>> SLIME_BOUNCE_CACHE = new WeakHashMap<>();

	public BlockSlime() {
		super(Material.clay);
		setHardness(0.0F);
		setNames("slime");
		setBlockSound(ModSounds.soundSlime);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.125F;
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - f, z + 1);
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (!entity.isSneaking()) {
			entity.fallDistance = 0;
			if (entity.motionY < 0.1) {
				SLIME_BOUNCE_CACHE.put(entity, new ImmutablePair<>(-entity.motionY, entity.ticksExisted));
			}
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		ImmutablePair<Double, Integer> pair = SLIME_BOUNCE_CACHE.get(entity);
		if (pair != null && SLIME_BOUNCE_CACHE.get(entity).getRight() == entity.ticksExisted) {
			entity.motionY = pair.getLeft();
		}
		SLIME_BOUNCE_CACHE.remove(entity);
		double d = 0.4 + Math.abs(entity.motionY) * 0.2;
		entity.motionX *= d;
		entity.motionZ *= d;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.SLIME_BLOCK;
	}
}