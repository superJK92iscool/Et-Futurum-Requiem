package ganymedes01.etfuturum.mixins.spectator;

import ganymedes01.etfuturum.spectator.SpectatorMode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

	public MixinEntityPlayer(World p_i1595_1_) {
		super(p_i1595_1_);
	}

	@Override
	public boolean canBePushed() {
		return !SpectatorMode.isSpectator((EntityPlayer)(Object)this);
	}

	@Override
	public boolean canBeCollidedWith() {
		return !SpectatorMode.isSpectator((EntityPlayer)(Object)this);
	}

	@Override
	protected void collideWithNearbyEntities() {
		if(!SpectatorMode.isSpectator((EntityPlayer)(Object)this))
			super.collideWithNearbyEntities();
	}

	@Override
	public boolean isOnLadder() {
		if(SpectatorMode.isSpectator((EntityPlayer)(Object)this))
			return false;
		return super.isOnLadder();
	}

	@Override
	public boolean handleWaterMovement() {
		if(SpectatorMode.isSpectator((EntityPlayer)(Object)this))
			return false;
		return super.handleWaterMovement();
	}
}
