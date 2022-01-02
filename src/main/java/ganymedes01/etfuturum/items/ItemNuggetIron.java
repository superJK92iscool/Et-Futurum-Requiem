package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.feature.WorldGenAmethystGeode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNuggetIron extends Item implements IConfigurable {
	
//  public int testvar = 0;

	public ItemNuggetIron() {
		super();
		setTextureName("iron_nugget");
		setUnlocalizedName(Utils.getUnlocalisedName("nugget_iron"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(!world.isRemote) {
			new WorldGenAmethystGeode().generate(world, world.rand, x, y, z);
		}
		return super.onItemUse(p_77648_1_, p_77648_2_, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableIronNugget;
	}

}
