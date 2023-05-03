package ganymedes01.etfuturum.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.blocks.itemblocks.ItemBlockCopper;
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

public class BlockCopper extends BlockGeneric implements IConfigurable, IDegradable {

	public BlockCopper() {
		super(Material.iron, "", "exposed", "weathered", "oxidized", "cut", "exposed_cut", "weathered_cut", "oxidized_cut", "waxed", "waxed_exposed", "waxed_weathered", "waxed_oxidized", "waxed_cut", "waxed_exposed_cut", "waxed_weathered_cut", "waxed_oxidized_cut");
		setHardness(3);
		setResistance(6);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("copper_block"));
		setBlockTextureName("copper_block");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setFlippedNames(true);
		setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundCopper : Block.soundTypeMetal);
		setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		tickDegradation(world, x, y, z, rand);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		return tryWaxOnWaxOff(world, x, y, z, entityPlayer);
	}
	
	@Override
	public String getNameFor(int meta) {
		return types[Math.max(Math.min(meta, types.length), 0)];
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = startMeta; i < types.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		int iconPos = Math.max(Math.min(meta, types.length - 1), startMeta);
		return icons[iconPos % 8];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++) {
			if(i > 7)
				break;
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
	public int getCopperMeta(int meta) {
		return meta;
	}

	@Override
	public Block getCopperBlockFromMeta(int meta) {
		return this;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockCopper.class;
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableCopper;
	}
}
