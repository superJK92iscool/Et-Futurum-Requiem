package ganymedes01.etfuturum.mixins.soulfire;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ducks.ISoulFireInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BlockFire.class)
public abstract class MixinBlockFire extends Block implements ISoulFireInfo {

	@SideOnly(Side.CLIENT)
	private IIcon[] soulFireIcons;

	protected MixinBlockFire(Material m) {
		super(m);
	}

	@Inject(method = "updateTick", at = @At(value = "HEAD"), cancellable = true)
	private void shouldSpread(World world, int x, int y, int z, Random p_149674_5_, CallbackInfo ci) {
		if ((Object) this == Blocks.fire) { //Only want to do this to vanilla fire, not modded fire.
			if (isSoulFire(world, x, y, z)) { //For some reason if I put them together IntelliJ has a false warning
				ci.cancel();
			}
		}
	}

	@Inject(method = "registerBlockIcons", at = @At(value = "HEAD"))
	private void addSoulFireIcon(IIconRegister reg, CallbackInfo ci) {
		soulFireIcons = new IIcon[2];
		soulFireIcons[0] = reg.registerIcon("soul_fire_0");
		soulFireIcons[1] = reg.registerIcon("soul_fire_1");
	}

	@Override
	public IIcon getSoulFireIcon(int type) {
		return soulFireIcons[type % soulFireIcons.length];
	}
}
