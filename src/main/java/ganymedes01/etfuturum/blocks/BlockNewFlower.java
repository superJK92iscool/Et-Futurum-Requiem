package ganymedes01.etfuturum.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.items.block.ItemBlockFlower;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockNewFlower extends BlockFlower implements ISubBlocksBlock {

    public BlockNewFlower() {
        super(1);
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = reg.registerIcon(getTextureName());
    }
    
	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockFlower.class;
	}
}
