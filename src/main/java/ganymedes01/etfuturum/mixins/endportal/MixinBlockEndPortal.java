package ganymedes01.etfuturum.mixins.endportal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = BlockEndPortal.class)
public class MixinBlockEndPortal extends Block {
	
	protected MixinBlockEndPortal(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Overwrite
	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {}
	
	@Overwrite
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, .75F, 1.0F);
	}
	
	@SideOnly(Side.CLIENT)
	@Overwrite
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return Item.getItemFromBlock(this);
	}

	@SideOnly(Side.CLIENT)
	@Overwrite
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = Blocks.obsidian.getIcon(0, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return "end_portal";
	}

}
