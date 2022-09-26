package ganymedes01.etfuturum.entities;

import java.awt.Color;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.items.ItemArrowTipped;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityTippedArrow extends EntityArrow {

	private ItemStack arrow;

	public EntityTippedArrow(World world) {
		super(world);
	}

	public EntityTippedArrow(World world, EntityLivingBase entity, float f0) {
		super(world, entity, f0);
	}

	public EntityTippedArrow(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityTippedArrow(World world, EntityLivingBase entity, EntityLivingBase target, float f0, float f1) {
		super(world, entity, target, f0, f1);
	}

	public void setArrow(ItemStack effect) {
		this.arrow = effect;
	}

	public ItemStack getArrow() {
		return arrow;
	}

	private boolean isEffectValid() {
		return arrow != null;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (isEffectValid()) {
			Color colour = new Color(Items.potionitem.getColorFromItemStack(arrow, 0));
			worldObj.spawnParticle("mobSpell", posX, posY, posZ, colour.getRed() / 255F, colour.getGreen() / 255F, colour.getBlue() / 255F);
		}
	}
    public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);

        if (p_70037_1_.hasKey("Potion", 10))
        {
            this.arrow = ItemStack.loadItemStackFromNBT(p_70037_1_.getCompoundTag("Potion"));
        }
        else
        {
            this.setPotionDamage(p_70037_1_.getInteger("potionValue"));
        }

        if (this.arrow == null)
        {
            this.setDead();
        }
    }

    public void setPotionDamage(int p_82340_1_)
    {
        if (this.arrow == null)
        {
            this.arrow = new ItemStack(ModItems.tipped_arrow, 1, 0);
        }

        this.arrow.setItemDamage(p_82340_1_);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);

        if (this.arrow != null)
        {
            p_70014_1_.setTag("Potion", this.arrow.writeToNBT(new NBTTagCompound()));
        }
    }

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if (!worldObj.isRemote && inGround && arrowShake <= 0 && isEffectValid()) {
			boolean flag = canBePickedUp == 1 || canBePickedUp == 2 && player.capabilities.isCreativeMode;

			ItemStack stack = arrow.copy();
			stack.stackSize = 1;

			if (canBePickedUp == 1 && !player.inventory.addItemStackToInventory(stack))
				flag = false;

			if (flag) {
				playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				player.onItemPickup(this, 1);
				setDead();
			}
		}
	}
}