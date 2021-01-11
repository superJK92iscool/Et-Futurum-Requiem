package ganymedes01.etfuturum.items.equipment;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import ganymedes01.etfuturum.items.ItemUninflammable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEFRAxe extends ItemAxe implements IConfigurable {

    public ItemEFRAxe(ToolMaterial material, int durabilityOverride) {
        super(material);
        this.setMaxDamage(durabilityOverride > -1 ? durabilityOverride : material.getMaxUses());
        this.setUnlocalizedName(Utils.getUnlocalisedName("netherite_axe"));
        this.setTextureName("netherite_axe");
        this.setCreativeTab(ConfigurationHandler.enableNetherite ? EtFuturum.creativeTabItems : null);
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {        
        return ModItems.netherite_ingot == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
    @Override
    public boolean isEnabled() {
        return ConfigurationHandler.enableNetherite;
    }
    
    public boolean hasCustomEntity(ItemStack stack)
    {
        return getUnlocalizedName().contains("netherite") && !ConfigurationHandler.enableNetheriteFlammable;
    }
    
    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
        if(!getUnlocalizedName().contains("netherite"))
            return null;
        return ItemUninflammable.createUninflammableItem(world, location);
    }

}
