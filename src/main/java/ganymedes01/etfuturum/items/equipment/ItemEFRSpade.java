package ganymedes01.etfuturum.items.equipment;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.ItemUninflammable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEFRSpade extends ItemSpade implements IConfigurable {

	public ItemEFRSpade(ToolMaterial material, int durabilityOverride) {
		super(material);
		this.setMaxDamage(durabilityOverride > -1 ? durabilityOverride : material.getMaxUses());
		this.setUnlocalizedName(Utils.getUnlocalisedName("netherite_spade"));
		this.setTextureName("netherite_shovel");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{        
		return ModItems.netherite_ingot == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableNetherite;
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack)
	{
		return getUnlocalizedName().contains("netherite") && !ConfigFunctions.enableNetheriteFlammable;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		if(!getUnlocalizedName().contains("netherite"))
			return null;
		return ItemUninflammable.createUninflammableItem(world, location);
	}

}
