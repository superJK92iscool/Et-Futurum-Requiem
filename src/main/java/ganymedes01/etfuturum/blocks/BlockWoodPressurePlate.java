package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWoodPressurePlate extends BlockPressurePlate {

	private final int meta;
	private final Block baseBlock;
	private final boolean flammable;

	public BlockWoodPressurePlate(String type, Block block, int meta, boolean flammable) {
		super(null, Material.wood, Sensitivity.everything);
		this.meta = meta;
		baseBlock = block;
		this.flammable = flammable;
		setBlockName(Utils.getUnlocalisedName(type + "_pressure_plate"));
		disableStats();
		setHardness(0.5F);
		setCreativeTab(EtFuturum.creativeTabBlocks);
		if (type.equals("crimson") || type.equals("warped")) {
			Utils.setBlockSound(this, ModSounds.soundNetherWood);
		} else if (type.equals("cherry")) {
			Utils.setBlockSound(this, ModSounds.soundCherryWood);
		} else {
			setStepSound(Block.soundTypeWood);
		}
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return baseBlock.getIcon(p_149691_1_, this.meta);
	}

	@Override
	public void registerBlockIcons(IIconRegister ignored) {
	}

	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks && flammable;
	}

	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks && flammable ? 20 : 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks && flammable ? 5 : 0;
	}
}