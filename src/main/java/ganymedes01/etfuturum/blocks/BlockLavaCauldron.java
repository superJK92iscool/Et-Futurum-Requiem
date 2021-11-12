package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.BlockCauldron;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockLavaCauldron extends BlockCauldron implements IConfigurable, ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	public IIcon field_150029_a;
	@SideOnly(Side.CLIENT)
	public IIcon field_150028_b;
	@SideOnly(Side.CLIENT)
	public IIcon field_150030_M;
	@SideOnly(Side.CLIENT)
	public IIcon lavaIcon;
	
	public BlockLavaCauldron() {
		super();
		this.setStepSound(Blocks.cauldron.stepSound);
		this.setHardness(2);
		this.setResistance(2);
		this.setTickRandomly(true);
		this.setLightLevel(1.0F);
		this.setBlockName(Utils.getUnlocalisedName("lava_cauldron"));
	}
	
	@Override
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
//      ItemStack item;
//      if (p_149727_5_.getCurrentEquippedItem() != null) {
//          item = p_149727_5_.getCurrentEquippedItem();
//          if (item.getItem() instanceof ItemBucket) {
//              // TODO Bucketing lava out of the cauldron
//          }
//      }
//      return true;
		return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		boolean flag = (int)entity.prevPosX != (int)entity.posX || (int)entity.prevPosY != (int)entity.posY || (int)entity.prevPosZ != (int)entity.posZ;

		if ((flag || entity.ticksExisted % 25 == 0) && !(entity instanceof EntityItemUninflammable))
		{
			entity.motionY = 0.20000000298023224D;
			entity.motionX = (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F;
			entity.motionZ = (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F;
			entity.playSound("random.fizz", 0.4F, 2.0F + world.rand.nextFloat() * 0.4F);
		}
		if (!world.isRemote) {
			entity.attackEntityFrom(DamageSource.lava, 4.0F);
			if(!entity.isImmuneToFire() && !(world.canBlockSeeTheSky(x, y + 1, z) && world.isRaining())) {
				entity.setFire(15);
			}
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.cauldron.getIcon(side, meta);
	}
	
	@Override
	public int tickRate(World world)
	{
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		float p0 = 0.875F;
		float p1 = 0.125F;

		double d0 = x + random.nextFloat();
		double d1 = z + random.nextFloat();
		
		if(d0 > (p0 + x)) {
			d0 = x + p0;
		} else if(d0 < (p1 + x))
			d0 = x + p1;
		if(d1 > (p0 + z)) {
			d1 = z + p0;
		} else if(d1 < (p1 + z))
			d1 = z + p1;
		
		if (world.canBlockSeeTheSky(x, y + 1, z) && world.isRaining()) {
			world.spawnParticle("smoke", d0, y + 1, d1, 0.0D, 0.0D, 0.0D);
		} if (random.nextInt(100) == 0) {
			world.spawnParticle("lava", d0, y + 1, d1, 0.0D, 0.0D, 0.0D);
			world.playSound(x + .5, y + 1, z + 0.5, "liquid.lavapop", 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
		} if (random.nextInt(200) == 0) {
			world.playSound(x + .5, y + 1, z + 0.5, "liquid.lava", 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		// Taking Icons from original Cauldron
	}
	
	@Override
	public int getRenderType()
	{
		return RenderIDs.LAVA_CAULDRON;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableLavaCauldrons;
	}
}
