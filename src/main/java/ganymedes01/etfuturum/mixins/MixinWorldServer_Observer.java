package ganymedes01.etfuturum.mixins;

import ganymedes01.etfuturum.ducks.IObserverWorldExtension;
import net.minecraft.block.Block;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(WorldServer.class)
public class MixinWorldServer_Observer implements IObserverWorldExtension {

    @Shadow private Set pendingTickListEntriesHashSet;

    @Override
    public boolean etfu$hasScheduledUpdate(int x, int y, int z, Block block) {
        return this.pendingTickListEntriesHashSet.contains(new NextTickListEntry(x, y, z, block));
    }
}
