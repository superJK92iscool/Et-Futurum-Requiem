package ganymedes01.etfuturum.mixins.laddertrapdoors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockLadder.class)
public class MixinBlockLadder extends Block {
	protected MixinBlockLadder(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@ModifyConstant(method = "func_149797_b", constant = @Constant(floatValue = 0.125F, ordinal = 0))
	private float expandLadderHitbox(float constant) {
		return 0.1875F;
	}

}
