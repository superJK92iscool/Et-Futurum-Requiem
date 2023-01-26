package ganymedes01.etfuturum.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.util.WeightedRandomChestContent;

/**
 * Access Transformers don't work on inner classes, reflection doesn't work on static final fields with Java 19,
 * and mixin accessor classes don't work on non-public classes. So we are left with this: we mix in a setter method,
 * and invoke it via reflection.
 */

@Mixin(targets = "net.minecraft.world.gen.structure.StructureNetherBridgePieces$Piece")
abstract class MixinStructureNetherBridgePieces_Piece {
	
	@Shadow
	@Final
	@Mutable
	private static WeightedRandomChestContent[] field_111019_a;
	
	private static void etfu$setLootTable(WeightedRandomChestContent[] lootTable) {
		field_111019_a = lootTable;
	}
	
}
