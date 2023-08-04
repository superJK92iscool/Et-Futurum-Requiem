package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCutCopperSlab extends BasicSlab implements IDegradable {

	public BlockCutCopperSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.iron, "cut_copper", "exposed_cut_copper", "weathered_cut_copper", "oxidized_cut_copper", "waxed_cut_copper", "waxed_exposed_cut_copper",
				"waxed_weathered_cut_copper", "waxed_oxidized_cut_copper");
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
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		setIcons(new IIcon[4]);
		for (int i = 0; i < getIcons().length; i++) {
			getIcons()[i] = "".equals(getTypes()[i]) ? reg.registerIcon(getTextureName()) : reg.registerIcon(getTypes()[i]);
		}
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
