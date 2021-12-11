package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

public class ItemEtFuturumRecord extends ItemRecord implements IConfigurable, IRegistryName {

	public ItemEtFuturumRecord(String recordName) {
		super(recordName);
		setTextureName("music_disc_" + recordName);
		setUnlocalizedName("record");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return recordName.contains("pigstep") ? ConfigBlocksItems.enablePigstep : ConfigBlocksItems.enableOtherside;
	}

	@Override
	public String getRegistryName() {
		return recordName + "_record";
	}
	
	@Override
    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(Reference.MCv118 + ":music_disc." + recordName);
    }
	
}
