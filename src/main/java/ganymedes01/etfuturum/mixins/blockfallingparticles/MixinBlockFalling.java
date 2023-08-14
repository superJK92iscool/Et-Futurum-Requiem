package ganymedes01.etfuturum.mixins.blockfallingparticles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.particle.ParticleHandler;
import ganymedes01.etfuturum.core.utils.ExternalContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

@Mixin(BlockFalling.class)
public abstract class MixinBlockFalling extends Block {

	protected MixinBlockFalling(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (rand.nextInt(16) == 0) {
			if (BlockFalling.func_149831_e(world, x, y - 1, z)) {
				double d0 = (float) x + rand.nextFloat();
				double d1 = (double) y - .05D;
				double d2 = (float) z + rand.nextFloat();
				ParticleHandler.FALLING_DUST.spawn(world, d0, d1, d2, 0, 0, 0, 1, etfuturum$getDustColor(world.getBlockMetadata(x, y, z)));
			}
		}
	}

	@Unique
	protected int etfuturum$getDustColor(int meta) {
		if (this == Blocks.gravel || this == ModBlocks.OLD_GRAVEL.get() || this == ExternalContent.Blocks.TCON_GRAVEL_ORE.get()) {
			return 0xFF807C7B;
		}
		if (this == ExternalContent.Blocks.NATURA_HEAT_SAND.get()) {
			return 0xFFD87F33;
		}
		if (this == ExternalContent.Blocks.NETHERLICIOUS_NETHER_GRAVEL.get()) {
			return 0xFF191919;
		}
		return getMapColor(meta).colorValue | 0xFF000000;
	}
}
