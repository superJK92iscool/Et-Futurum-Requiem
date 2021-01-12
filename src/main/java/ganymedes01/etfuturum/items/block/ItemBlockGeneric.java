package ganymedes01.etfuturum.items.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.blocks.BlockGeneric;
import ganymedes01.etfuturum.blocks.BlockGenericSand;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockGeneric extends ItemBlock {

    public ItemBlockGeneric(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return Block.getBlockFromItem(this).getIcon(2, p_77617_1_);
    }
    
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (field_150939_a instanceof BlockGeneric || field_150939_a instanceof BlockGenericSand) {
			String name;
			if(field_150939_a instanceof BlockGenericSand)
				name = ((BlockGenericSand) field_150939_a).getNameFor(stack.getItemDamage());
			else
				name = ((BlockGeneric) field_150939_a).getNameFor(stack.getItemDamage());
			if ("".equals(name))
				return getUnlocalizedName();
			else
				return getUnlocalizedName() + "_" + name;
		}

		return getUnlocalizedName() + "_" + stack.getItemDamage();
	}

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}