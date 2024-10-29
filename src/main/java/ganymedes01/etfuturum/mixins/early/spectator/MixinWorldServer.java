package ganymedes01.etfuturum.mixins.early.spectator;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ganymedes01.etfuturum.spectator.SpectatorMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldServer.class)
public abstract class MixinWorldServer extends World {
    public MixinWorldServer(ISaveHandler p_i45368_1_, String p_i45368_2_, WorldProvider p_i45368_3_, WorldSettings p_i45368_4_, Profiler p_i45368_5_) {
        super(p_i45368_1_, p_i45368_2_, p_i45368_3_, p_i45368_4_, p_i45368_5_);
    }

    @WrapOperation(method = "areAllPlayersAsleep", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isPlayerFullyAsleep()Z"))
    private boolean dontCountSpectatorsAsAwake(EntityPlayer instance, Operation<Boolean> original) {
        if(SpectatorMode.isSpectator(instance)) {
            return true;
        }
        return original.call(instance);
    }

    @WrapOperation(method = "updateAllPlayersSleepingFlag", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isPlayerSleeping()Z"))
    private boolean dontCountSpectatorsAsAwakeForFlag(EntityPlayer instance, Operation<Boolean> original) {
        if(SpectatorMode.isSpectator(instance)) {
            return true;
        }
        return original.call(instance);
    }
}
