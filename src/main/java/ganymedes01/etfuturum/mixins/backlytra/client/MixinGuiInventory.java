package ganymedes01.etfuturum.mixins.backlytra.client;

import ganymedes01.etfuturum.core.proxy.ClientProxy;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiInventory.class)
public class MixinGuiInventory {
    @Inject(method = "func_147046_a", at = @At(value = "HEAD"))
    private static void hookPlayerRenderingHead(CallbackInfo ci) {
        ClientProxy.isRenderingInventoryPlayer = true;
    }

    @Inject(method = "func_147046_a", at = @At(value = "TAIL"))
    private static void hookPlayerRenderingTail(CallbackInfo ci) {
        ClientProxy.isRenderingInventoryPlayer = false;
    }
}
