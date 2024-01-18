package ganymedes01.etfuturum.items;

import com.google.common.collect.Maps;
import ganymedes01.etfuturum.entities.EntityNewBoat;
import ganymedes01.etfuturum.entities.EntityNewBoatWithChest;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ItemNewBoat extends BaseItem {

	@Deprecated
	private EntityNewBoat.Type type;
	private final boolean isChest;

	private final String name;
	private final String domain;

	public static final Map<String, BoatInfo> BOAT_INFO = Maps.newHashMap();

	@Deprecated
	public ItemNewBoat(EntityNewBoat.Type type, boolean isChest) {
		super(type.getName() + (isChest ? "_chest_boat" : "_boat"));
		this.type = type;
		this.name = type.getName();
		this.domain = "minecraft";
		this.isChest = isChest;
		BOAT_INFO.put("minecraft:" + type.getName().toLowerCase() + (isChest ? "_chest" : ""),
				new BoatInfo(new ItemStack(this), () -> Item.getItemFromBlock(Blocks.planks), type.ordinal(), false));
		setMaxStackSize(1);
	}

	public ItemNewBoat(String domain, String name, Supplier<Item> plank, int plankMeta, boolean isChest, boolean isRaft) {
		super(name + (isChest ? "_chest" : "") + (isRaft ? "_raft" : "_boat"));
		this.name = name;
		this.domain = domain;
		this.isChest = isChest;
		BOAT_INFO.put(domain + ":" + name + (isChest ? "_chest" : ""), new BoatInfo(new ItemStack(this), plank, plankMeta, isRaft));
		setMaxStackSize(1);
	}

	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		float f = 1.0F;
		float f1 = p_77659_3_.prevRotationPitch + (p_77659_3_.rotationPitch - p_77659_3_.prevRotationPitch) * f;
		float f2 = p_77659_3_.prevRotationYaw + (p_77659_3_.rotationYaw - p_77659_3_.prevRotationYaw) * f;
		double d0 = p_77659_3_.prevPosX + (p_77659_3_.posX - p_77659_3_.prevPosX) * (double) f;
		double d1 = p_77659_3_.prevPosY + (p_77659_3_.posY - p_77659_3_.prevPosY) * (double) f + 1.62D - (double) p_77659_3_.yOffset;
		double d2 = p_77659_3_.prevPosZ + (p_77659_3_.posZ - p_77659_3_.prevPosZ) * (double) f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
		MovingObjectPosition movingobjectposition = p_77659_2_.rayTraceBlocks(vec3, vec31, true);

		if (movingobjectposition == null) {
			return p_77659_1_;
		}
		Vec3 vec32 = p_77659_3_.getLook(f);
		boolean flag = false;
		float f9 = 1.0F;
		List list = p_77659_2_.getEntitiesWithinAABBExcludingEntity(p_77659_3_, p_77659_3_.boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
		int i;

		for (i = 0; i < list.size(); ++i) {
			Entity entity = (Entity) list.get(i);

			if (entity.canBeCollidedWith()) {
				float f10 = entity.getCollisionBorderSize();
				AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f10, f10, f10);

				if (axisalignedbb.isVecInside(vec3)) {
					flag = true;
				}
			}
		}

		if (flag) {
			return p_77659_1_;
		}
		if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;

			if (p_77659_2_.getBlock(i, j, k) == Blocks.snow_layer) {
				--j;
			}

			EntityNewBoat entityboat;
			if (isChest) {
				entityboat = new EntityNewBoatWithChest(p_77659_2_);
			} else {
				entityboat = new EntityNewBoat(p_77659_2_);
			}
			boolean isWater = p_77659_2_.getBlock(i, j, k).getMaterial() == Material.water;
			entityboat.setPositionAndRotation(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord + (isWater ? -0.12 : 0), movingobjectposition.hitVec.zCoord, p_77659_3_.rotationYaw, 0);
			entityboat.motionX = entityboat.motionY = entityboat.motionZ = 0;
			entityboat.setBoatType(domain, name);
			if (p_77659_1_.hasDisplayName()) {
				entityboat.setBoatName(p_77659_1_.getDisplayName());
			}

			if (!p_77659_2_.getCollidingBoundingBoxes(entityboat, entityboat.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
				return p_77659_1_;
			}

			if (!p_77659_2_.isRemote) {
				p_77659_2_.spawnEntityInWorld(entityboat);
			}

			if (!p_77659_3_.capabilities.isCreativeMode) {
				--p_77659_1_.stackSize;
			}
		}

		return p_77659_1_;
	}

	public class BoatInfo {
		private final ItemStack boatItem;
		private final Supplier<Item> plank;
		private final int plankMeta;
		private final boolean raft;

		public BoatInfo(ItemStack boatItem, Supplier<Item> plank, int plankMeta, boolean raft) {
			this.boatItem = boatItem;
			this.plank = plank;
			this.plankMeta = plankMeta;
			this.raft = raft;
		}

		public ItemStack getBoatItem() {
			return boatItem.copy();
		}

		public ItemStack getPlank() {
			return new ItemStack(plank.get(), 1, plankMeta);
		}

		public boolean isRaft() {
			return raft;
		}
	}
}
