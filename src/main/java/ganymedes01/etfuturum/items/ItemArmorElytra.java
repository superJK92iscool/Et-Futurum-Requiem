package ganymedes01.etfuturum.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.client.model.ModelElytra;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.feature.WorldGenFossil;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemArmorElytra extends Item implements IConfigurable {

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
		return armorType == 1;
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
}