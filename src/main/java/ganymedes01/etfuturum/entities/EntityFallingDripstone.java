package ganymedes01.etfuturum.entities;

import java.util.ArrayList;
import java.util.Iterator;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockPointedDripstone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFallingDripstone extends EntityFallingBlock {
	
	private int fallHurtMax;
	private float fallHurtAmount;
	private boolean hurtEntities;
	
	public EntityFallingDripstone(World p_i45318_1_) {
		super(p_i45318_1_);
		this.fallHurtMax = 40;
		this.fallHurtAmount = 4.0F;
		hurtEntities = true;
	}
	
	public EntityFallingDripstone(World p_i45318_1_, double p_i45318_2_, double p_i45318_4_, double p_i45318_6_) {
		super(p_i45318_1_, p_i45318_2_, p_i45318_4_, p_i45318_6_, ModBlocks.pointed_dripstone);
		this.fallHurtMax = 40;
		this.fallHurtAmount = 4.0F;
		hurtEntities = true;
	}

	public void onUpdate()
	{

		Block block = ModBlocks.pointed_dripstone;
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		++this.field_145812_b;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY);
		int k = MathHelper.floor_double(this.posZ);
		if (this.field_145812_b == 1)
		{
			field_145814_a = worldObj.getBlockMetadata(i, j, k);
		}

		if (!this.worldObj.isRemote)
		{

			if (this.field_145812_b == 1)
			{
				if (this.worldObj.getBlock(i, j, k) != block)
				{
					this.setDead();
					return;
				}

				this.worldObj.setBlockToAir(i, j, k);
			}

			if (this.onGround && worldObj.getBlock(i, j - 1, k) != block)
			{
				this.motionX *= 0.699999988079071D;
				this.motionZ *= 0.699999988079071D;
				this.motionY *= -0.5D;
				this.setDead();
				
				if (this.field_145813_c)
				{
					this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.field_145814_a)), 0.0F);
				}
			}
			else if (this.field_145812_b > 100 && !this.worldObj.isRemote && (j < 1 || j > 256) || this.field_145812_b > 600)
			{
				if (this.field_145813_c)
				{
					this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.field_145814_a)), 0.0F);
				}

				this.setDead();
			}
		}
	}
	
	protected void fall(float p_70069_1_)
	{
		if (this.hurtEntities)
		{
			int i = MathHelper.ceiling_float_int(p_70069_1_ - 1.0F);

			if (i > 0)
			{
				ArrayList arraylist = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
				DamageSource damagesource = BlockPointedDripstone.STALACTITE_DAMAGE;
				Iterator iterator = arraylist.iterator();

				while (iterator.hasNext())
				{
					Entity entity = (Entity)iterator.next();
					entity.attackEntityFrom(damagesource, (float)Math.min(MathHelper.floor_float((float)i * this.fallHurtAmount), this.fallHurtMax));
				}
			}
		}
	}
	
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setBoolean("HurtEntities", this.hurtEntities);
		p_70014_1_.setFloat("FallHurtAmount", this.fallHurtAmount);
		p_70014_1_.setInteger("FallHurtMax", this.fallHurtMax);
	}
	
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		this.hurtEntities = p_70037_1_.getBoolean("HurtEntities");
		this.fallHurtAmount = p_70037_1_.getFloat("FallHurtAmount");
		this.fallHurtMax = p_70037_1_.getInteger("FallHurtMax");
	}
}
