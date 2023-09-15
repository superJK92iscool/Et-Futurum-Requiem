package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWoodButton extends BlockButtonWood {

	private final int meta;

	public BlockWoodButton(int meta) {
		this.meta = meta;
		setBlockName(Utils.getUnlocalisedName("button_" + BlockWoodDoor.names[meta]));
		setHardness(0.5F);
		setCreativeTab(EtFuturum.creativeTabBlocks);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int _meta) {
		return Blocks.planks.getIcon(side, this.meta);
	}

	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks && meta < 6;
	}

	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks && meta < 6 ? 20 : 0;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigFunctions.enableExtraBurnableBlocks && meta < 6 ? 5 : 0;
	}
}
