package ganymedes01.etfuturum.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.gen.feature.WorldGenMinable;

@Mixin(value = WorldGenMinable.class)
public class MixinWorldGenMinable {

}
