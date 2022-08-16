package ganymedes01.etfuturum.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
    private static final Map field_77835_b = new LinkedHashMap();

	@SideOnly(Side.CLIENT)
	private IIcon tipIcon;
	
    private HashMap effectCache = new HashMap();

	public ItemArrowTipped() {
		setHasSubtypes(true);
		setTextureName("tipped_arrow");
		setUnlocalizedName(Utils.getUnlocalisedName("tipped_arrow"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);

		if (ConfigBlocksItems.enableTippedArrows)
			BlockDispenser.dispenseBehaviorRegistry.putObject(this, new DispenserBehaviourTippedArrow());
	}
	
	private List<PotionEffect> getPotionEffects(int meta, boolean flag) {
		List<PotionEffect> list = new ArrayList();
	    list = PotionHelper.getPotionEffects(meta, flag);
	    for(int i = 0; i < list.size(); i++) {
	    	PotionEffect effect = (PotionEffect) list.get(i);
	    	list.set(i, new PotionEffect(effect.getPotionID(), effect.getDuration() / (Potion.potionTypes[effect.getPotionID()].isBadEffect() ? 6 : 5), effect.getAmplifier()));
	    }
	    return list;
	}
	
    public List getEffects(ItemStack stack)
    {
    	if(stack.hasTagCompound()) {
    		if (stack.getTagCompound().hasKey("Potion", Constants.NBT.TAG_COMPOUND)) {
    			NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("Potion");
    			List list = new ArrayList();
    			list.add(PotionEffect.readCustomPotionEffectFromNBT(nbt));
    			return list;
    		}
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CustomPotionEffects", 9))
            {
                ArrayList arraylist = new ArrayList();
                NBTTagList nbttaglist = stack.getTagCompound().getTagList("CustomPotionEffects", 10);

                for (int i = 0; i < nbttaglist.tagCount(); ++i)
                {
                    NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                    PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);

                    if (potioneffect != null)
                    {
                        arraylist.add(potioneffect);
                    }
                }

                return arraylist;
            }
    	}
		List list = (List)this.effectCache.get(Integer.valueOf(stack.getItemDamage()));

		if (list == null)
		{
			list = getPotionEffects(stack.getItemDamage(), false);
		    this.effectCache.put(Integer.valueOf(stack.getItemDamage()), list);
		}

		return list;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        super.getSubItems(item, tab, list);
        int j;

        if (field_77835_b.isEmpty())
        {
            for (int i = 1; i <= 15; ++i)
            {
                    int k;

                    k = i | 8192;

                    for (int l = 0; l <= 2; ++l)
                    {
                        int i1 = k;

                        if (l != 0)
                        {
                            if (l == 1)
                            {
                                i1 = k | 32;
                            }
                            else if (l == 2)
                            {
                                i1 = k | 64;
                            }
                        }

                        List list1 = getPotionEffects(i1, false);

                        if (list1 != null && !list1.isEmpty())
                        {
                            field_77835_b.put(list1, Integer.valueOf(i1));
                        }
                    }
            }
        }

        Iterator iterator = field_77835_b.values().iterator();

        while (iterator.hasNext())
        {
            j = ((Integer)iterator.next()).intValue();
            list.add(new ItemStack(item, 1, j));
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

		List list = getEffects(stack);
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