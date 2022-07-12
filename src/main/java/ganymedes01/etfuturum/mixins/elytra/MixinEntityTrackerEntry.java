package ganymedes01.etfuturum.mixins.elytra;

import ganymedes01.etfuturum.api.elytra.IElytraEntityTrackerEntry;
import net.minecraft.entity.EntityTrackerEntry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityTrackerEntry.class)
public class MixinEntityTrackerEntry implements IElytraEntityTrackerEntry {
    private boolean etfu$wasSendingVelUpdates;

    @Override
    public boolean etfu$getWasSendingVelUpdates() {
        return etfu$wasSendingVelUpdates;
    }

    @Override
    public void etfu$setWasSendingVelUpdates(boolean b) {
        etfu$wasSendingVelUpdates = b;
    }
}
