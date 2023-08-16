package ganymedes01.etfuturum.entities;

import com.google.common.collect.Lists;
import ganymedes01.etfuturum.blocks.BlockBeeHive;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.helpers.Vec3i;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.tileentities.TileEntityBeeHive;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntityBee extends EntityAnimal {
	private static final int DATA_FLAGS_ID = 13; //byte
	private static final int ANGER_TIME = 14; //int
	private UUID lastHurtBy;
	private float rollAmount;
	private float rollAmountO;
	private int timeSinceSting;
	private int ticksWithoutNectarSinceExitingHive;
	private int stayOutOfHiveCountdown;
	private int numCropsGrownSincePollination;
	private int remainingCooldownBeforeLocatingNewHive = 0;
	private int remainingCooldownBeforeLocatingNewFlower = 0;
	private BlockPos savedFlowerPos = null;
	private BlockPos hivePos = null;
	private EntityBee.PollinateGoal pollinateGoal;
	private EntityBee.FindBeehiveGoal findBeehiveGoal;
	private EntityBee.FindFlowerGoal findFlowerGoal;
	private int underWaterTicks;
	private float nextFlap = 1.0F;

	public EntityBee(World worldIn) {
		super(worldIn);
//		this.moveController = new FlyingMovementController(this, 20, true);
//		this.lookController = new EntityBee.BeeLookController(this);
		setSize(0.4F, 0.4F);
		registerGoals();
	}

	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(DATA_FLAGS_ID, (byte) 0);
		this.getDataWatcher().addObject(ANGER_TIME, 0);
	}

	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
		if (this.isInWater()) {
			this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.800000011920929D;
			this.motionZ *= 0.800000011920929D;
		} else if (this.handleLavaMovement()) {
			this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		} else {
			float f2 = 0.91F;

			if (this.onGround) {
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			float f3 = 0.16277136F / (f2 * f2 * f2);
			this.moveFlying(p_70612_1_, p_70612_2_, this.onGround ? 0.1F * f3 : 0.02F);
			f2 = 0.91F;

			if (this.onGround) {
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= f2;
			this.motionY *= f2;
			this.motionZ *= f2;
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d1 = this.posX - this.prevPosX;
		double d0 = this.posZ - this.prevPosZ;
		float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

		if (f4 > 1.0F) {
			f4 = 1.0F;
		}

		this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	public float getBlockPathWeight(int x, int y, int z) {
		return worldObj.isAirBlock(x, y, z) ? 10.0F : getPathWeight(worldObj.getBlock(x, y, z));
	}

	private float getPathWeight(Block block) {
		if (block instanceof BlockFire || block.getMaterial().isLiquid() || block instanceof BlockCocoa || block instanceof BlockFence || block instanceof BlockWall) {
			return -1F;
		}
		return 0.0F;
	}

	protected void registerGoals() {
		tasks.addTask(0, new EntityBee.StingGoal(this, EntityLivingBase.class, true));
		tasks.addTask(1, new EntityBee.EnterBeehiveGoal());
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
//		tasks.addTask(3, new EntityAITempt(this, 1.25D, Item.getItemFromBlock(Blocks.red_flower), false));//Temptime seems to already work
		this.pollinateGoal = new EntityBee.PollinateGoal();
		tasks.addTask(4, this.pollinateGoal);
		tasks.addTask(5, new EntityAIFollowParent(this, 1.25D));
		tasks.addTask(5, new EntityBee.UpdateBeehiveGoal());
		this.findBeehiveGoal = new EntityBee.FindBeehiveGoal();
		tasks.addTask(5, this.findBeehiveGoal);
		this.findFlowerGoal = new EntityBee.FindFlowerGoal();
		tasks.addTask(6, this.findFlowerGoal);
		tasks.addTask(7, new EntityBee.FindPollinationTargetGoal());
		tasks.addTask(8, new EntityBee.WanderGoal());
		tasks.addTask(9, new EntityAISwimming(this));
		tasks.addTask(1, (new EntityBee.AngerGoal(this))/*.setCallsForHelp(new Class[0])*/);
		tasks.addTask(2, new EntityBee.AttackPlayerGoal(this));
		getNavigator().setAvoidsWater(true);
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if (this.hasHive()) {
			compound.setTag("HivePos", BlockPos.writeToNBT(this.getHivePos()));
		}

		if (this.hasFlower()) {
			compound.setTag("FlowerPos", BlockPos.writeToNBT(this.getFlowerPos()));
		}

		compound.setBoolean("HasNectar", this.hasNectar());
		compound.setBoolean("HasStung", this.hasStung());
		compound.setInteger("TicksSincePollination", this.ticksWithoutNectarSinceExitingHive);
		compound.setInteger("CannotEnterHiveTicks", this.stayOutOfHiveCountdown);
		compound.setInteger("CropsGrownSincePollination", this.numCropsGrownSincePollination);
		compound.setInteger("Anger", this.getAnger());
		if (this.lastHurtBy != null) {
			compound.setString("HurtBy", this.lastHurtBy.toString());
		} else {
			compound.setString("HurtBy", "");
		}
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		this.hivePos = null;
		if (compound.hasKey("HivePos")) {
			this.hivePos = BlockPos.readFromNBT(compound.getCompoundTag("HivePos"));
		}

		this.savedFlowerPos = null;
		if (compound.hasKey("FlowerPos")) {
			setFlowerPos(BlockPos.readFromNBT(compound.getCompoundTag("FlowerPos")));
		}

		super.readEntityFromNBT(compound);
		this.setHasNectar(compound.getBoolean("HasNectar"));
		this.setHasStung(compound.getBoolean("HasStung"));
		this.setAnger(compound.getInteger("Anger"));
		this.ticksWithoutNectarSinceExitingHive = compound.getInteger("TicksSincePollination");
		this.stayOutOfHiveCountdown = compound.getInteger("CannotEnterHiveTicks");
		this.numCropsGrownSincePollination = compound.getInteger("CropsGrownSincePollination");
		String s = compound.getString("HurtBy");
		if (!s.isEmpty()) {
			this.lastHurtBy = UUID.fromString(s);
			EntityPlayer playerentity = null;
			for (EntityPlayer player : (List<EntityPlayer>) worldObj.playerEntities) {
				if (lastHurtBy.equals(player.getUniqueID())) {
					playerentity = player;
					break;
				}
			}
			this.setRevengeTarget(playerentity);
			if (playerentity != null) {
				this.attackingPlayer = playerentity;
				this.recentlyHit = this.numTicksToChaseTarget;
			}
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(new EntityDamageSource("sting", this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue()));
		if (flag) {
//			this.applyEnchantments(this, entityIn);
			if (entityIn instanceof EntityLiving) {
//				((EntityLiving)entityIn).setBeeStingCount(((EntityLiving)entityIn).getBeeStingCount() + 1);
				int i = 0;
				if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
					i = 10;
				} else if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
					i = 18;
				}

				if (i > 0) {
					((EntityLiving) entityIn).addPotionEffect(new PotionEffect(Potion.poison.id, i * 20, 0));
				}
			}

			this.setHasStung(true);
			this.setAttackTarget(null);
			this.playSound(Reference.MCAssetVer + ":entity.bee.sting", 1.0F, 1.0F);
		}

		return flag;
	}

	public void onLivingUpdate() {
		if (this.distanceWalkedOnStepModified > nextFlap && worldObj.isAirBlock((int) posX, (int) posY, (int) posZ)) {
//			playSound(getLivingSound(), 1, 1);
//			nextFlap = 0;
		}
		super.onLivingUpdate();
//		if (this.hasNectar() && this.getCropsGrownSincePollination() < 10 && this.rand.nextFloat() < 0.05F) {
//			for(int i = 0; i < this.rand.nextInt(2) + 1; ++i) {
//				this.addParticle(this.worldObj, this.getPosX() - (double)0.3F, this.getPosX() + (double)0.3F, this.getPosZ() - (double)0.3F, this.getPosZ() + (double)0.3F, this.getPosYHeight(0.5D), ParticleTypes.FALLING_NECTAR);
//			}
//		}

		this.updateBodyPitch();
	}

//	private void addParticle(World worldIn, double p_226397_2_, double p_226397_4_, double p_226397_6_, double p_226397_8_, double posY, IParticleData particleData) {
//		worldIn.addParticle(particleData, Utils.lerp(worldIn.rand.nextDouble(), p_226397_2_, p_226397_4_), posY, Utils.lerp(worldIn.rand.nextDouble(), p_226397_6_, p_226397_8_), 0.0D, 0.0D, 0.0D);
//	}

	private void startMovingTo(BlockPos pos) {
		Vec3i vec3d = new BlockPos(pos);
//		int i = 0;
		BlockPos blockpos = new BlockPos(this);
//		int j = vec3d.getY() - blockpos.getY();
//		if (j > 2) {
//			i = 4;
//		} else if (j < -2) {
//			i = -4;
//		}

		int k = 6;
		int l = 8;
		int i1 = blockpos.manhattanDistance(pos);
		if (i1 < 15) {
			k = i1 / 2;
			l = i1 / 2;
		}

		Vec3 vec3d1 = RandomPositionGenerator.findRandomTarget(this, k, l/*, i, vec3d, (double)((float)Math.PI / 10F)*/);
		if (vec3d1 != null) {
//			this.getNavigator().setRangeMultiplier(0.5F);
			this.getNavigator().tryMoveToXYZ(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, 1.0D);
		}
	}

	public BlockPos getFlowerPos() {
		return this.savedFlowerPos;
	}

	public boolean hasFlower() {
		return this.savedFlowerPos != null;
	}

	public void setFlowerPos(BlockPos pos) {
		this.savedFlowerPos = pos;
	}

	private boolean failedPollinatingTooLong() {
		return this.ticksWithoutNectarSinceExitingHive > 3600;
	}

	private boolean canEnterHive() {
		if (this.stayOutOfHiveCountdown <= 0 && !this.pollinateGoal.isRunning() && !this.hasStung()) {
			boolean flag = this.failedPollinatingTooLong() || this.worldObj.isRaining() || !this.worldObj.isDaytime() || this.hasNectar();
			return flag && !this.isHiveNearFire();
		} else {
			return false;
		}
	}

	public void setStayOutOfHiveCountdown(int p_226450_1_) {
		this.stayOutOfHiveCountdown = p_226450_1_;
	}

	public float getBodyPitch(float p_226455_1_) {
		return Utils.lerp(p_226455_1_, this.rollAmountO, this.rollAmount);
	}

	private void updateBodyPitch() {
		this.rollAmountO = this.rollAmount;
		if (this.isNearTarget()) {
			this.rollAmount = Math.min(1.0F, this.rollAmount + 0.2F);
		} else {
			this.rollAmount = Math.max(0.0F, this.rollAmount - 0.24F);
		}

	}

	public void setRevengeTarget(EntityLivingBase livingBase) {
		super.setRevengeTarget(livingBase);
		if (livingBase != null) {
			this.lastHurtBy = livingBase.getUniqueID();
		}

	}

	protected void updateAITasks() {
		boolean flag = this.hasStung();
		if (this.isInWater()) {
			++this.underWaterTicks;
		} else {
			this.underWaterTicks = 0;
		}

		if (this.underWaterTicks > 20) {
			this.attackEntityFrom(DamageSource.drown, 1.0F);
		}

		if (flag) {
			++this.timeSinceSting;
			if (this.timeSinceSting % 5 == 0 && this.rand.nextInt(MathHelper.clamp_int(1200 - this.timeSinceSting, 1, 1200)) == 0) {
				this.attackEntityFrom(DamageSource.generic, this.getHealth());
			}
		}

		if (this.isAngry()) {
			int i = this.getAnger();
			this.setAnger(i - 1);
			EntityLivingBase livingentity = this.getAttackTarget();
			if (i == 0 && livingentity != null) {
				this.setBeeAttacker(livingentity);
			}
		}

		if (!this.hasNectar()) {
			++this.ticksWithoutNectarSinceExitingHive;
		}

	}

	public void resetTicksWithoutNectar() {
		this.ticksWithoutNectarSinceExitingHive = 0;
	}

	private boolean isHiveNearFire() {
		if (this.hivePos == null) {
			return false;
		} else {
			TileEntity tileentity = hivePos.getTileEntity(worldObj);
			return tileentity instanceof TileEntityBeeHive && ((TileEntityBeeHive) tileentity).isNearFire();
		}
	}

	public boolean isAngry() {
		return this.getAnger() > 0;
	}

	private int getAnger() {
		return this.getDataWatcher().getWatchableObjectInt(ANGER_TIME);
	}

	private void setAnger(int angerTime) {
		this.getDataWatcher().updateObject(ANGER_TIME, angerTime);
	}

	private boolean doesHiveHaveSpace(BlockPos pos) {
		TileEntity tileentity = pos.getTileEntity(worldObj);
		if (tileentity instanceof TileEntityBeeHive) {
			return !((TileEntityBeeHive) tileentity).isFullOfBees();
		} else {
			return false;
		}
	}

	public boolean hasHive() {
		return this.hivePos != null;
	}

	public BlockPos getHivePos() {
		return this.hivePos;
	}

//	protected void sendDebugPackets() {
//		super.sendDebugPackets();
//		DebugPacketSender.func_229749_a_(this);
//	}

	private int getCropsGrownSincePollination() {
		return this.numCropsGrownSincePollination;
	}

	private void resetCropCounter() {
		this.numCropsGrownSincePollination = 0;
	}

	private void addCropCounter() {
		++this.numCropsGrownSincePollination;
	}

	public void onUpdate() {
		super.onUpdate();
		if (!this.worldObj.isRemote) {
			if (this.stayOutOfHiveCountdown > 0) {
				--this.stayOutOfHiveCountdown;
			}

			if (this.remainingCooldownBeforeLocatingNewHive > 0) {
				--this.remainingCooldownBeforeLocatingNewHive;
			}

			if (this.remainingCooldownBeforeLocatingNewFlower > 0) {
				--this.remainingCooldownBeforeLocatingNewFlower;
			}

			boolean flag = this.isAngry() && !this.hasStung() && this.getAttackTarget() != null && this.getAttackTarget().getDistanceSq(posX, posY, posZ) < 4.0D;
			this.setNearTarget(flag);
			if (this.ticksExisted % 20 == 0 && !this.isHiveValid()) {
				this.hivePos = null;
			}
		}

	}

	private boolean isHiveValid() {
		if (!this.hasHive()) {
			return false;
		} else {
			TileEntity tileentity = hivePos.getTileEntity(worldObj);
			return tileentity instanceof TileEntityBeeHive;
		}
	}

	public boolean hasNectar() {
		return this.getBeeFlag(8);
	}

	private void setHasNectar(boolean p_226447_1_) {
		if (p_226447_1_) {
			this.resetTicksWithoutNectar();
		}

		this.setBeeFlag(8, p_226447_1_);
	}

	public boolean hasStung() {
		return this.getBeeFlag(4);
	}

	private void setHasStung(boolean p_226449_1_) {
		this.setBeeFlag(4, p_226449_1_);
	}

	private boolean isNearTarget() {
		return this.getBeeFlag(2);
	}

	private void setNearTarget(boolean p_226452_1_) {
		this.setBeeFlag(2, p_226452_1_);
	}

	private boolean isTooFar(BlockPos pos) {
		return !this.isWithinDistance(pos, 48);
	}

	private void setBeeFlag(int flagId, boolean p_226404_2_) {
		if (p_226404_2_) {
			this.getDataWatcher().updateObject(DATA_FLAGS_ID, (byte) (this.getDataWatcher().getWatchableObjectByte(DATA_FLAGS_ID) | flagId));
		} else {
			this.getDataWatcher().updateObject(DATA_FLAGS_ID, (byte) (this.getDataWatcher().getWatchableObjectByte(DATA_FLAGS_ID) & ~flagId));
		}

	}

	private boolean getBeeFlag(int flagId) {
		return (this.getDataWatcher().getWatchableObjectByte(DATA_FLAGS_ID) & flagId) != 0;
	}

	public boolean isPollinating() {
		return getBeeFlag(0x1);
	}

	public void setPollinating(boolean isPollinating) {
		setBeeFlag(0x1, isPollinating);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
//		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
//		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue((double)0.6F);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((double) 0.3F);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
	}

//	protected PathNavigator createNavigator(World worldIn) {
//		FlyingPathNavigator flyingpathgetNavigator() = new FlyingPathNavigator(this, worldIn) {
//			public boolean canEntityStandOnPos(BlockPos pos) {
//				return !this.worldObj.getBlockState(pos.down()).isAir();
//			}
//
//			public void tick() {
//				if (!EntityBee.this.pollinateGoal.isRunning()) {
//					super.tick();
//				}
//			}
//		};
//		flyingpathgetNavigator().setCanOpenDoors(false);
//		flyingpathgetNavigator().setCanSwim(false);
//		flyingpathgetNavigator().setCanEnterDoors(true);
//		return flyingpathgetNavigator();
//	}

	public boolean isBreedingItem(ItemStack stack) {
		return isFlower(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
	}

	/**
	 * Incomplete
	 */
	private boolean isFlower(Block block, int meta) {
		if (block instanceof BlockFlower && !block.getUnlocalizedName().contains("wither")) {
			return true;
		} else if (block instanceof BlockDoublePlant) {
			return true;
//						if(block == Blocks.double_plant) {
//
//						}
		}
		return false;
	}

	@Override
	protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
	}

	public void playLivingSound() {
	}

	protected String getLivingSound() {
		return Reference.MCAssetVer + ":entity.bee.loop" + (isAngry() ? "_aggressive" : "");
	}

	protected String getHurtSound() {
		return Reference.MCAssetVer + ":entity.bee.hurt";
	}

	protected String getDeathSound() {
		return Reference.MCAssetVer + ":entity.bee.death";
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	public EntityBee createChild(EntityAgeable entity) {
		return new EntityBee(entity.worldObj);
	}

	public float getEyeHeight() {
		return this.height * 0.5F;
	}

	protected void fall(float p_70069_1_) {
	}

	protected void updateFallState(double p_70064_1_, boolean p_70064_3_) {
	}

	public void onHoneyDelivered() {
		this.setHasNectar(false);
		this.resetCropCounter();
	}

	public boolean setBeeAttacker(Entity attacker) {
		this.setAnger(400 + this.rand.nextInt(400));
		if (attacker instanceof EntityLiving) {
			this.setRevengeTarget((EntityLiving) attacker);
		}

		return true;
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable()) {
			return false;
		} else {
			Entity entity = source.getEntity();
			if (!this.worldObj.isRemote && entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode && this.canEntityBeSeen(entity)/* && !this.isAIDisabled()*/) {
				this.pollinateGoal.cancel();
				this.setBeeAttacker(entity);
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

//	protected void handleFluidJump(Tag<Fluid> fluidTag) {
//		this.setMotion(this.getMotion().add(0.0D, 0.01D, 0.0D));
//	}

	private boolean isWithinDistance(BlockPos pos, int distance) {
		return pos.isWithinDistance(new BlockPos(this), distance);
	}

	class AngerGoal extends EntityAIHurtByTarget {
		AngerGoal(EntityBee beeIn) {
			super(beeIn, true);
		}

		protected boolean isSuitableTarget(EntityLivingBase targetIn, boolean p_75296_2_) {
			if (taskOwner instanceof EntityBee && this.taskOwner.canEntityBeSeen(targetIn) && ((EntityBee) taskOwner).setBeeAttacker(targetIn)) {
				taskOwner.setAttackTarget(targetIn);
				return true;
			}
			return false;
		}
	}

	static class AttackPlayerGoal extends EntityAINearestAttackableTarget {
		AttackPlayerGoal(EntityBee beeIn) {
			super(beeIn, EntityPlayer.class, 10, true);
		}

		public boolean shouldExecute() {
			return this.canSting() && super.shouldExecute();
		}

		public boolean continueExecuting() {
			boolean flag = this.canSting();
			if (flag && taskOwner.getAttackTarget() != null) {
				return super.continueExecuting();
			} else {
				targetEntity = null;
				return false;
			}
		}

		private boolean canSting() {
			EntityBee beeentity = (EntityBee) this.taskOwner;
			return beeentity.isAngry() && !beeentity.hasStung();
		}
	}

//	class BeeLookController extends LookController {
//		BeeLookController(MobEntity beeIn) {
//			super(beeIn);
//		}
//
//		public void updateTask() {
//			if (!EntityBee.this.isAngry()) {
//				super.updateTask();
//			}
//		}
//
//		protected boolean func_220680_b() {
//			return !EntityBee.this.pollinateGoal.isRunning();
//		}
//	}

	class EnterBeehiveGoal extends EntityBee.PassiveGoal {
		private EnterBeehiveGoal() {
		}

		public boolean canBeeStart() {
			if (EntityBee.this.hasHive() && EntityBee.this.canEnterHive() && EntityBee.this.hivePos.isWithinDistance(new BlockPos(EntityBee.this), 2.0D)) {
				TileEntity tileentity = getHivePos().getTileEntity(worldObj);
				if (tileentity instanceof TileEntityBeeHive) {
					TileEntityBeeHive beehivetileentity = (TileEntityBeeHive) tileentity;
					if (!beehivetileentity.isFullOfBees()) {
						return true;
					}

					EntityBee.this.hivePos = null;
				}
			}

			return false;
		}

		public boolean canBeeContinue() {
			return false;
		}

		public void startExecuting() {
			TileEntity tileentity = getHivePos().getTileEntity(worldObj);
			if (tileentity instanceof TileEntityBeeHive) {
				TileEntityBeeHive beehivetileentity = (TileEntityBeeHive) tileentity;
				beehivetileentity.tryEnterHive(EntityBee.this, EntityBee.this.hasNectar());
			}

		}
	}

	public class FindBeehiveGoal extends EntityBee.PassiveGoal {
		private int ticks = EntityBee.this.worldObj.rand.nextInt(10);
		private final List<BlockPos> possibleHives = Lists.newArrayList();
		private PathEntity path = null;

		FindBeehiveGoal() {
			setMutexBits(0);
		}

		public boolean canBeeStart() {
			return EntityBee.this.hivePos != null && !EntityBee.this.hasHome() && EntityBee.this.canEnterHive() && !this.isCloseEnough(EntityBee.this.hivePos) && getHivePos().getBlock(worldObj) instanceof BlockBeeHive;
		}

		public boolean canBeeContinue() {
			return this.canBeeStart();
		}

		public void startExecuting() {
			this.ticks = 0;
			super.startExecuting();
		}

		public void resetTask() {
			this.ticks = 0;
			EntityBee.this.getNavigator().clearPathEntity();
//			EntityBee.this.getNavigator().resetRangeMultiplier();
		}

		public void updateTask() {
			if (EntityBee.this.hivePos != null) {
				++this.ticks;
				if (this.ticks > 600) {
					this.makeChosenHivePossibleHive();
				} else if (getNavigator().noPath()) {
					if (!EntityBee.this.isWithinDistance(EntityBee.this.hivePos, 16)) {
						if (EntityBee.this.isTooFar(EntityBee.this.hivePos)) {
							this.reset();
						} else {
							EntityBee.this.startMovingTo(EntityBee.this.hivePos);
						}
					} else {
						boolean flag = this.startMovingToFar(EntityBee.this.hivePos);
						if (!flag) {
							this.makeChosenHivePossibleHive();
						} else if (this.path != null && EntityBee.this.getNavigator().getPath().isSamePath(this.path)) {
							this.reset();
						} else {
							this.path = EntityBee.this.getNavigator().getPath();
						}

					}
				}
			}
		}

		private boolean startMovingToFar(BlockPos pos) {
//			EntityBee.this.getNavigator().setRangeMultiplier(10.0F);
			EntityBee.this.getNavigator().tryMoveToXYZ(pos.getX(), pos.getY(), pos.getZ(), 1.0D);
			return EntityBee.this.getNavigator().getPath() != null /*&& EntityBee.this.getNavigator().getPath().reachesTarget()*/;
		}

		private boolean isPossibleHive(BlockPos pos) {
			return this.possibleHives.contains(pos);
		}

		private void addPossibleHives(BlockPos pos) {
			this.possibleHives.add(pos);

			while (this.possibleHives.size() > 3) {
				this.possibleHives.remove(0);
			}

		}

		private void clearPossibleHives() {
			this.possibleHives.clear();
		}

		private void makeChosenHivePossibleHive() {
			if (EntityBee.this.hivePos != null) {
				this.addPossibleHives(EntityBee.this.hivePos);
			}

			this.reset();
		}

		private void reset() {
			EntityBee.this.hivePos = null;
			EntityBee.this.remainingCooldownBeforeLocatingNewHive = 200;
		}

		private boolean isCloseEnough(BlockPos pos) {
			if (EntityBee.this.isWithinDistance(pos, 2)) {
				return true;
			} else {
				PathEntity path = EntityBee.this.getNavigator().getPath();
				return path != null && path.isDestinationSame(Vec3.createVectorHelper(pos.getX(), pos.getY(), pos.getZ()))/* && path.reachesTarget() */ && path.isFinished();
			}
		}
	}

	public class FindFlowerGoal extends EntityBee.PassiveGoal {
		private int ticks = EntityBee.this.worldObj.rand.nextInt(10);

		FindFlowerGoal() {
			setMutexBits(0);
		}

		public boolean canBeeStart() {
			return EntityBee.this.savedFlowerPos != null && !EntityBee.this.hasHome() && this.shouldMoveToFlower() && EntityBee.this.isFlower(savedFlowerPos.getBlock(worldObj), savedFlowerPos.getBlockMetadata(worldObj)) && !EntityBee.this.isWithinDistance(EntityBee.this.savedFlowerPos, 2);
		}

		public boolean canBeeContinue() {
			return this.canBeeStart();
		}

		public void startExecuting() {
			this.ticks = 0;
			super.startExecuting();
		}

		public void resetTask() {
			this.ticks = 0;
			EntityBee.this.getNavigator().clearPathEntity();
//			EntityBee.this.getNavigator().resetRangeMultiplier();
		}

		public void tick() {
			if (EntityBee.this.savedFlowerPos != null) {
				++this.ticks;
				if (this.ticks > 600) {
					EntityBee.this.savedFlowerPos = null;
				} else if (getNavigator().noPath()) {
					if (EntityBee.this.isTooFar(EntityBee.this.savedFlowerPos)) {
						EntityBee.this.savedFlowerPos = null;
					} else {
						EntityBee.this.startMovingTo(EntityBee.this.savedFlowerPos);
					}
				}
			}
		}

		private boolean shouldMoveToFlower() {
			return EntityBee.this.ticksWithoutNectarSinceExitingHive > 2400;
		}
	}

	class FindPollinationTargetGoal extends EntityBee.PassiveGoal {
		private FindPollinationTargetGoal() {
		}

		public boolean canBeeStart() {
			if (EntityBee.this.getCropsGrownSincePollination() >= 10) {
				return false;
			} else if (EntityBee.this.rand.nextFloat() < 0.3F) {
				return false;
			} else {
				return EntityBee.this.hasNectar() && EntityBee.this.isHiveValid();
			}
		}

		public boolean canBeeContinue() {
			return this.canBeeStart();
		}

		public void updateTask() {
//			if (EntityBee.this.rand.nextInt(30) == 0) {
//				for(int i = 1; i <= 2; ++i) {
//					BlockPos blockpos = (new BlockPos(EntityBee.this)).down(i);
//					Block block = blockpos.getBlock(worldObj);
//					boolean flag = false;
//					IntegerProperty integerproperty = null;
//					if (block.isIn(BlockTags.BEE_GROWABLES)) {
//						if (block instanceof CropsBlock) {
//							CropsBlock cropsblock = (CropsBlock)block;
//							if (!cropsblock.isMaxAge(blockstate)) {
//								flag = true;
//								integerproperty = cropsblock.getAgeProperty();
//							}
//						} else if (block instanceof StemBlock) {
//							int j = blockstate.get(StemBlock.AGE);
//							if (j < 7) {
//								flag = true;
//								integerproperty = StemBlock.AGE;
//							}
//						} else if (block == Blocks.SWEET_BERRY_BUSH) {
//							int k = blockstate.get(SweetBerryBushBlock.AGE);
//							if (k < 3) {
//								flag = true;
//								integerproperty = SweetBerryBushBlock.AGE;
//							}
//						}
//
//						if (flag) {
//							EntityBee.this.playSound(Reference.MCAssetVer + ":entity.bee.pollinate", 1.0F, 1.0F);
////							EntityBee.this.worldObj.setBlockState(blockpos, blockstate.with(integerproperty, Integer.valueOf(blockstate.get(integerproperty) + 1)));
//							EntityBee.this.addCropCounter();
//						}
//					}
//				}
//			}
		}
	}

	abstract class PassiveGoal extends EntityAIBase {
		private PassiveGoal() {
		}

		public abstract boolean canBeeStart();

		public abstract boolean canBeeContinue();

		public boolean shouldExecute() {
			return this.canBeeStart() && !EntityBee.this.isAngry();
		}

		public boolean continueExecuting() {
			return this.canBeeContinue() && !EntityBee.this.isAngry();
		}
	}

	class PollinateGoal extends EntityBee.PassiveGoal {
		private int pollinationTicks = 0;
		private int lastPollinationTick = 0;
		private boolean running;
		private Vec3i nextTarget;
		private int ticks = 0;

		PollinateGoal() {
			this.setMutexBits(0);
		}

		public boolean canBeeStart() {
			if (EntityBee.this.remainingCooldownBeforeLocatingNewFlower > 0) {
				return false;
			} else if (EntityBee.this.hasNectar()) {
				return false;
			} else if (EntityBee.this.worldObj.isRaining()) {
				return false;
			} else if (EntityBee.this.rand.nextFloat() < 0.7F) {
				return false;
			} else {
				Optional<BlockPos> optional = this.getFlower();
				if (optional.isPresent()) {
					EntityBee.this.savedFlowerPos = optional.get();
					EntityBee.this.getNavigator().tryMoveToXYZ((double) EntityBee.this.savedFlowerPos.getX() + 0.5D, (double) EntityBee.this.savedFlowerPos.getY() + 0.5D, (double) EntityBee.this.savedFlowerPos.getZ() + 0.5D, (double) 1.2F);
					return true;
				} else {
					return false;
				}
			}
		}

		public boolean canBeeContinue() {
			if (!this.running) {
				return false;
			} else if (!EntityBee.this.hasFlower()) {
				return false;
			} else if (EntityBee.this.worldObj.isRaining()) {
				return false;
			} else if (this.completedPollination()) {
				return EntityBee.this.rand.nextFloat() < 0.2F;
			} else if (EntityBee.this.ticksExisted % 20 == 0 && !EntityBee.this.isFlower(savedFlowerPos.getBlock(worldObj), savedFlowerPos.getBlockMetadata(worldObj))) {
				EntityBee.this.savedFlowerPos = null;
				return false;
			} else {
				return true;
			}
		}

		private boolean completedPollination() {
			return this.pollinationTicks > 400;
		}

		private boolean isRunning() {
			return this.running;
		}

		private void cancel() {
			this.running = false;
		}

		public void startExecuting() {
			this.pollinationTicks = 0;
			this.ticks = 0;
			this.lastPollinationTick = 0;
			this.running = true;
			EntityBee.this.resetTicksWithoutNectar();
		}

		public void resetTask() {
			if (this.completedPollination()) {
				EntityBee.this.setHasNectar(true);
			}

			this.running = false;
			EntityBee.this.getNavigator().clearPathEntity();
			EntityBee.this.remainingCooldownBeforeLocatingNewFlower = 200;
		}

		public void tick() {
			BlockPos beePos = new BlockPos(EntityBee.this);
			++this.ticks;
			if (this.ticks > 600) {
				EntityBee.this.savedFlowerPos = null;
			} else {
				Vec3i vec3d = EntityBee.this.savedFlowerPos.add(0.5D, 0.6F, 0.5D);
				if (vec3d.getSquaredDistance(beePos) > 1.0D) {
					this.nextTarget = vec3d;
					this.moveToNextTarget();
				} else {
					if (this.nextTarget == null) {
						this.nextTarget = vec3d;
					}

					boolean flag = beePos.getSquaredDistance(this.nextTarget) <= 0.1D;
					boolean flag1 = true;
					if (!flag && this.ticks > 600) {
						EntityBee.this.savedFlowerPos = null;
					} else {
						if (flag) {
							boolean flag2 = EntityBee.this.rand.nextInt(100) == 0;
							if (flag2) {
								this.nextTarget = new Vec3i(vec3d.getX() + (double) this.getRandomOffset(), vec3d.getY(), vec3d.getZ() + (double) this.getRandomOffset());
								EntityBee.this.getNavigator().clearPathEntity();
							} else {
								flag1 = false;
							}

//							EntityBee.this.getLookController().setLookPosition(vec3d.getX(), vec3d.getY(), vec3d.getZ());
						}

						if (flag1) {
							this.moveToNextTarget();
						}

						++this.pollinationTicks;
						if (EntityBee.this.rand.nextFloat() < 0.05F && this.pollinationTicks > this.lastPollinationTick + 60) {
							this.lastPollinationTick = this.pollinationTicks;
							EntityBee.this.playSound(Reference.MCAssetVer + ":entity.bee.pollinate", 1.0F, 1.0F);
						}

					}
				}
			}
		}

		private void moveToNextTarget() {
			EntityBee.this.getMoveHelper().setMoveTo(this.nextTarget.getX(), this.nextTarget.getY(), this.nextTarget.getZ(), (double) 0.35F);
		}

		private float getRandomOffset() {
			return (EntityBee.this.rand.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
		}

		private Optional<BlockPos> getFlower() {
			return this.findFlower(5.0D);
		}

		private Optional<BlockPos> findFlower(double distance) {
			BlockPos blockpos = new BlockPos(EntityBee.this);
			BlockPos blockpos$mutable = new BlockPos(blockpos);

			for (int i = 0; (double) i <= distance; i = i > 0 ? -i : 1 - i) {
				for (int j = 0; (double) j < distance; ++j) {
					for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
						for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
							blockpos$mutable = blockpos$mutable.add(k, i - 1, l);
							if (blockpos.getSquaredDistance(blockpos$mutable) <= distance * distance && isFlower(blockpos$mutable.getBlock(worldObj), blockpos$mutable.getBlockMetadata(worldObj))) {
								return Optional.of(blockpos$mutable);
							}
						}
					}
				}
			}

			return Optional.empty();
		}
	}

	class StingGoal extends EntityAINearestAttackableTarget {
		StingGoal(EntityCreature creatureIn, Class classIn, /*double speedIn,*/ boolean useLongMemory) {
			super(creatureIn, classIn, 10, useLongMemory);
		}

		public boolean shouldExecute() {
			return super.shouldExecute() && EntityBee.this.isAngry() && !EntityBee.this.hasStung();
		}

		public boolean continueExecuting() {
			return super.continueExecuting() && EntityBee.this.isAngry() && !EntityBee.this.hasStung();
		}
	}

	class UpdateBeehiveGoal extends EntityBee.PassiveGoal {
		private UpdateBeehiveGoal() {
		}

		public boolean canBeeStart() {
			return EntityBee.this.remainingCooldownBeforeLocatingNewHive == 0 && !EntityBee.this.hasHive() && EntityBee.this.canEnterHive();
		}

		public boolean canBeeContinue() {
			return false;
		}

		public void startExecuting() {
			EntityBee.this.remainingCooldownBeforeLocatingNewHive = 200;
			List<BlockPos> list = this.getNearbyFreeHives();
			if (!list.isEmpty()) {
				for (BlockPos blockpos : list) {
					if (!EntityBee.this.findBeehiveGoal.isPossibleHive(blockpos)) {
						EntityBee.this.hivePos = blockpos;
						return;
					}
				}

				EntityBee.this.findBeehiveGoal.clearPossibleHives();
				EntityBee.this.hivePos = list.get(0);
			}
		}

		private static final int CHECK_RANGE = 20;

		private List<BlockPos> getNearbyFreeHives() {
			final BlockPos beePos = new BlockPos(EntityBee.this);
			final List<BlockPos> posList = Lists.newArrayList();

			for (int x1 = -CHECK_RANGE; x1 <= CHECK_RANGE; x1++) {
				for (int y1 = -CHECK_RANGE; y1 <= CHECK_RANGE; y1++) {
					for (int z1 = -CHECK_RANGE; z1 <= CHECK_RANGE; z1++) {
						BlockPos pos = new BlockPos(x1, y1, z1);
						if (worldObj.getBlock(x1, y1, z1) instanceof BlockBeeHive && doesHiveHaveSpace(pos)) {
							posList.add(pos);
						}
					}
				}
			}
			posList.sort(Comparator.comparingDouble(pos -> pos.getSquaredDistance(beePos)));
			return posList;
		}
	}

	class WanderGoal extends EntityAIBase {
		WanderGoal() {
			this.setMutexBits(0);
		}

		public boolean shouldExecute() {
			return EntityBee.this.getNavigator().noPath() && EntityBee.this.rand.nextInt(10) == 0;
		}

		public boolean continueExecuting() {
			return !getNavigator().noPath();
		}

		public void startExecuting() {
			Vec3i vec3d = this.getRandomLocation();
			EntityBee.this.getNavigator().setPath(EntityBee.this.getNavigator().getPathToXYZ(vec3d.getX(), vec3d.getY(), vec3d.getZ()), 1.0D);

		}

		private Vec3i getRandomLocation() {
			Vec3 vec3d;
			if (EntityBee.this.isHiveValid() && !EntityBee.this.isWithinDistance(EntityBee.this.hivePos, 40)) {
				Vec3 vec3d1 = Vec3.createVectorHelper(getHivePos().getX(), getHivePos().getY(), getHivePos().getZ());
				vec3d = vec3d1.subtract(getPosition(1)).normalize();
			} else {
				vec3d = EntityBee.this.getLook(0.0F);
			}

			int i = 8;
//			Vec3i vec3d2 = RandomPositionGenerator.findAirTarget(EntityBee.this, 8, 7, vec3d, ((float)Math.PI / 2F), 2, 1);
//			return vec3d2 != null ? vec3d2 : RandomPositionGenerator.findGroundTarget(EntityBee.this, 8, 4, -2, vec3d, (double)((float)Math.PI / 2F));
			return new Vec3i(RandomPositionGenerator.findRandomTarget(EntityBee.this, 8, 7));
		}
	}
}
