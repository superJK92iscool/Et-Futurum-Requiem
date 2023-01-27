package ganymedes01.etfuturum.mixins.sounds.client;

import ganymedes01.etfuturum.client.sound.BeaconAmbientSound;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * We need this separate class because importing Minecraft.getMinecraft() will crash the server even if it's in a separate function.
 * Minecraft.getMinecraft() is needed to play the custom sound class for the custom lowered attenuation distance.
 */
@Mixin(TileEntityBeacon.class)
public class MixinTileEntityBeacon_AmbienceOnly extends TileEntity {

    @Shadow boolean field_146015_k;

    @Inject(method = "updateEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntityBeacon;func_146000_x()V", shift = At.Shift.BEFORE))
    private void playAmbience(CallbackInfo ci) {
        //worldObj.isRemote is always true since this is a client-only mixin, so we don't need to check it
        if(field_146015_k) {
            Minecraft.getMinecraft().getSoundHandler().playSound(new BeaconAmbientSound((TileEntityBeacon) (Object) this));
        }
    }
}
