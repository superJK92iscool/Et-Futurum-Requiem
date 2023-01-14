package ganymedes01.etfuturum.mixins;

import org.spongepowered.asm.mixin.Mixin;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

@Mixin(value = BlockBed.class)
public class MixinBlockBed extends Block {

	protected MixinBlockBed(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
		if (!entity.isSneaking()) {
			entity.fallDistance /= 2;
			if (entity.motionY < 0)
				entity.getEntityData().setDouble(Reference.MOD_ID + ":bed_bounce", -entity.motionY * 0.66);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		NBTTagCompound data = entity.getEntityData();
		if (data.hasKey(Reference.MOD_ID + ":bed_bounce")) {
			entity.motionY = data.getDouble(Reference.MOD_ID + ":bed_bounce");
			data.removeTag(Reference.MOD_ID + ":bed_bounce");
		}
	}
}
