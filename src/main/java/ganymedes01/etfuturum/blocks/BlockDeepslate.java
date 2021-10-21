package ganymedes01.etfuturum.blocks;

import java.util.Map.Entry;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockDeepslate extends BlockRotatedPillar implements IConfigurable {

	public BlockDeepslate() {
		super(Material.rock);
		this.setHardness(3);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate"));
		this.setBlockTextureName("deepslate");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		this.setStepSound(ConfigWorld.enableNewBlocksSounds ? ModSounds.soundDeepslate : soundTypeStone);
	}

	@Override
	protected IIcon getSideIcon(int p_150163_1_) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getTopIcon(int p_150161_1_)
	{
		return field_150164_N;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		blockIcon = p_149651_1_.registerIcon(getTextureName());
		field_150164_N = p_149651_1_.registerIcon(getTextureName() + "_top");
	}
	
	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(ModBlocks.cobbled_deepslate);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return this == target || target == Blocks.stone;
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableDeepslate;
	}

	public static void init() {
		if (ConfigBlocksItems.enableDeepslateOres) { //Copy block settings from deepslate base blocks
			for (Entry<BlockAndMetadataMapping, BlockAndMetadataMapping> entry : EtFuturum.deepslateOres.entrySet()) {
				Block oreNorm = entry.getKey().getOre();
				if (oreNorm == null || oreNorm == Blocks.air) continue;
				Block oreDeep = entry.getValue().getOre();
				if (oreDeep == null || oreDeep == Blocks.air) continue;
				
				ItemStack
				stackNorm = new ItemStack(oreNorm, 1, entry.getKey().getMeta()),
				stackDeep = new ItemStack(oreDeep, 1, entry.getValue().getMeta());
				
				if (((oreNorm instanceof IConfigurable) && !((IConfigurable)oreNorm).isEnabled()) || ((oreDeep instanceof IConfigurable) && !((IConfigurable)oreDeep).isEnabled()))
					return;
				for (int i : OreDictionary.getOreIDs(stackNorm)) {
					String oreName = OreDictionary.getOreName(i);
					for(int k = 0; k < OreDictionary.getOres(oreName).size(); k++) {
						if (ItemStack.areItemStacksEqual(OreDictionary.getOres(oreName).get(k), stackNorm)) {
							OreDictionary.registerOre(oreName.replace("Vanillastone", "Deepslate"), stackDeep.copy()); // Yes the .copy() is required!
						}
					}
				}
				
				if (FurnaceRecipes.smelting().getSmeltingResult(stackNorm) != null) {
					GameRegistry.addSmelting(stackDeep, FurnaceRecipes.smelting().getSmeltingResult(stackNorm), FurnaceRecipes.smelting().func_151398_b(stackNorm));
				}
			}
		}
	}
}
