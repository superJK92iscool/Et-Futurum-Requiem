package ganymedes01.etfuturum.entities.ai;

import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.world.IBlockAccess;

import java.util.Map;

public class FlyingPathFinder extends PathFinder {

	private final Map<PathPoint, Float> costMalusMap = Maps.newHashMap();

	public FlyingPathFinder(IBlockAccess p_i2137_1_, boolean p_i2137_2_, boolean p_i2137_3_, boolean p_i2137_4_, boolean p_i2137_5_) {
		super(p_i2137_1_, p_i2137_2_, p_i2137_3_, p_i2137_4_, p_i2137_5_);
	}

	protected int findPathOptions(Entity entity, PathPoint currentPoint/*no idea what this is*/, PathPoint offsetPoint, PathPoint targetPoint, float maxDistance) {
		int i = 0;
		PathPoint pathpoint = openPointFlying(entity, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord + 1);
		PathPoint pathpoint1 = openPointFlying(entity, currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord);
		PathPoint pathpoint2 = openPointFlying(entity, currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord);
		PathPoint pathpoint3 = openPointFlying(entity, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord - 1);
		PathPoint pathpoint4 = openPointFlying(entity, currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord);
		PathPoint pathpoint5 = openPointFlying(entity, currentPoint.xCoord, currentPoint.yCoord - 1, currentPoint.zCoord);

		if (pathpoint != null && !pathpoint.isFirst && pathpoint.distanceTo(targetPoint) < maxDistance) {
			pathOptions[i++] = pathpoint;
		}

		if (pathpoint1 != null && !pathpoint1.isFirst && pathpoint1.distanceTo(targetPoint) < maxDistance) {
			pathOptions[i++] = pathpoint1;
		}

		if (pathpoint2 != null && !pathpoint2.isFirst && pathpoint2.distanceTo(targetPoint) < maxDistance) {
			pathOptions[i++] = pathpoint2;
		}

		if (pathpoint3 != null && !pathpoint3.isFirst && pathpoint3.distanceTo(targetPoint) < maxDistance) {
			pathOptions[i++] = pathpoint3;
		}

		if (pathpoint4 != null && !pathpoint4.isFirst && pathpoint4.distanceTo(targetPoint) < maxDistance) {
			pathOptions[i++] = pathpoint4;
		}

		if (pathpoint5 != null && !pathpoint5.isFirst && pathpoint5.distanceTo(targetPoint) < maxDistance) {
			pathOptions[i++] = pathpoint5;
		}

		boolean flag = pathpoint3 == null || costMalusMap.getOrDefault(pathpoint3, 0.0F) > 0.0F;
		boolean flag1 = pathpoint == null || costMalusMap.getOrDefault(pathpoint, 0.0F) > 0.0F;
		boolean flag2 = pathpoint2 == null || costMalusMap.getOrDefault(pathpoint2, 0.0F) > 0.0F;
		boolean flag3 = pathpoint1 == null || costMalusMap.getOrDefault(pathpoint1, 0.0F) > 0.0F;
		boolean flag4 = pathpoint4 == null || costMalusMap.getOrDefault(pathpoint4, 0.0F) > 0.0F;
		boolean flag5 = pathpoint5 == null || costMalusMap.getOrDefault(pathpoint5, 0.0F) > 0.0F;

		if (flag && flag3) {
			PathPoint pathpoint6 = openPointFlying(entity, currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord - 1);

			if (pathpoint6 != null && !pathpoint6.isFirst && pathpoint6.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint6;
			}
		}

		if (flag && flag2) {
			PathPoint pathpoint7 = openPointFlying(entity, currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord - 1);

			if (pathpoint7 != null && !pathpoint7.isFirst && pathpoint7.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint7;
			}
		}

		if (flag1 && flag3) {
			PathPoint pathpoint8 = openPointFlying(entity, currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord + 1);

			if (pathpoint8 != null && !pathpoint8.isFirst && pathpoint8.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint8;
			}
		}

		if (flag1 && flag2) {
			PathPoint pathpoint9 = openPointFlying(entity, currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord + 1);

			if (pathpoint9 != null && !pathpoint9.isFirst && pathpoint9.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint9;
			}
		}

		if (flag && flag4) {
			PathPoint pathpoint10 = openPointFlying(entity, currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord - 1);

			if (pathpoint10 != null && !pathpoint10.isFirst && pathpoint10.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint10;
			}
		}

		if (flag1 && flag4) {
			PathPoint pathpoint11 = openPointFlying(entity, currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord + 1);

			if (pathpoint11 != null && !pathpoint11.isFirst && pathpoint11.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint11;
			}
		}

		if (flag2 && flag4) {
			PathPoint pathpoint12 = openPointFlying(entity, currentPoint.xCoord + 1, currentPoint.yCoord + 1, currentPoint.zCoord);

			if (pathpoint12 != null && !pathpoint12.isFirst && pathpoint12.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint12;
			}
		}

		if (flag3 && flag4) {
			PathPoint pathpoint13 = openPointFlying(entity, currentPoint.xCoord - 1, currentPoint.yCoord + 1, currentPoint.zCoord);

			if (pathpoint13 != null && !pathpoint13.isFirst && pathpoint13.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint13;
			}
		}

		if (flag && flag5) {
			PathPoint pathpoint14 = openPointFlying(entity, currentPoint.xCoord, currentPoint.yCoord - 1, currentPoint.zCoord - 1);

			if (pathpoint14 != null && !pathpoint14.isFirst && pathpoint14.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint14;
			}
		}

		if (flag1 && flag5) {
			PathPoint pathpoint15 = openPointFlying(entity, currentPoint.xCoord, currentPoint.yCoord - 1, currentPoint.zCoord + 1);

			if (pathpoint15 != null && !pathpoint15.isFirst && pathpoint15.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint15;
			}
		}

		if (flag2 && flag5) {
			PathPoint pathpoint16 = openPointFlying(entity, currentPoint.xCoord + 1, currentPoint.yCoord - 1, currentPoint.zCoord);

			if (pathpoint16 != null && !pathpoint16.isFirst && pathpoint16.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint16;
			}
		}

		if (flag3 && flag5) {
			PathPoint pathpoint17 = openPointFlying(entity, currentPoint.xCoord - 1, currentPoint.yCoord - 1, currentPoint.zCoord);

			if (pathpoint17 != null && !pathpoint17.isFirst && pathpoint17.distanceTo(targetPoint) < maxDistance) {
				pathOptions[i++] = pathpoint17;
			}
		}

		return i;
	}

	protected PathPoint openPointFlying(Entity entity, int x, int y, int z) {
		if (!(entity instanceof EntityCreature)) return super.openPoint(x, y, z);

		float f = ((EntityCreature) entity).getBlockPathWeight(x, y, z);
		PathPoint pathpoint = null;
//		Vec3 vec = ((EntityCreature) entity).getPosition(1.0F);
//		vec.yCoord += entity.getEyeHeight(); //Experimental code to not put a path point if it's obstructed; bees seem to enjoy sticking to walls even with this code.
		if (f >= 0.0F /*&& entity.worldObj.func_147447_a(vec, Vec3.createVectorHelper(x, y, z), false, true, false) == null*/) {
			pathpoint = super.openPoint(x, y, z);
			costMalusMap.put(pathpoint, f);
		}

		return pathpoint;
	}
}
