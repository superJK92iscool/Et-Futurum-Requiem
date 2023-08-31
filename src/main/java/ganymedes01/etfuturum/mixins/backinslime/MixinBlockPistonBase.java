package ganymedes01.etfuturum.mixins.backinslime;

import com.google.common.collect.Lists;
import ganymedes01.etfuturum.api.PistonBehaviorRegistry;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Code ported from Back in Slime, with DonBruce64's permission. This code remains mostly unchanged from the original version.
 */
@Mixin(BlockPistonBase.class)
public class MixinBlockPistonBase extends Block {

	@Shadow
	@Final
	private boolean isSticky;

	@Shadow
	private boolean isIndirectlyPowered(World p_150072_1_, int p_150072_2_, int p_150072_3_, int p_150072_4_, int p_150072_5_) {
		return false;
	}

	@Unique
	private List<Pair<Block, Integer>> etfuturum$pushedBlockList = Lists.newArrayList();
	@Unique
	private List<BlockPos> etfuturum$pushedBlockPosList = Lists.newArrayList();

	protected MixinBlockPistonBase(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Inject(method = "<init>", at = @At(value = "RETURN"))
	private void setupLists(boolean p_i45443_1_, CallbackInfo ci) {
		//TODO, list for sticky blocks here
	}

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @author
	 * @reason
	 */
	@Overwrite
	private void updatePistonState(World world, int x, int y, int z) {
		int pistonMetadata = world.getBlockMetadata(x, y, z);
		int side = BlockPistonBase.getPistonOrientation(pistonMetadata);
		boolean isPowered = this.isIndirectlyPowered(world, x, y, z, side);

		ForgeDirection dir = ForgeDirection.getOrientation(side);
		int xoffset = dir.offsetX;
		int yoffset = dir.offsetY;
		int zoffset = dir.offsetZ;
		int xoffset2 = dir.offsetX * 2;
		int yoffset2 = dir.offsetY * 2;
		int zoffset2 = dir.offsetZ * 2;
		int oppositeSide = dir.getOpposite().ordinal();
		if (isPowered && !BlockPistonBase.isExtended(pistonMetadata)) {
			etfuturum$clearBlockLists();
			if (etfuturum$getPushableBlocks(world, x + xoffset, y + yoffset, z + zoffset, oppositeSide, side, x, y, z) <= 12) {
				world.addBlockEvent(x, y, z, this, 1, side); //push piston
			}
		} else if (!isPowered && BlockPistonBase.isExtended(pistonMetadata)) {
			if (this.isSticky) {
				etfuturum$clearBlockLists();
				if (etfuturum$getPushableBlocks(world, x + xoffset2, y + yoffset2, z + zoffset2, oppositeSide, oppositeSide, x + xoffset, y + yoffset, z + zoffset) > 12) {
					return;
				}
			}
			world.addBlockEvent(x, y, z, this, 0, side); //pull piston
		}
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	public boolean onBlockEventReceived(World world, int x, int y, int z, int extend, int side) {
		if (!world.isRemote) {
			boolean hasPower = this.isIndirectlyPowered(world, x, y, z, side);
			if (hasPower && extend == 0) {
				world.setBlockMetadataWithNotify(x, y, z, side | 8, 2);
				return false;
			}
			if (!hasPower && extend == 1) {
				return false;
			}
		}

		ForgeDirection dir = ForgeDirection.getOrientation(side);
		int xoffset = dir.offsetX;
		int yoffset = dir.offsetY;
		int zoffset = dir.offsetZ;
		int xoffset2 = dir.offsetX * 2;
		int yoffset2 = dir.offsetY * 2;
		int zoffset2 = dir.offsetZ * 2;
		int oppositeSide = dir.getOpposite().ordinal();
		if (extend == 0) {
			TileEntity tileentity = world.getTileEntity(x + xoffset, y + yoffset, z + zoffset);
			if (tileentity instanceof TileEntityPiston) {
				((TileEntityPiston) tileentity).clearPistonTileEntity();
			}
			world.setBlock(x, y, z, Blocks.piston_extension, side, 3);
			world.setTileEntity(x, y, z, BlockPistonMoving.getTileEntity(this, side, side, false, true));

			Block blockToPull = world.getBlock(x + xoffset2, y + yoffset2, z + zoffset2);
			int metaToPull = world.getBlockMetadata(x + xoffset2, y + yoffset2, z + zoffset2);

			if (this.isSticky && !PistonBehaviorRegistry.isNonStickyBlock(blockToPull, metaToPull)) {
				etfuturum$clearBlockLists();
				if (etfuturum$getPushableBlocks(world, x + xoffset2, y + yoffset2, z + zoffset2, oppositeSide, oppositeSide, x + xoffset, y + yoffset, z + zoffset) == 0) {
					world.setBlockToAir(x + xoffset, y + yoffset, z + zoffset);
				} else {
					etfuturum$pushBlocks(world, oppositeSide, false);
				}
			}
			world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.15F + 0.6F);
		} else if (extend == 1) {
			etfuturum$clearBlockLists();
			etfuturum$getPushableBlocks(world, x + xoffset, y + yoffset, z + zoffset, oppositeSide, side, x, y, z);
			etfuturum$pushBlocks(world, side, true);
			boolean flag1 = world.setBlock(x + xoffset, y + yoffset, z + zoffset, Blocks.piston_extension, side | (this.isSticky ? 8 : 0), 4);
			world.setTileEntity(x + xoffset, y + yoffset, z + zoffset, BlockPistonMoving.getTileEntity(Blocks.piston_head, side | (this.isSticky ? 8 : 0), side, true, false));
			//world.notifyBlocksOfNeighborChange(x + xoffset, y + yoffset, z + zoffset, BIS.slimePistonHead);
			boolean flag2 = world.setBlockMetadataWithNotify(x, y, z, side | 8, 2);
			if (flag1 || flag2) {
				world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
			}
		}
		return true;
	}

	//TODO: Find a way to make the above two functions use proper injects (idk how atm)

	/**
	 * Recursive, internal method to calculate the blocks that constitute a piston system.
	 * This includes all attached slime blocks.  It populates the pushedBlockCoordinate list and pushingBlockCoordinate list,
	 * so use those to determine what blocks need pushing/ are pushers.
	 *
	 * @param world      The current world
	 * @param x          X-coordinate of push chain
	 * @param y          Y-coordinate of push chain
	 * @param z          Z-coordinate of push chain
	 * @param ignoreSide Side to ignore looking for blocks.  Used when encountering a slimeblock.
	 * @param side       Direction piston or block is facing, determines extension direction
	 * @return The number of blocks that will be pushed by this piston system.
	 */
	@Unique
	private int etfuturum$getPushableBlocks(World world, int x, int y, int z, int ignoreSide, final int side, final int pistonX, final int pistonY, final int pistonZ) {
		Block pushedBlock = world.getBlock(x, y, z);
		int pushedBlockX = x;
		int pushedBlockY = y;
		int pushedBlockZ = z;
		int pushedBlockMeta = world.getBlockMetadata(pushedBlockX, pushedBlockY, pushedBlockZ);
		int blocksPushed = 0;

		while (blocksPushed < 13) {
			if (pushedBlock.isAir(world, pushedBlockX, pushedBlockY, pushedBlockZ)) {
				return blocksPushed;
			} else if ((pushedBlockY == 0 && side == 0) || (pushedBlockY >= world.getHeight() - 1 && side == 1)) {
				return 13;
			} else if (!etfuturum$canPushBlockNested(pushedBlock, world, pushedBlockX, pushedBlockY, pushedBlockZ, side, side)) {
				if (pushedBlockX == pistonX && pushedBlockY == pistonY && pushedBlockZ == pistonZ) {
					return blocksPushed;
				} else {
					return 13;
				}
			}

			BlockPos pushedBlockCoords = new BlockPos(pushedBlockX, pushedBlockY, pushedBlockZ);
			for (BlockPos pushedBlockPos : etfuturum$pushedBlockPosList) {
				if (pushedBlockPos.equals(pushedBlockCoords)) {
					return blocksPushed;
				}
			}

			++blocksPushed;
			etfuturum$pushedBlockList.add(Pair.of(pushedBlock, pushedBlockMeta));
			etfuturum$pushedBlockPosList.add(pushedBlockCoords);

			if (PistonBehaviorRegistry.isStickyBlock(pushedBlock, pushedBlockMeta)) {
				for (ForgeDirection dir : ForgeDirection.values()) {
					if (dir.ordinal() != side && dir.ordinal() != ignoreSide) {
						int attachedX = pushedBlockX + dir.offsetX;
						int attachedY = pushedBlockY + dir.offsetY;
						int attachedZ = pushedBlockZ + dir.offsetZ;
//						int attachedMeta=world.getBlockMetadata(attachedX, attachedY, attachedZ);
						Block attachedBlock = world.getBlock(attachedX, attachedY, attachedZ);

						if (!(attachedX == pistonX && attachedY == pistonY && attachedZ == pistonZ)) {
							if (etfuturum$canPushBlockNested(attachedBlock, world, attachedX, attachedY, attachedZ, dir.ordinal(), side)) {
								if (attachedBlock.getMobilityFlag() != 1) {
									blocksPushed += etfuturum$getPushableBlocks(world, attachedX, attachedY, attachedZ, dir.getOpposite().ordinal(), side, pistonX, pistonY, pistonZ);
								}
							}
						}
					}
				}
			}

			pushedBlockX += ForgeDirection.getOrientation(side).offsetX;
			pushedBlockY += ForgeDirection.getOrientation(side).offsetY;
			pushedBlockZ += ForgeDirection.getOrientation(side).offsetZ;
			pushedBlockMeta = world.getBlockMetadata(pushedBlockX, pushedBlockY, pushedBlockZ);
			pushedBlock = world.getBlock(pushedBlockX, pushedBlockY, pushedBlockZ);
		}
		return blocksPushed;
	}

	/**
	 * Pushes all blocks in the pushedBlocks list and pushingBlocks list.
	 * Also launches entities if needed
	 *
	 * @param world     World
	 * @param side      Side the block is moving towards.
	 * @param extending Whether the piston is extending or retracting
	 */
	@Unique
	private void etfuturum$pushBlocks(World world, int side, boolean extending) {
		boolean needsPusher;
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		int xoffset = dir.offsetX;
		int yoffset = dir.offsetY;
		int zoffset = dir.offsetZ;
		List<BlockPos> removedBlockCoords = Lists.newArrayList();
		List<Entity> launchedEntityList = Lists.newArrayList();
		List<Entity> pulledEntityList = Lists.newArrayList();

		for (int i = 0; i < etfuturum$pushedBlockList.size(); ++i) {
			needsPusher = true;
			Block block = etfuturum$pushedBlockList.get(i).getLeft();
			int blockMeta = etfuturum$pushedBlockList.get(i).getRight();
			BlockPos pos = etfuturum$pushedBlockPosList.get(i);
			BlockPos rearPos = new BlockPos(pos.getX() - xoffset, pos.getY() - yoffset, pos.getZ() - zoffset);
			int blockX = pos.getX();
			int blockY = pos.getY();
			int blockZ = pos.getZ();

			for (BlockPos pushedBlockPos : etfuturum$pushedBlockPosList) {
				if (pushedBlockPos.equals(rearPos)) {
					needsPusher = false;
					break;
				}
			}
			if (needsPusher) {
				removedBlockCoords.add(etfuturum$pushedBlockPosList.get(i));
			}

			blockX += xoffset;
			blockY += yoffset;
			blockZ += zoffset;
			if (block.getMobilityFlag() == 1) {
				float chance = block instanceof BlockSnow ? -1.0f : 1.0f;
				block.dropBlockAsItemWithChance(world, blockX, blockY, blockZ, blockMeta, chance, 0);
				world.setBlockToAir(blockX, blockY, blockZ);
			} else {
				world.setBlock(blockX, blockY, blockZ, Blocks.piston_extension, blockMeta, 4);
				world.setTileEntity(blockX, blockY, blockZ, BlockPistonMoving.getTileEntity(block, blockMeta, side, true, false));
				world.notifyBlocksOfNeighborChange(blockX, blockY, blockZ, block);
			}

			if (extending && PistonBehaviorRegistry.bouncesEntities(block, blockMeta)) {
				for (Entity o : (List<Entity>) world.getEntitiesWithinAABBExcludingEntity(null, this.getCollisionBoundingBoxFromPool(world, blockX, blockY, blockZ))) {
					if (!launchedEntityList.contains(o)) {
						launchedEntityList.add(o);
					}
				}
			} else if (side > 1 && PistonBehaviorRegistry.pullsEntities(block, blockMeta)) {
				for (Entity o : (List<Entity>) world.getEntitiesWithinAABBExcludingEntity(null, this.getCollisionBoundingBoxFromPool(world, blockX, blockY, blockZ))) {
					if (!pulledEntityList.contains(o)) {
						pulledEntityList.add(o);
					}
				}
			}
		}

		for (Entity entity : launchedEntityList) {
			entity.motionX += xoffset * 1.1F;
			entity.motionY += yoffset * 1.1F;
			entity.motionZ += zoffset * 1.1F;
		}
		for (Entity entity : pulledEntityList) {
			entity.motionX += xoffset * .4F;
			entity.posY += .15F; //Stops the entity falling through the block
			entity.motionZ += zoffset * .4F;
		}

		for (BlockPos blockCoords : removedBlockCoords) {
			world.setBlockToAir(blockCoords.getX(), blockCoords.getY(), blockCoords.getZ());
			world.notifyBlocksOfNeighborChange(blockCoords.getX(), blockCoords.getY(), blockCoords.getZ(), Blocks.air);
		}
	}

	@Unique
	private boolean etfuturum$canPushBlockNested(Block pushedBlock, World world, int x, int y, int z, int pushedSide, int sideToPushTo) {
		int pushedMeta = world.getBlockMetadata(x, y, z);
		if (sideToPushTo != pushedSide) {
			if (PistonBehaviorRegistry.isNonStickyBlock(pushedBlock, pushedMeta)) {
				return false;
			}

			int xoffset = ForgeDirection.getOrientation(pushedSide).getOpposite().offsetX;
			int yoffset = ForgeDirection.getOrientation(pushedSide).getOpposite().offsetY;
			int zoffset = ForgeDirection.getOrientation(pushedSide).getOpposite().offsetZ;
			Block stuckToBlock = world.getBlock(x + xoffset, y + yoffset, z + zoffset);
			int stuckToMeta = world.getBlockMetadata(x + xoffset, y + yoffset, z + zoffset);

			if (PistonBehaviorRegistry.isStickyBlock(pushedBlock, pushedMeta) && PistonBehaviorRegistry.isStickyBlock(stuckToBlock, stuckToMeta) && (pushedBlock != stuckToBlock || pushedMeta != stuckToMeta)) {
				return false;
			}
		}
		if (pushedBlock == Blocks.obsidian) {
			return false;
		}

		if (pushedBlock != Blocks.piston && pushedBlock != Blocks.sticky_piston) {
			if (pushedBlock.getBlockHardness(world, x, y, z) == -1.0F || pushedBlock.getMobilityFlag() == 2) {
				return false;
			} else if (pushedBlock.getMobilityFlag() == 1) {
				return pushedSide == sideToPushTo || pushedSide == ForgeDirection.OPPOSITES[sideToPushTo];
			}
		} else {
			return !BlockPistonBase.isExtended(world.getBlockMetadata(x, y, z));
		}
		return !(world.getBlock(x, y, z).hasTileEntity(world.getBlockMetadata(x, y, z)));
	}

	@Unique
	private void etfuturum$clearBlockLists() {
		etfuturum$pushedBlockList.clear();
		etfuturum$pushedBlockPosList.clear();
	}
}
