package ganymedes01.etfuturum.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockCopper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockCopper extends BlockGeneric implements IConfigurable, IDegradable {

	public BlockCopper() {
		super(Material.iron, "", "exposed", "weathered", "oxidized", "cut", "exposed_cut", "weathered_cut", "oxidized_cut", "waxed", "waxed_exposed", "waxed_weathered", "unused", "waxed_cut", "waxed_exposed_cut", "waxed_weathered_cut", "unused");
		setHardness(3);
		setResistance(6);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("copper_block"));
		setBlockTextureName("copper_block");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setFlippedNames(true);
		setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundCopper : Block.soundTypeMetal);
		setTickRandomly(true);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableCopper;
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

	private void tickDegradation(World world, int x, int y, int z, Random random) {
	   float f = 0.05688889F;
	   if (random.nextFloat() < f) {
		  this.tryDegrade(world, x, y, z, random);
	   }
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		boolean flag = false;
		boolean flag2 = false;
		int meta = world.getBlockMetadata(x, y, z);
		if(entityPlayer.getCurrentEquippedItem() != null) {
			ItemStack heldStack = entityPlayer.getCurrentEquippedItem();
			if(meta <= 7 && meta + 8 < 15 && meta + 8 != 11) {
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
			if(heldStack.getItem().getToolClasses(heldStack).contains("axe") && meta - 1 >= 0 && meta % 4 != (meta > 7 ? 4 : 0)) {
				heldStack.damageItem(1, entityPlayer);
				if(meta <= 7) {
					flag2 = true;
				} else {
					flag = true;
				}
			}
			if(flag && !flag2) {
				world.setBlock(x, y, z, this, meta + 8 > 15 ? meta - 8 : meta + 8, 2);
				BlockCopper.spawnParticles(world, x, y, z, false);
			} else if (!flag && flag2) {
				world.setBlock(x, y, z, this, meta - 1, 2);
				BlockCopper.spawnParticles(world, x, y, z, true);
			}
		}
		return flag || flag2;
	}
	
	public static void spawnParticles(World p_150186_1_, int p_150186_2_, int p_150186_3_, int p_150186_4_, boolean oxidize)
	{
		Random random = p_150186_1_.rand;
		double d0 = 0.0625D;

		for (int l = 0; l < 10; ++l)
		{
			double d1 = (double)((float)p_150186_2_ + random.nextFloat());
			double d2 = (double)((float)p_150186_3_ + random.nextFloat());
			double d3 = (double)((float)p_150186_4_ + random.nextFloat());

			if (l == 0 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube())
			{
				d2 = (double)(p_150186_3_ + 1) + d0;
			}

			if (l == 1 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube())
			{
				d2 = (double)(p_150186_3_ + 0) - d0;
			}

			if (l == 2 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube())
			{
				d3 = (double)(p_150186_4_ + 1) + d0;
			}

			if (l == 3 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube())
			{
				d3 = (double)(p_150186_4_ + 0) - d0;
			}

			if (l == 4 && !p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube())
			{
				d1 = (double)(p_150186_2_ + 1) + d0;
			}

			if (l == 5 && !p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube())
			{
				d1 = (double)(p_150186_2_ + 0) - d0;
			}

			if (d1 < (double)p_150186_2_ || d1 > (double)(p_150186_2_ + 1) || d2 < 0.0D || d2 > (double)(p_150186_3_ + 1) || d3 < (double)p_150186_4_ || d3 > (double)(p_150186_4_ + 1))
			{
				p_150186_1_.spawnParticle("happyVillager", d1, d2, d3, oxidize ? 1D : 0.0D, 0.0D, 0.0D);
			}
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
	
	public String getNameFor(int meta) {
		String name = types[Math.max(Math.min(meta, types.length - 1), 0)];
		return name.equals("unused") ? types[0] : name;
	}

	@Override
	public int damageDropped(int meta) {
		String name = types[Math.max(Math.min(meta, types.length - 1), 0)];
		return name.equals("unused") ? 0 : meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = startMeta; i < types.length; i++) {
			if(types[i].equals("unused"))
				continue;
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		int iconPos = Math.max(Math.min(meta, types.length - 1), startMeta);
		return icons[iconPos % 8] == null || types[iconPos].contains("unused") ? icons[0] : icons[iconPos % 8];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++) {
			if(i > 7)
				break;
			if ("unused".equals(types[i]))
				continue;
			if ("".equals(types[i])) {
				icons[i] = reg.registerIcon(getTextureName());
			} else {
				String name = types[i];
				String textName = getTextureName();
				if(i > 0) {
					textName = textName.replace("_block", "");
				}
				icons[i] = reg.registerIcon(name + "_" + textName);
			}
		}
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockCopper.class;
	}

	@Override
	public int getDegredationState(int meta) {
		return meta > 7 || meta % 4 == 3 ? -1 : meta % 4;
	}
}
