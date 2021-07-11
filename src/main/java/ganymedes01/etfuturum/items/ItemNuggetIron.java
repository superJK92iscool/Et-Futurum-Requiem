package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
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
//        EntityItemUninflammable entity = new EntityItemUninflammable(world);
//        entity.delayBeforeCanPickup = 40;
//        entity.setPosition(x, y, z);
//        world.spawnEntityInWorld(entity);
//      if(p_77648_2_.isSneaking()) {
//          if(world.isRemote) {
//              testvar++;
//              testvar %= 4;
//              ((EntityClientPlayerMP)p_77648_2_).sendChatMessage(Integer.toString(testvar));
//          }
//      } else {
//          new WorldGenFossil().generateSpecificFossil(world, world.rand, x, y, z, testvar, 0, world.rand.nextInt(2) == 1);
//      }
		return super.onItemUse(p_77648_1_, p_77648_2_, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableIronNugget;
	}

}
