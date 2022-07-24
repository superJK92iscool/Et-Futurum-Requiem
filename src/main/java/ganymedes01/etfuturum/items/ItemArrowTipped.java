package ganymedes01.etfuturum.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.dispenser.DispenserBehaviourTippedArrow;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.Constants;

public class ItemArrowTipped extends Item implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon tipIcon;
	
    private HashMap effectCache = new HashMap();

	public ItemArrowTipped() {
		setTextureName("tipped_arrow");
		setUnlocalizedName(Utils.getUnlocalisedName("tipped_arrow"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);

		if (ConfigBlocksItems.enableTippedArrows)
			BlockDispenser.dispenseBehaviorRegistry.putObject(this, new DispenserBehaviourTippedArrow());
	}
	
    public List getEffects(ItemStack stack)
    {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Potion", Constants.NBT.TAG_COMPOUND)) {
			NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("Potion");
			List list = new ArrayList();
			list.add(PotionEffect.readCustomPotionEffectFromNBT(nbt));
			stack.getTagCompound().removeTag("Potion");
			if(stack.getTagCompound().hasNoTags()) {
				stack.setTagCompound(null);
			}
			return list;
		}
		return Items.potionitem.getEffects(stack);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		List<ItemStack> potions = new ArrayList<ItemStack>();
		Items.potionitem.getSubItems(item, tab, potions);

		for (ItemStack potion : potions) {
			if (potion.getItemDamage() > 0 && !ItemPotion.isSplash(potion.getItemDamage())) {
				list.add(potion);
			}
		}
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getIconString() + "_base");
		tipIcon = reg.registerIcon(getIconString() + "_head");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return Items.potionitem.getColorFromItemStack(stack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass) {
		return pass == 0 ? tipIcon : super.getIcon(stack, pass);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() == 0)
        {
            return StatCollector.translateToLocal("tipped_arrow.effect.empty").trim();
        }
		String s = "tipped_arrow.";

		List list = Items.potionitem.getEffects(stack);
		String s1;

		if (list != null && !list.isEmpty())
		{
		    s1 = ((PotionEffect)list.get(0)).getEffectName();
		    return s + StatCollector.translateToLocal(s1).trim();
		}
		
		s1 = PotionHelper.func_77905_c(stack.getItemDamage());
		return StatCollector.translateToLocal(s1).trim() + " " + super.getItemStackDisplayName(stack);
		

//		PotionEffect effect = getEffect(stack);
//		if (effect == null || effect.getPotionID() < 0 || effect.getPotionID() >= Potion.potionTypes.length)
//			//return super.getUnlocalizedName(stack); 
//			return "tipped_arrow.effect.empty";
//
//		Potion potion = Potion.potionTypes[effect.getPotionID()];
//		return "tipped_arrow." + potion.getName();
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableTippedArrows;
	}
	
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_)
	{
		Items.potionitem.addInformation(stack, p_77624_2_, list, p_77624_4_);
	}
	
}