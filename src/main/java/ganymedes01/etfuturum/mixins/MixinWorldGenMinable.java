package ganymedes01.etfuturum.mixins;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.DeepslateOreRegistry;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.feature.WorldGenMinable;

@Mixin(value = WorldGenMinable.class)
public class MixinWorldGenMinable {

	@Shadow
	/**
	 * Block to generate
	 */
	private Block field_150519_a;
	@Shadow
	/**
	 * Generate meta
	 */
	private int mineableBlockMeta;
	@Shadow
	/**
	 * Block to replace
	 */
	private Block field_150518_c;
	
	//Custom fields
	private BlockAndMetadataMapping map;
	private boolean notDeepslate;
	
	private boolean stopGeneration(Block getBlock, World world, int x, int y, int z) {
		boolean replaceable = getBlock.isReplaceableOreGen(world, x, y, z, ModBlocks.deepslate);
		if(ConfigWorld.deepslateReplacesDirt && field_150519_a == Blocks.dirt && replaceable) {
			return true;
		}
		if(ConfigWorld.deepslateReplacesStones && field_150519_a == ModBlocks.stone && replaceable) {
			return true;
		}
		return false;
	}
	
	@Inject(method = "generate", at = @At(value = "HEAD"), cancellable = true)
	private void canGenerateHere(World world, Random rand, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		notDeepslate = (map = DeepslateOreRegistry.getOre(field_150519_a, mineableBlockMeta)) == null;
	}
	
	@Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlock(IIILnet/minecraft/block/Block;II)Z", ordinal = 0))
	private boolean ignoreVanillaSetblock(World world, int x, int y, int z, Block block, int meta, int flag) {
		Block getBlock = world.getBlock(x, y, z);
		boolean replaceOre = !notDeepslate && getBlock.isReplaceableOreGen(world, x, y, z, ModBlocks.deepslate);
		
		if(!stopGeneration(getBlock, world, x, y, z)) {
			if (replaceOre && !ArrayUtils.contains(ConfigWorld.deepslateLayerDimensionBlacklist, world.provider.dimensionId) && world.getWorldInfo().getTerrainType() != WorldType.FLAT) {
				world.setBlock(x, y, z, map.getBlock(), map.getMeta(), 2);
				return true;
			} else if(getBlock.isReplaceableOreGen(world, x, y, z, field_150518_c)) {
				world.setBlock(x, y, z, this.field_150519_a, mineableBlockMeta, 2);
				return true;
			}
		}
		
		return false;
	}
}
