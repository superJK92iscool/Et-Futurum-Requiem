package ganymedes01.etfuturum.items.block;

import ganymedes01.etfuturum.blocks.BlockGlazedTerracotta;
import ganymedes01.etfuturum.client.renderer.tileentity.TileEntityShulkerBoxRenderer;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShulkerBox extends ItemBlock {

	public ItemShulkerBox(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
	
	@Override
    public int getMetadata(int i)
    {
        return field_150939_a.damageDropped(i);
    }
	
	@Override
    public String getUnlocalizedName(ItemStack stack)
    {
    	String string = field_150939_a.getUnlocalizedName().substring(15);
    	if(stack.getItemDamage() % TileEntityShulkerBoxRenderer.tiers.length > 0) {
    		string = TileEntityShulkerBoxRenderer.tiers[(stack.getItemDamage() - 1) % TileEntityShulkerBoxRenderer.tiers.length] + "_" + string;
    	}
    	if(stack.hasTagCompound()) {
        	if(stack.getTagCompound().hasKey("Color") && stack.getTagCompound().getByte("Color") > 0) {
        		String dye = BlockGlazedTerracotta.subBlock[stack.getTagCompound().getByte("Color") - 1 % BlockGlazedTerracotta.subBlock.length];
        		string = dye + "_" + string;
        	}
    	}
        return "tile.etfuturum." + string.toString();
    }
	
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {

       if (!world.setBlock(x, y, z, field_150939_a, metadata, 3))
       {
           return false;
       }

       if (world.getBlock(x, y, z) == field_150939_a)
       {
           field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
           field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
       }

		TileEntityShulkerBox box = (TileEntityShulkerBox) world.getTileEntity(x, y, z);
		box.facing = (byte) side;
       return true;
    }
}
