package ganymedes01.etfuturum.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class ItemHoneyBottle extends BaseItem {
	public ItemHoneyBottle() {
		setNames("honey_bottle");
		setContainerItem(Items.glass_bottle);
	}

	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
		if (!p_77654_3_.capabilities.isCreativeMode) {
			--p_77654_1_.stackSize;
		}

		if (!p_77654_2_.isRemote) {
			p_77654_3_.removePotionEffect(Potion.poison.id);
		}

		if (!p_77654_3_.capabilities.isCreativeMode) {
			if (p_77654_1_.stackSize <= 0) {
				return new ItemStack(Items.glass_bottle);
			}

			p_77654_3_.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
		}

		return p_77654_1_;
	}

	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 32;
	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.drink;
	}

	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		return p_77659_1_;
	}
}
