package ganymedes01.etfuturum.entities;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class EntityStray extends EntitySkeleton {
	
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	
    public EntityStray(final World p_i1741_1_) {
    	super(p_i1741_1_);
    	this.tasks.addTask(1, new EntityAISwimming(this));
    	this.tasks.addTask(2, new EntityAIRestrictSun(this));
    	this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
    	this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
    	this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    	this.tasks.addTask(6, new EntityAILookIdle(this));
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
    	if ((p_i1741_1_ != null) && (!p_i1741_1_.isRemote)) {
    		setCombatTask();
    	}
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
    {
        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);

        this.tasks.addTask(4, this.aiArrowAttack );
        this.addRandomArmor();
        this.enchantEquipment();

        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));

        if (this.getEquipmentInSlot(4) == null)
        {
            Calendar calendar = this.worldObj.getCurrentDate();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }

        return p_110161_1_;
    }
    
    protected void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean flag = super.attackEntityAsMob(entity);
        if (flag) {
            final int i = this.worldObj.difficultySetting.getDifficultyId();
            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < i * 0.3f) {
                entity.setFire(0 * i);
            }
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 140 * i, 0));
        }
        return flag;
    }
    
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
        int j;
        int k;

        j = this.rand.nextInt(3 + p_70628_2_);

        for (k = 0; k < j; ++k) {
            this.dropItem(Items.arrow, 1);
        }
        for (k = 0; k < j; ++k) {
            this.dropItem(Items.bone, 1);
        }
    }
    
    protected String getLivingSound() {
        return "etfuturum:mob.stray.idle";
    }
    
    protected String getHurtSound() {
        return "etfuturum:mob.stray.hurt";
    }
    
    protected String getDeathSound() {
        return "etfuturum:mob.stray.death";
    }
    
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        this.playSound("etfuturum:mob.stray.step", 0.15f, 1.0f);
    }
}
