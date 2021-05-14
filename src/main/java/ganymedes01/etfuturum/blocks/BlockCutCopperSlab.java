package ganymedes01.etfuturum.blocks;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockCutCopperSlab extends BlockGenericSlab implements IConfigurable, IDegradable {

	public BlockCutCopperSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.iron, "", "exposed", "weathered", "oxidized", "waxed", "waxed_exposed", "waxed_weathered");
		setHardness(3);
		setResistance(6);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("cut_copper_slab"));
		setBlockTextureName("cut_copper");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundCopper : Block.soundTypeMetal);
		setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (world.isRemote)
			return;
		int meta = world.getBlockMetadata(x, y, z);
		if (getDegredationState(meta) == -1)
			return;
		tickDegradation(world, x, y, z, rand);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		boolean flag = false;
		boolean flag2 = false;
		int meta = world.getBlockMetadata(x, y, z) % 8;
		if(entityPlayer.getCurrentEquippedItem() != null) {
			ItemStack heldStack = entityPlayer.getCurrentEquippedItem();
			if(meta <= 3 && meta + 4 < 7) {
				for(int oreID : OreDictionary.getOreIDs(heldStack)) {
					if((OreDictionary.doesOreNameExist("materialWax") || OreDictionary.doesOreNameExist("materialWaxcomb")) ?
							OreDictionary.getOreName(oreID).equals("materialWax") || OreDictionary.getOreName(oreID).equals("materialWaxcomb") :
								OreDictionary.getOreName(oreID).equals("slimeball")) {
						flag = true;
						
						if (!entityPlayer.capabilities.isCreativeMode && --heldStack.stackSize <= 0)
						{
							entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, (ItemStack)null);
						}
						
						entityPlayer.inventoryContainer.detectAndSendChanges();
						break;
					}
				}
			}
			if(heldStack.getItem().getToolClasses(heldStack).contains("axe") && meta - 1 >= 0 && ((meta % 4) != 0 || meta == 4)) {
				heldStack.damageItem(1, entityPlayer);
				if(meta <= 3) {
					flag2 = true;
				} else {
					flag = true;
				}
			}
			if(flag && !flag2) {
				world.setBlock(x, y, z, this, meta + 4 > 7 ? world.getBlockMetadata(x, y, z) - 4 : world.getBlockMetadata(x, y, z) + 4, 2);
				BlockCopper.spawnParticles(world, x, y, z, false);
			} else if (!flag && flag2) {
				world.setBlock(x, y, z, this, world.getBlockMetadata(x, y, z) - 1, 2);
				BlockCopper.spawnParticles(world, x, y, z, true);
			}
		}
		return flag || flag2;
	}

	private void tickDegradation(World world, int x, int y, int z, Random random) {
		float f = 0.05688889F;
		if (random.nextFloat() < f) {
			this.tryDegrade(world, x, y, z, random);
		}
	}

	private void tryDegrade(World world, int x, int y, int z, Random random) {
		   int i = getDegredationState(world.getBlockMetadata(x, y, z));
		   int j = 0;
		   int k = 0;
		   
		   for(int x1 = -4; x1 <= 4; x1++) {
			   for(int y1 = -4; y1 <= 4; y1++) {
				   for(int z1 = -4; z1 <= 4; z1++) {
					   Block block = world.getBlock(x1 + x, y1 + y, z1 + z);
					   if(block instanceof IDegradable && (x1 != 0 || y1 != 0 || z1 != 0) && Math.abs(x1) + Math.abs(y1) + Math.abs(z1) <= 4) {
						   int m = ((IDegradable)block).getDegredationState(world.getBlockMetadata(x1, y1, z1));
						   
						   if(m == -1)
							  continue;
						   
						   if (m < i) {
							  return;
						   }
				  
						   if (m > i) {
							  ++k;
						   } else {
							  ++j;
						   }
					   }
				   }
			   }
		   }

		   float f = (float)(k + 1) / (float)(k + j + 1);
		   float g = f * f * (i == 0 ? 0.75F : 1F);
		   if (random.nextFloat() < g) {
				  world.setBlock(x, y, z, this, world.getBlockMetadata(x, y, z) + 1, 2);
			   }
	 }

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableCopper;
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab)ModBlocks.cut_copper_slab, (BlockGenericSlab)ModBlocks.double_cut_copper_slab};
	}

	public String func_150002_b(int meta)
	{
		meta %= 8;
				
		if(meta >= metaBlocks.length) {
			meta = 0;
		}

		if(metaBlocks[meta].equals("")) {
			return super.getUnlocalizedName();
		} else {
			return "tile.etfuturum." + metaBlocks[meta] + "_" + super.getUnlocalizedName().split("tile.etfuturum.")[1];
		}
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		IIcon[] blocks = new IIcon[7];
		for(int i = 0; i < 7; i++) {
			blocks[i] = ModBlocks.copper_block.getIcon(side, (i % 4) + 4);
		};
		return blocks;
	}

	@Override
	public int getDegredationState(int meta) {
		return meta % 8 > 3 || meta % 4 == 3 ? -1 : meta % 4;
	}

}
