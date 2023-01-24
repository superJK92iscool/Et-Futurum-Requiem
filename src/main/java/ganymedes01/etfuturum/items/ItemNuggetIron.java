package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemNuggetIron extends Item implements IConfigurable {

	public ItemNuggetIron() {
		super();
		setTextureName("iron_nugget");
		setUnlocalizedName(Utils.getUnlocalisedName("nugget_iron"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}
	
//    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
//    {
//    	WorldGenAmethystGeode geode = new WorldGenAmethystGeode();
//    	for(int x = 0; x <= 100; x += 25)
//        	for(int z = 0; z <= 100; z += 25)
//      geode.generate(p_77648_3_, p_77648_3_.rand, p_77648_4_ + x, p_77648_5_, p_77648_6_ + z);
//        return false;
//    }
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableIronNugget;
	}

}
