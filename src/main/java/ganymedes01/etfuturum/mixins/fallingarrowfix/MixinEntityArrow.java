package ganymedes01.etfuturum.mixins.fallingarrowfix;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow extends Entity {
	@Shadow
	private int field_145791_d;

	@Shadow
	private int field_145792_e;

	@Shadow
	private int field_145789_f;

	@Shadow
	private int inData;

	@Shadow
	public abstract void onUpdate();

	public MixinEntityArrow(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	/**
	 * @author embeddedt
	 * @reason Prevent arrows from immediately falling off of blocks like targets when they change state.
	 */
	@Redirect(method = "onUpdate", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/entity/projectile/EntityArrow;inData:I"))
	private int getCurrentStuckInMetadata(EntityArrow instance) {
		int meta = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
		if (inData != meta) {
			/* check if the arrow really needs to fall */
			AxisAlignedBB arrowBounds = AxisAlignedBB.getBoundingBox(this.posX - 0.06, this.posY - 0.06, this.posZ - 0.06, this.posX + 0.06, this.posY + 0.06, this.posZ + 0.06);
			if (!this.worldObj.getCollidingBoundingBoxes(instance, arrowBounds).isEmpty()) {
				/* skip falling */
				return meta;
			}
		}
		return inData;
	}
}
