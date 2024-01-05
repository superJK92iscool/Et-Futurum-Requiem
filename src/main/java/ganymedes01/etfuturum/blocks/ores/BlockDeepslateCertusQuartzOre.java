package ganymedes01.etfuturum.blocks.ores;

import ganymedes01.etfuturum.blocks.IEmissiveLayerBlock;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.compat.ExternalContent;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockDeepslateCertusQuartzOre extends BaseSubtypesDeepslateOre implements IEmissiveLayerBlock {

	public BlockDeepslateCertusQuartzOre() {
		super(Material.rock, "deepslate_certus_quartz_ore", "deepslate_charged_certus_quartz_ore");
		setBlockSound(ModSounds.soundDeepslate);
		this.setResistance(5.0F);
		setHardness(4.5F);
	}

	@Override
	protected Block getBase(int meta) {
		if (meta == 1) {
			return ExternalContent.Blocks.AE2_CHARGED_CERTUS_QUARTZ_ORE.get();
		}
		System.out.println();
		return ExternalContent.Blocks.AE2_CERTUS_QUARTZ_ORE.get();
	}

	@Override
	public IIcon getEmissiveLayerIcon(int side, int meta) {
		return getBase(meta).getIcon(side, getBaseMeta(meta));
	}

	//Not sure how to make this work rn
	@Override
	public int getEmissiveMinBrightness(IBlockAccess world, int x, int y, int z) {
		return 1;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.EMISSIVE_DOUBLE_LAYER;
	}
}
