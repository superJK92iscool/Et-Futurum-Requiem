package ganymedes01.etfuturum.mixins.blockinventories;

import ganymedes01.etfuturum.api.inventory.FakeTileEntityProvider;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntityHopper.class)
public class MixinTileEntityHopper {
	// TODO: replace redirect with less invasive mixin
	@Redirect(method = "func_145893_b", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTileEntity(III)Lnet/minecraft/tileentity/TileEntity;", ordinal = 0))
	private static TileEntity getFakeTileEntity(World world, int x, int y, int z) {
		TileEntity original = world.getTileEntity(x, y, z);
		if(original == null) {
			Block block = world.getBlock(x, y, z);
			if(block instanceof FakeTileEntityProvider) {
				return ((FakeTileEntityProvider)block).getFakeTileEntity(world, x, y, z);
			}
		}
		return original;
	}
}
