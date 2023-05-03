package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.feature.WorldGenFossil;
import ganymedes01.etfuturum.world.generate.structure.WorldGenEndCityTest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNuggetIron extends Item implements IConfigurable {

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
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableIronNugget;
	}

}
