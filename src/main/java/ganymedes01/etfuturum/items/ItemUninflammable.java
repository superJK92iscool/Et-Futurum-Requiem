package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUninflammable extends Item {
	
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
		entity.delayBeforeCanPickup = 40;
		return entity;
	}
}
