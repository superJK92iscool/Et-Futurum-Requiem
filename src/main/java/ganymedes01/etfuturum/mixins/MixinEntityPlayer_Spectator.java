package ganymedes01.etfuturum.mixins;

import ganymedes01.etfuturum.spectator.SpectatorMode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer_Spectator extends EntityLivingBase {

    public MixinEntityPlayer_Spectator(World p_i1595_1_) {
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
}
