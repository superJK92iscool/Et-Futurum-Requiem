package ganymedes01.etfuturum.items.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemEndPortal extends ItemBlock {
	
	private IIcon endPortalIcon;
	
	public ItemEndPortal(Block block) {
		super(block);
	}

    @SideOnly(Side.CLIENT)
    public int getSpriteNumber()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
    	super.registerIcons(p_94581_1_);
    	endPortalIcon = p_94581_1_.registerIcon("end_portal");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return field_150939_a == Blocks.end_portal ? endPortalIcon : super.getIconFromDamage(p_77617_1_);
    }
}
