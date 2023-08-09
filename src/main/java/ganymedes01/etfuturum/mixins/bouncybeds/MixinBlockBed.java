package ganymedes01.etfuturum.mixins.bouncybeds;

import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;
import java.util.WeakHashMap;

@Mixin(value = BlockBed.class)
public class MixinBlockBed extends Block {

	private static final Map<Entity, ImmutablePair<Double, Integer>> BED_BOUNCE_CACHE = new WeakHashMap<>();

	protected MixinBlockBed(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (!entity.isSneaking() && ConfigMixins.bouncyBeds) {
			entity.fallDistance /= 2;
			if (entity.motionY < 0) {
				BED_BOUNCE_CACHE.put(entity, new ImmutablePair<>(-entity.motionY * 0.66, entity.ticksExisted));
			}
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		ImmutablePair<Double, Integer> pair = BED_BOUNCE_CACHE.get(entity);
		if (ConfigMixins.bouncyBeds && pair != null && BED_BOUNCE_CACHE.get(entity).getRight() == entity.ticksExisted) {
			entity.motionY = pair.getLeft();
		}
		BED_BOUNCE_CACHE.remove(entity);
	}
}
