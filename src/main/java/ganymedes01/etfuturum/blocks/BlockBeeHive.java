package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.client.particle.ParticleHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityBee;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.tileentities.TileEntityBeeHive;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockBeeHive extends BlockContainer {
	@SideOnly(Side.CLIENT)
	protected IIcon bottomIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon topIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon frontIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon frontIconHoney;

	public BlockBeeHive() {
		super(Material.wood);
		setCreativeTab(EtFuturum.creativeTabBlocks);
		setStepSound(Block.soundTypeWood);
	}

	public BlockBeeHive setHiveType(String type, boolean efrprefix) {
		setBlockName(efrprefix ? Utils.getUnlocalisedName(type) : type);
		setBlockTextureName(type);
		if (type.contains("nest")) {
			setResistance(0.3F);
			setHardness(0.3F);
		} else {
			setResistance(0.6F);
			setHardness(0.6F);
		}
		return this;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int ordinal = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		switch (ordinal) {
			case 0:
				ordinal = 2;
				break;
			case 1:
				ordinal = 5;
				break;
			case 2:
				ordinal = 3;
				break;
			case 3:
				ordinal = 4;
				break;
		}
		world.setBlockMetadataWithNotify(x, y, z, ordinal, 2);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound compound = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			if (compound.hasKey("honeyLevel") && compound.getInteger("honeyLevel") == 5) {
				updateHiveState(world, x, y, z, true);
			}
			TileEntity te = world.getTileEntity(x, y, z);
			te.readFromNBT(compound);
			te.xCoord = x;
			te.yCoord = y;
			te.zCoord = z;
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntityBeeHive te = ((TileEntityBeeHive) world.getTileEntity(x, y, z));
		int meta = world.getBlockMetadata(x, y, z);
		if (meta > 6 && te.getHoneyLevel() != 5) {
			te.setHoneyLevel(5);
		}
		updateHiveState(world, x, y, z, te.getHoneyLevel() == 5);
		super.onBlockAdded(world, x, y, z);
	}

	public static void updateHiveState(World world, int x, int y, int z, boolean full) {
		if (world.getChunkFromBlockCoords(x, z).isChunkLoaded) {
			int meta = world.getBlockMetadata(x, y, z);
			if (full && meta < 6) {
				world.setBlockMetadataWithNotify(x, y, z, MathHelper.clamp_int(meta + 6, 8, 11), 2);
			} else if (!full && meta > 5) {
				world.setBlockMetadataWithNotify(x, y, z, MathHelper.clamp_int(meta - 6, 2, 5), 2);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBeeHive();
	}

	public boolean hasComparatorInputOverride() {
		return true;
	}

	public int getComparatorInputOverride(World world, int x, int y, int z, int p_149736_5_) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityBeeHive) {
			return ((TileEntityBeeHive) tile).getHoneyLevel();
		}
		return 0;
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		switch (side) {
			case 0:
				return bottomIcon;
			case 1:
				return topIcon;
			default:
				return (side == meta % 6) || (side == 3 && meta == 0) ? meta > 5 ? frontIconHoney : frontIcon : blockIcon;
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
		frontIcon = p_149651_1_.registerIcon(getTextureName() + "_front");
		frontIconHoney = p_149651_1_.registerIcon(getTextureName() + "_front_honey");
		if (getTextureName().contains("nest")) {
			topIcon = p_149651_1_.registerIcon(getTextureName() + "_top");
			bottomIcon = p_149651_1_.registerIcon(getTextureName() + "_bottom");
		} else {
			topIcon = bottomIcon = p_149651_1_.registerIcon(getTextureName() + "_end");
		}
	}

	public void harvestBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta) {
		TileEntity te = worldIn.getTileEntity(x, y, z);
		if (!worldIn.isRemote && te instanceof TileEntityBeeHive) {
			TileEntityBeeHive beehivetileentity = (TileEntityBeeHive) te;
			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, player.getHeldItem()) == 0) {
				beehivetileentity.angerBees(player, TileEntityBeeHive.State.EMERGENCY);
				this.angerNearbyBees(worldIn, x, y, z);
			}

//			CriteriaTriggers.BEE_NEST_DESTROYED.test((ServerEntityPlayer)player, state.getBlock(), stack, beehivetileentity.getBeeCount());
		}
		super.harvestBlock(worldIn, player, x, y, z, meta);
	}

	private void angerNearbyBees(World p_226881_1_, int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(8.0D, 6.0D, 8.0D);
		List<EntityBee> list = p_226881_1_.getEntitiesWithinAABB(EntityBee.class, box);
		if (!list.isEmpty()) {
			List<EntityPlayer> list1 = p_226881_1_.getEntitiesWithinAABB(EntityPlayer.class, box);
			int i = list1.size();

			for (EntityBee beeentity : list) {
				if (beeentity.getAttackTarget() == null) {
					beeentity.setBeeAttacker(list1.get(p_226881_1_.rand.nextInt(i)));
				}
			}
		}

	}

	private void dropHoneyComb(World worldIn, int x, int y, int z) {
		dropBlockAsItem(worldIn, x, y, z, ModItems.HONEYCOMB.newItemStack(3));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		ItemStack itemstack = player.getHeldItem();
		TileEntity te = world.getTileEntity(x, y, z);
		if (!(te instanceof TileEntityBeeHive)) return false;
		int i = ((TileEntityBeeHive) te).getHoneyLevel();
		boolean flag = false;
		if (i >= 5 && itemstack != null) {
			if (itemstack.getItem() instanceof ItemShears) {
				world.playSoundAtEntity(player, Reference.MCAssetVer + ":block.beehive.shear", 1.0F, 1.0F);
				dropHoneyComb(world, x, y, z);
				itemstack.damageItem(1, player);
				flag = true;
			} else if (itemstack.getItem() == Items.glass_bottle) {
				itemstack.stackSize--;
				world.playSoundAtEntity(player, Reference.MCAssetVer + ":item.bottle.fill", 1.0F, 1.0F);
				if (itemstack.stackSize == 0) {
					player.setCurrentItemOrArmor(0, ModItems.HONEY_BOTTLE.newItemStack());
				} else if (!player.inventory.addItemStackToInventory(ModItems.HONEY_BOTTLE.newItemStack())) {
					player.entityDropItem(ModItems.HONEY_BOTTLE.newItemStack(), 0);
				}

				flag = true;
			}
		}

		if (flag) {
			if (!TileEntityBeeHive.isLitCampfireBelow(world, x, y, z, 5)) {
				if (this.hasBees((TileEntityBeeHive) te)) {
					this.angerNearbyBees(world, x, y, z);
				}

				this.takeHoney((TileEntityBeeHive) te, player, TileEntityBeeHive.State.EMERGENCY);
			} else {
				this.takeHoney((TileEntityBeeHive) te);
//				if (player instanceof ServerEntityPlayer) {
//					CriteriaTriggers.SAFELY_HARVEST_HONEY.test((ServerEntityPlayer)player, pos, itemstack1);
//				}
			}
			updateHiveState(world, x, y, z, ((TileEntityBeeHive) te).getHoneyLevel() == 5);
			return true;
		} else {
			return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
		}
	}

	private boolean hasBees(TileEntityBeeHive hive) {
		return !hive.hasNoBees();
	}

	public void takeHoney(TileEntityBeeHive hive, EntityPlayer playerIn, TileEntityBeeHive.State stateIn) {
		this.takeHoney(hive);
		hive.angerBees(playerIn, stateIn);
	}

	public void takeHoney(TileEntityBeeHive hive) {
		hive.setHoneyLevel(0);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if (world.getBlockMetadata(x, y, z) > 5) {
			if (!world.getBlock(x, y - 1, z).isOpaqueCube()) {
				for (int i = 0; i < random.nextInt(1) + 1; ++i) {
					if (random.nextFloat() > 0.3F) {
						ParticleHandler.DRIP.spawn(world, world.rand.nextDouble() + x, y, world.rand.nextDouble() + z,
								0, 0, 0, 1F, Reference.MCAssetVer + ":block.beehive.drip", 0xFF8E6D14);
					}
				}
			}
		}
	}

	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		if (!world.isRemote && player.capabilities.isCreativeMode && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityBeeHive) {
				TileEntityBeeHive beehivetileentity = (TileEntityBeeHive) tileentity;
				ItemStack itemstack = new ItemStack(this);
				int i = beehivetileentity.getHoneyLevel();
				boolean flag = !beehivetileentity.hasNoBees();
				if (!flag && i == 0) {
					return;
				}

				NBTTagCompound compoundnbt = new NBTTagCompound();
				if (flag) {
					compoundnbt.setTag("Bees", beehivetileentity.getBees());
				}
				if (i > 0) {
					compoundnbt.setInteger("honeyLevel", i);
				}
				if (!compoundnbt.tagMap.isEmpty()) {
					itemstack.setTagInfo("BlockEntityTag", compoundnbt);
				}
				EntityItem itementity = new EntityItem(world, x, y, z, itemstack);
				itementity.delayBeforeCanPickup = 20;
				world.spawnEntityInWorld(itementity);
			}
		}

		super.onBlockHarvested(world, x, y, z, meta, player);
	}

	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		if (this.harvesters.get() == null) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityBeeHive) {
				((TileEntityBeeHive) te).angerBees(null, TileEntityBeeHive.State.EMERGENCY);
			}
		}
		return super.getDrops(world, x, y, z, meta, fortune);
	}

	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity instanceof TileEntityBeeHive) {
			EnumFacing facing = EnumFacing.getFront(meta % 6);
			if (world.getBlock(x + facing.getFrontOffsetX(), y, z + facing.getFrontOffsetZ()).getMaterial() == Material.fire) {
				((TileEntityBeeHive) tileentity).angerBees(null, TileEntityBeeHive.State.EMERGENCY);
			}
		}
		super.onPostBlockPlaced(world, x, y, z, meta);
	}
}
