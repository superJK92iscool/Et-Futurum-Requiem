package ganymedes01.etfuturum.mixins.elytra;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.api.elytra.IElytraPlayer;
import ganymedes01.etfuturum.items.ItemArmorElytra;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase implements IElytraPlayer {
    @Shadow public abstract boolean isPlayerSleeping();

    public MixinEntityPlayer(World p_i1594_1_) {
        super(p_i1594_1_);
    }

    public void tickElytra() {
        boolean flag = etfu$isElytraFlying();

        if (flag && !this.onGround && !this.isRiding() && !this.isInWater()) {
            ItemStack itemstack = this.getEquipmentInSlot(3);

            if (itemstack != null && itemstack.getItem() == ModItems.elytra && !ItemArmorElytra.isBroken(itemstack)) {
                flag = true;

                if (!this.worldObj.isRemote && (this.etfu$ticksElytraFlying + 1) % 20 == 0) {
                    itemstack.damageItem(1, this);
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }

        if (!this.worldObj.isRemote) {
            this.etfu$setElytraFlying(flag);
        }

        if (this.etfu$isElytraFlying()) {
            this.etfu$ticksElytraFlying = this.etfu$ticksElytraFlying + 1;
        } else {
            this.etfu$ticksElytraFlying = 0;
        }
    }

    @Inject(method = "getEyeHeight", at = @At("HEAD"), cancellable = true)
    private void getElytraEyeHeight(CallbackInfoReturnable<Float> cir) {
        if(this.etfu$isElytraFlying() && !this.isPlayerSleeping()) {
            cir.setReturnValue(0.4f);
        }
    }

    private float etfu$ticksElytraFlying = 0;
    private boolean etfu$lastElytraFlying = false;

    /**
     * FIXME: Since when can we just use a datawatcher value without reserving it?
     */
    @Override
    public boolean etfu$isElytraFlying() {
        return (this.getDataWatcher().getWatchableObjectByte(0) & (1 << 7)) != 0;
    }

    @Override
    public void etfu$setElytraFlying(boolean flag) {
        byte b0 = this.getDataWatcher().getWatchableObjectByte(0);

        if (flag) {
            this.getDataWatcher().updateObject(0, (byte) (b0 | 1 << 7));
        } else {
            this.getDataWatcher().updateObject(0, (byte) (b0 & ~(1 << 7)));
        }
    }

    @Override
    public float etfu$getTicksElytraFlying() {
        return etfu$ticksElytraFlying;
    }

    @Override
    public boolean etfu$lastElytraFlying() {
        return etfu$lastElytraFlying;
    }

    @Override
    public void etfu$setLastElytraFlying(boolean flag) {
        etfu$lastElytraFlying = flag;
    }
}
