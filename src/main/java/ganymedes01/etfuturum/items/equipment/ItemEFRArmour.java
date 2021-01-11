package ganymedes01.etfuturum.items.equipment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import ganymedes01.etfuturum.items.ItemUninflammable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEFRArmour extends ItemArmor implements IConfigurable {

    public ItemEFRArmour(ArmorMaterial material, int type, int durabilityOverride) {
        super(material, 0, type);
        this.setMaxDamage(durabilityOverride > -1 ? durabilityOverride : material.getDurability(type));
        String s = "helmet";
        switch(type) {
        case 1: s = "chestplate"; break;
        case 2: s = "leggings"; break;
        case 3: s = "boots"; break;
        }
        this.setUnlocalizedName(Utils.getUnlocalisedName("netherite_" + s));
        this.setTextureName("netherite_" + s);
        this.setCreativeTab(ConfigurationHandler.enableNetherite ? EtFuturum.creativeTabItems : null);
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {        
        return ModItems.netherite_ingot == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        String wearingType = "netherite";
        return this.armorType == 2 ? "textures/models/armor/" + wearingType + "_layer_2.png" : "textures/models/armor/" + wearingType + "_layer_1.png";
    }
    
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
