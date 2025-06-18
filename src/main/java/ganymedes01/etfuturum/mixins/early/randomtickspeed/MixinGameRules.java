package ganymedes01.etfuturum.mixins.early.randomtickspeed;

import ganymedes01.etfuturum.gamerule.RandomTickSpeed;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GameRules.class)
public class MixinGameRules {
    @Inject(method = "setOrCreateGameRule", at = @At("RETURN"))
    private void onGameRuleChanged(String ruleName, String value, CallbackInfo ci) {
        // Your code here to handle gamerule changes
        if (ruleName.equals("randomTickSpeed")) {
            RandomTickSpeed.INSTANCE.CURRENT_VALUE = null;
        }
    }
}