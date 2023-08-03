package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCutCopperSlab extends BasicVariantsSlab implements IDegradable {

	public BlockCutCopperSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.iron, "", "exposed", "weathered", "oxidized", "waxed", "waxed_exposed", "waxed_weathered", "waxed_oxidized");
		setHardness(3);
		setResistance(6);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("cut_copper_slab"));
		setBlockTextureName("cut_copper");
		setCreativeTab(EtFuturum.creativeTabBlocks);
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
	public BasicVariantsSlab[] getSlabTypes() {
		return new BasicVariantsSlab[]{(BasicVariantsSlab) ModBlocks.CUT_COPPER_SLAB.get(), (BasicVariantsSlab) ModBlocks.DOUBLE_CUT_COPPER_SLAB.get()};
	}

	@Override
	public String func_150002_b(int meta)
	{
		meta %= 8;
		
		if(metaBlocks[meta].equals("")) {
			return super.getUnlocalizedName();
		}
		
		return "tile.etfuturum." + metaBlocks[meta] + "_" + super.getUnlocalizedName().split("tile.etfuturum.")[1];
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		IIcon[] blocks = new IIcon[8];
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = ModBlocks.COPPER_BLOCK.get().getIcon(side, (i % 4) + 4);
		}
		return blocks;
	}
	
	@Override
	public int getFinalCopperMeta(int copperMeta, int worldMeta) {
		return (copperMeta - (copperMeta > 11 ? 8 : 4)) + (worldMeta > 7 ? 8 : 0);
	}

	public int getCopperMeta(int meta) {
		return meta % 8 + (meta % 8 < 4 ? 4 : 8);
	}
	
	public Block getCopperBlockFromMeta(int meta) {
		return this;
	}

}
