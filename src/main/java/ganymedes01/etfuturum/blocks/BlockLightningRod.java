package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.tileentities.TileEntityLightningRod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLightningRod extends Block implements IConfigurable {

	public BlockLightningRod() {
	super(Material.iron);
	setHardness(3);
	setResistance(6);
	setHarvestLevel("pickaxe", 1);
	setBlockName(Utils.getUnlocalisedName("lightning_rod"));
	setBlockTextureName("lightning_rod");
	setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundCopper : Block.soundTypeMetal);
	setTickRandomly(true);
	}

	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityLightningRod();
	}
	
	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
