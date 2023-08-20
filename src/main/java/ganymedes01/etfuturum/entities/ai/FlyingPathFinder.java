package ganymedes01.etfuturum.entities.ai;

import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Map;

public class FlyingPathFinder extends PathFinder {

	private final Map<PathPoint, Float> costMalusMap = Maps.newHashMap();

	public FlyingPathFinder(IBlockAccess p_i2137_1_, boolean p_i2137_2_, boolean p_i2137_3_, boolean p_i2137_4_, boolean p_i2137_5_) {
		super(p_i2137_1_, p_i2137_2_, p_i2137_3_, p_i2137_4_, p_i2137_5_);
	}

	protected int findPathOptions(Entity entity, PathPoint currentPoint/*no idea what this is*/, PathPoint offsetPoint, PathPoint targetPoint, float maxDistance) {
		int i = 0;
		boolean[] booleans = new boolean[6];
		for (ForgeDirection facing : ForgeDirection.VALID_DIRECTIONS) {
			PathPoint pathpoint = openPointFlying(entity, currentPoint.xCoord + facing.offsetX, currentPoint.yCoord + facing.offsetY, currentPoint.zCoord + facing.offsetZ);

			if (pathpoint != null && !pathpoint.isFirst && pathpoint.distanceTo(targetPoint) < maxDistance) {
				booleans[i] = costMalusMap.getOrDefault(pathpoint, 0.0F) >= 0.0F;
				pathOptions[i++] = pathpoint;
			}
		}


		for (int dir = 0; dir < 4; dir++) {
			ForgeDirection facing = ForgeDirection.getOrientation(dir);
			for (int dir2 = 0; dir2 < 5; dir2++) {
				ForgeDirection facing2 = ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[dir2]);
				boolean flag2 = facing.offsetY != 0;
				boolean flag1 = facing2.offsetY != 0;
				if (facing != facing2 && facing != facing2.getOpposite() && facing2 != facing.getOpposite() && booleans[facing.ordinal()] && booleans[facing2.ordinal()]) {
					PathPoint pathpoint = this.openPointFlying(entity, currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord - 1);
					if (pathpoint != null && !pathpoint.isFirst && pathpoint.distanceTo(targetPoint) < maxDistance) {
						pathOptions[i++] = pathpoint;
					}
				}
				if (i >= 20) {
					return 20;
				}
			}
		}
		return i;
	}

	protected PathPoint openPointFlying(Entity entity, int x, int y, int z) {
		if (!(entity instanceof EntityCreature)) return super.openPoint(x, y, z);

		float f = ((EntityCreature) entity).getBlockPathWeight(x, y, z);
		PathPoint pathpoint = null;
//		Vec3 vec = ((EntityCreature) entity).getPosition(1.0F);
//		vec.yCoord += entity.getEyeHeight(); Experimental code to not put a path point if it's obstructed; bees seem to enjoy sticking to walls even with this code.
		if (f >= 0.0F /*&& entity.worldObj.func_147447_a(vec, Vec3.createVectorHelper(x, y, z), false, true, false) == null*/) {
			pathpoint = super.openPoint(x, y, z);
			costMalusMap.put(pathpoint, f);
		}

		return pathpoint;
	}
}
