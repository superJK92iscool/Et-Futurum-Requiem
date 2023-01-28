package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemDecorationWorkbench;
import ganymedes01.etfuturum.lib.GUIsID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSmithingTable extends Block implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;
	
	public BlockSmithingTable() {
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setHardness(2.5F);
		this.setHarvestLevel("axe", 0);
		this.setResistance(2.5F);
		this.setBlockName(Utils.getUnlocalisedName("smithing_table"));
		this.setBlockTextureName("smithing_table");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return p_149691_1_ == 1 ? this.topIcon : (p_149691_1_ == 0 ? bottomIcon : (p_149691_1_ != 2 && p_149691_1_ != 3 ? this.blockIcon : this.sideIcon));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_front");
		this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableSmithingTable;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
		if (!world.isRemote)
			player.openGui(EtFuturum.instance, GUIsID.SMITHING_TABLE, world, x, y, z);
		return true;
	}
}
