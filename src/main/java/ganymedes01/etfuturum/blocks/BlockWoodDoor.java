package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockWoodDoor extends BlockDoor {

	public static final String[] names = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "dark_oak"};

	public BlockWoodDoor(int meta) {
		super(Material.wood);
		String name = names[meta];

		disableStats();
		setHardness(3.0F);
		setStepSound(soundTypeWood);
		setBlockTextureName(name + "_door");
		setBlockName(Utils.getUnlocalisedName(name + "_door"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return (meta & 8) != 0 ? null : Item.getItemFromBlock(this);
	}

	@Override
	public int getRenderType() {
		return RenderIDs.DOOR;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {
		return getTextureName();
	}


	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		field_150017_a = new IIcon[2];
		field_150016_b = new IIcon[2];
		field_150017_a[0] = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		field_150016_b[0] = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
		field_150017_a[1] = new IconFlipped(this.field_150017_a[0], true, false);
		field_150016_b[1] = new IconFlipped(this.field_150016_b[0], true, false);
	}
}