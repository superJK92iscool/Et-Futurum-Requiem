package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockButtonWood;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWoodButton extends BlockButtonWood implements IConfigurable {

	private final int meta;

	public BlockWoodButton(int meta) {
		this.meta = meta;
		setBlockName(Utils.getUnlocalisedName("button_" + BlockWoodDoor.names[meta]));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int _meta) {
		return Blocks.planks.getIcon(side, this.meta);
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableWoodRedstone;
	}
	
	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigBase.enableBurnableBlocks && meta < 6;
	}
	
	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigBase.enableBurnableBlocks && meta < 6 ? 20 : 0;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return ConfigBase.enableBurnableBlocks && meta < 6 ? 5 : 0;
	}
}
