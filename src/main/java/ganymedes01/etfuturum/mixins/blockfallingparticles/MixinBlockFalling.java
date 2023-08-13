package ganymedes01.etfuturum.mixins.blockfallingparticles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.particle.ParticleHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mixin(BlockFalling.class)
public abstract class MixinBlockFalling extends Block {

	@Unique
	private static final Map<Block, MapColor> COLOR_OVERRIDES = new HashMap<>();

	protected MixinBlockFalling(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Unique
	private Map<Block, MapColor> etfuturum$getColorOverrides() {
		if (COLOR_OVERRIDES.isEmpty()) {
			COLOR_OVERRIDES.put(Blocks.gravel, MapColor.stoneColor);
			if (ModBlocks.OLD_GRAVEL.isEnabled()) {
				COLOR_OVERRIDES.put(ModBlocks.OLD_GRAVEL.get(), MapColor.stoneColor);
			}
			if (EtFuturum.hasTConstruct) {
				COLOR_OVERRIDES.put(Block.getBlockFromName("TConstruct:GravelOre"), MapColor.stoneColor);
			}
			if (EtFuturum.hasNatura) {
				COLOR_OVERRIDES.put(Block.getBlockFromName("Natura:heatsand"), MapColor.adobeColor);
			}
			if (EtFuturum.hasNetherlicious) {
				COLOR_OVERRIDES.put(Block.getBlockFromName("netherlicious:Nether_Gravel"), MapColor.blackColor);
			}
		}
		return COLOR_OVERRIDES;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (rand.nextInt(16) == 0) {
			if (BlockFalling.func_149831_e(world, x, y - 1, z)) {
				double d0 = (float) x + rand.nextFloat();
				double d1 = (double) y - .05D;
				double d2 = (float) z + rand.nextFloat();
				MapColor mapcolor = etfuturum$getColorOverrides().getOrDefault(this, getMapColor(world.getBlockMetadata(x, y, z)));
				ParticleHandler.FALLING_DUST.spawn(world, d0, d1, d2, 0, 0, 0, 1, mapcolor.colorValue | 0xff000000);
			}
		}
	}
}
