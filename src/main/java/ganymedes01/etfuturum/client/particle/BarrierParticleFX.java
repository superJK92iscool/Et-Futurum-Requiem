package ganymedes01.etfuturum.client.particle;

import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BarrierParticleFX extends EtFuturumFXParticle {

	public BarrierParticleFX(World world, double x, double y, double z, double mx, double my, double mz, int maxAge,
			float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, 0, 0, 0, maxAge, scale, color, texture, textures);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		particleMaxAge = maxAge + particleRand.nextInt(20);
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		ItemStack heldItem = player.getHeldItem();

		boolean decay = worldObj.getBlock((int) posX, (int) posY, (int) posZ) != ModBlocks.BARRIER.get() &&
				(!player.capabilities.isCreativeMode || heldItem == null || heldItem.getItem() != Item.getItemFromBlock(ModBlocks.BARRIER.get()));
		if (decay) {
			if (this.particleAge++ >= this.particleMaxAge) {
				this.setDead();
			}
		} else {
			particleAge = 0;
		}
	}
}
