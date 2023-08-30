package ganymedes01.etfuturum.mixins.backinslime;

import com.google.common.collect.Lists;
import ganymedes01.etfuturum.api.PistonBehaviorRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
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
	private List<Block> etfuturum$pushedBlockList = Lists.newArrayList();
	@Unique
	private List<int[]> etfuturum$pushedBlockData = Lists.newArrayList();

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
		if (isPowered && !BlockPistonBase.isExtended(pistonMetadata)) {
			etfuturum$clearBlockLists();
			if (etfuturum$getPushableBlocks(world, x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side], Facing.oppositeSide[side], side, x, y, z) <= 12) {
				world.addBlockEvent(x, y, z, this, 1, side); //push piston
			}
		} else if (!isPowered && BlockPistonBase.isExtended(pistonMetadata)) {
			if (this.isSticky) {
				etfuturum$clearBlockLists();
				if (etfuturum$getPushableBlocks(world, x + Facing.offsetsXForSide[side] * 2, y + Facing.offsetsYForSide[side] * 2, z + Facing.offsetsZForSide[side] * 2, Facing.oppositeSide[side], Facing.oppositeSide[side], x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side]) > 12) {
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

		if (extend == 0) {
			TileEntity tileentity = world.getTileEntity(x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side]);
			if (tileentity instanceof TileEntityPiston) {
				((TileEntityPiston) tileentity).clearPistonTileEntity();
			}
			world.setBlock(x, y, z, Blocks.piston_extension, side, 3);
			world.setTileEntity(x, y, z, BlockPistonMoving.getTileEntity(this, side, side, false, true));

			int xoffset = ForgeDirection.getOrientation(side).offsetX * 2;
			int yoffset = ForgeDirection.getOrientation(side).offsetY * 2;
			int zoffset = ForgeDirection.getOrientation(side).offsetZ * 2;
			Block blockToPull = world.getBlock(x + xoffset, y + yoffset, z + zoffset);
			int metaToPull = world.getBlockMetadata(x + xoffset, y + yoffset, z + zoffset);

			if (this.isSticky && !PistonBehaviorRegistry.isNonStickyBlock(blockToPull, metaToPull)) {
				etfuturum$clearBlockLists();
				if (etfuturum$getPushableBlocks(world, x + Facing.offsetsXForSide[side] * 2, y + Facing.offsetsYForSide[side] * 2, z + Facing.offsetsZForSide[side] * 2, Facing.oppositeSide[side], Facing.oppositeSide[side], x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side]) == 0) {
					world.setBlockToAir(x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side]);
				} else {
					etfuturum$pushBlocks(world, Facing.oppositeSide[side], false);
				}
			}
			world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.15F + 0.6F);
		} else if (extend == 1) {
			etfuturum$clearBlockLists();
			etfuturum$getPushableBlocks(world, x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side], Facing.oppositeSide[side], side, x, y, z);
			etfuturum$pushBlocks(world, side, true);
			world.setBlock(x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side], Blocks.piston_extension, side | (this.isSticky ? 8 : 0), 4);
			world.setTileEntity(x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side], BlockPistonMoving.getTileEntity(Blocks.piston_head, side | (this.isSticky ? 8 : 0), side, true, false));
			//world.notifyBlocksOfNeighborChange(x + Facing.offsetsXForSide[side], y + Facing.offsetsYForSide[side], z + Facing.offsetsZForSide[side], BIS.slimePistonHead);
			world.setBlockMetadataWithNotify(x, y, z, side | 8, 2);
			world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
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

			int[] pushedBlockCoords = new int[]{pushedBlockX, pushedBlockY, pushedBlockZ, pushedBlockMeta};
			for (int[] etfuturum$pushedBlockDatum : etfuturum$pushedBlockData) {
				if (Arrays.equals(etfuturum$pushedBlockDatum, pushedBlockCoords)) {
					return blocksPushed;
				}
			}

			++blocksPushed;
			etfuturum$pushedBlockList.add(pushedBlock);
			etfuturum$pushedBlockData.add(pushedBlockCoords);

			if (PistonBehaviorRegistry.isStickyBlock(pushedBlock, pushedBlockMeta)) {
				for (int i = 0; i < 6; ++i) {
					if (i != side && i != ignoreSide) {
						int attachedX = pushedBlockX + Facing.offsetsXForSide[i];
						int attachedY = pushedBlockY + Facing.offsetsYForSide[i];
						int attachedZ = pushedBlockZ + Facing.offsetsZForSide[i];
//						int attachedMeta=world.getBlockMetadata(attachedX, attachedY, attachedZ);
						Block attachedBlock = world.getBlock(attachedX, attachedY, attachedZ);

						if (!(attachedX == pistonX && attachedY == pistonY && attachedZ == pistonZ)) {
							if (etfuturum$canPushBlockNested(attachedBlock, world, attachedX, attachedY, attachedZ, i, side)) {
								if (attachedBlock.getMobilityFlag() != 1) {
									blocksPushed += etfuturum$getPushableBlocks(world, attachedX, attachedY, attachedZ, Facing.oppositeSide[i], side, pistonX, pistonY, pistonZ);
								}
							}
						}
					}
				}
			}

			pushedBlockX += Facing.offsetsXForSide[side];
			pushedBlockY += Facing.offsetsYForSide[side];
			pushedBlockZ += Facing.offsetsZForSide[side];
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
		int blockX;
		int blockY;
		int blockZ;
		int blockMeta;
		int[] rearCoords;
		Block block;
		List<int[]> removedBlockCoords = new ArrayList<>();
		List<Entity> launchedEntityList = new ArrayList<>();
		List<Entity> pulledEntityList = new ArrayList<>();

		for (int i = 0; i < etfuturum$pushedBlockList.size(); ++i) {
			needsPusher = true;
			block = etfuturum$pushedBlockList.get(i);
			blockX = etfuturum$pushedBlockData.get(i)[0];
			blockY = etfuturum$pushedBlockData.get(i)[1];
			blockZ = etfuturum$pushedBlockData.get(i)[2];
			blockMeta = etfuturum$pushedBlockData.get(i)[3];
			rearCoords = new int[]{blockX - Facing.offsetsXForSide[side], blockY - Facing.offsetsYForSide[side], blockZ - Facing.offsetsZForSide[side]};

			for (int[] etfuturum$pushedBlockDatum : etfuturum$pushedBlockData) {
				if (rearCoords[0] == etfuturum$pushedBlockDatum[0] && rearCoords[1] == etfuturum$pushedBlockDatum[1] && rearCoords[2] == etfuturum$pushedBlockDatum[2]) {
					needsPusher = false;
					break;
				}
			}
			if (needsPusher) {
				removedBlockCoords.add(etfuturum$pushedBlockData.get(i));
			}

			blockX += Facing.offsetsXForSide[side];
			blockY += Facing.offsetsYForSide[side];
			blockZ += Facing.offsetsZForSide[side];
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
			entity.motionX += Facing.offsetsXForSide[side] * 1.1F;
			entity.motionY += Facing.offsetsYForSide[side] * 1.1F;
			entity.motionZ += Facing.offsetsZForSide[side] * 1.1F;
		}
		for (Entity entity : pulledEntityList) {
			entity.motionX += Facing.offsetsXForSide[side] * .4F;
			entity.posY += .15F; //Stops the entity falling through the block
			entity.motionZ += Facing.offsetsZForSide[side] * .4F;
		}

		for (int[] blockCoords : removedBlockCoords) {
			world.setBlockToAir(blockCoords[0], blockCoords[1], blockCoords[2]);
			world.notifyBlocksOfNeighborChange(blockCoords[0], blockCoords[1], blockCoords[2], Blocks.air);
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
				return pushedSide == sideToPushTo || pushedSide == Facing.oppositeSide[sideToPushTo];
			}
		} else {
			return !BlockPistonBase.isExtended(world.getBlockMetadata(x, y, z));
		}
		return !(world.getBlock(x, y, z).hasTileEntity(world.getBlockMetadata(x, y, z)));
	}

	@Unique
	private void etfuturum$clearBlockLists() {
		etfuturum$pushedBlockList.clear();
		etfuturum$pushedBlockData.clear();
	}
}
