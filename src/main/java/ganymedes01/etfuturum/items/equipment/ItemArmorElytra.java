package ganymedes01.etfuturum.items.equipment;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.expanded.IBaubleExpanded;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.CompatBaublesExpanded;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.configuration.configs.ConfigModCompat;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemArmorElytra extends Item implements IConfigurable, IBaubleExpanded {

	@SideOnly(Side.CLIENT)
	private IIcon broken;

	public ItemArmorElytra() {
		setMaxStackSize(1);
		setMaxDamage(432);
		setTextureName("elytra");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
		setUnlocalizedName(Utils.getUnlocalisedName("elytra"));
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, BlockDispenser.dispenseBehaviorRegistry.getObject(Items.iron_chestplate));
	}

	public static boolean isBroken(ItemStack stack) {
		return stack.getItemDamage() >= stack.getMaxDamage();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == Items.leather;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		int entityequipmentslot = 3;
		ItemStack itemstack = playerIn.getEquipmentInSlot(entityequipmentslot);

		if (itemstack == null) {
			playerIn.setCurrentItemOrArmor(entityequipmentslot, itemStackIn.copy());
			itemStackIn.stackSize = 0;
		}
		return itemStackIn;
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return ConfigModCompat.elytraBaublesExpandedCompat != 2 && armorType == 1 && entity instanceof EntityLivingBase && getElytra((EntityLivingBase) entity) == null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta >= getMaxDamage() ? broken : super.getIconFromDamage(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		broken = reg.registerIcon("broken_elytra");
	}

	@Override
	public boolean isEnabled() {
		return ConfigMixins.enableElytra;
	}

	public static ItemStack getElytra(EntityLivingBase entity) {
		ItemStack armorSlot = entity.getEquipmentInSlot(3);
		if(armorSlot != null && armorSlot.getItem() instanceof ItemArmorElytra) {
			return armorSlot;
		}
		if(ConfigModCompat.elytraBaublesExpandedCompat > 0 && EtFuturum.hasBaublesExpanded && entity instanceof EntityPlayer) {
			for (int slotIndex : CompatBaublesExpanded.wingSlotIDs) {
				ItemStack wings = BaublesApi.getBaubles((EntityPlayer) entity).getStackInSlot(slotIndex);
				if (wings != null && wings.getItem() instanceof ItemArmorElytra) {
					return wings;
				}
			}
		}
		return null;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return null;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		if(ConfigSounds.armorEquip) {
			player.worldObj.playSoundAtEntity(player, Reference.MCAssetVer + ":item.armor.equip_elytra", 1, 1);
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return getElytra(player) == null;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public String[] getBaubleTypes(ItemStack itemstack) {
		if(ConfigModCompat.elytraBaublesExpandedCompat == 0) {
			return null;
		}
		return new String[] {"wings"};
	}
}