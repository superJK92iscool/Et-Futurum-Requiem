package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;

public class ItemRabbitFoot extends Item implements IConfigurable {

	@SuppressWarnings("unchecked")
	public ItemRabbitFoot() {
		setTextureName("rabbit_foot");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_foot"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);

		if (ConfigEntities.enableRabbit)
			PotionHelper.potionRequirements.put(Potion.jump.getId(), "0 & 1 & !2 & 3");
			PotionHelper.potionAmplifiers.put(Potion.jump.getId(), "5");
			Potion.jump.liquidColor = 0x22FF4C;
	}

	@Override
	public String getPotionEffect(ItemStack stack) {
		return "+0+1-2+3&4-4+13";
	}

	@Override
	public boolean isPotionIngredient(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return ConfigEntities.enableRabbit;
	}
}