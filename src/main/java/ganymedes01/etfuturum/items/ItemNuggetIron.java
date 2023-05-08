package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemNuggetIron extends Item {

	public ItemNuggetIron() {
		super();
		setTextureName("iron_nugget");
		setUnlocalizedName(Utils.getUnlocalisedName("nugget_iron"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}
	
//    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
//    {
//      if(p_77648_3_.isRemote) return false;
//      WorldGenFossil geode = new WorldGenFossil();
//          geode.generate(p_77648_3_, p_77648_3_.rand, p_77648_4_, p_77648_5_, p_77648_6_);
//        return false;
//    }

}
