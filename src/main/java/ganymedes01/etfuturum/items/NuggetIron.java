package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class NuggetIron extends Item implements IConfigurable {

    public NuggetIron() {
        super();
        setTextureName("iron_nugget");
        setUnlocalizedName(Utils.getUnlocalisedName("nugget_iron"));
        setCreativeTab(ConfigurationHandler.enableIronNugget ? EtFuturum.creativeTabItems : null);
    }
    
    @Override
    public boolean isEnabled() {
        return ConfigurationHandler.enableIronNugget;
    }

}
