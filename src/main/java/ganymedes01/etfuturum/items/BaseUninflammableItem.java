package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BaseUninflammableItem extends BaseItem {

	public BaseUninflammableItem(String names) {
		super(names);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return !ConfigFunctions.enableNetheriteFlammable;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return createUninflammableItem(world, location);
	}
	
	public static Entity createUninflammableItem(World world, Entity location) {
		if(ConfigFunctions.enableNetheriteFlammable)
			return null;
		EntityItemUninflammable entity = new EntityItemUninflammable(world);
		entity.copyDataFrom(location, true);
		entity.copyLocationAndAnglesFrom(location);
		entity.delayBeforeCanPickup = ((EntityItem) location).delayBeforeCanPickup;
		return entity;
	}
}
