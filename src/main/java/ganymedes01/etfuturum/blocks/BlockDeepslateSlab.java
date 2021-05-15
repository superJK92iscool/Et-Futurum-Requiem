package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockDeepslateSlab extends BlockGenericSlab implements IConfigurable {
	
	private final boolean brick;

	public BlockDeepslateSlab(boolean isDouble, boolean isBrick) {
		super(isDouble, Material.rock, isBrick ? (new String[] {"brick", "tile"}) : (new String[] {"cobbled", "polished"}));
		brick = isBrick;
		this.setHardness(3);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate" + (isBrick ? "_brick" : "") + "_slab"));
		this.setBlockTextureName("deepslate" + (isBrick ? "_brick" : "") + "_slab");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		this.setStepSound(ConfigurationHandler.enableNewBlocksSounds ? isBrick ? ModSounds.soundDeepslateBricks : ModSounds.soundDeepslate : soundTypeStone);
	}

	public String func_150002_b(int meta)
	{
		meta %= 8;
				
		if(meta >= metaBlocks.length) {
			meta = 0;
		}
		String name = super.getUnlocalizedName();
		if(metaBlocks[meta].equals("tile"))
			name = name.replace("brick", "tile");

		return "tile.etfuturum." + (!brick ? (metaBlocks[meta]) + "_" : "") + name.split("\\.")[2];
	}
	
	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return brick ? new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.deepslate_brick_slab, (BlockGenericSlab) ModBlocks.double_deepslate_brick_slab}
		: new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.deepslate_slab, (BlockGenericSlab) ModBlocks.double_deepslate_slab};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return brick ? new IIcon[] {ModBlocks.deepslate_bricks.getIcon(side, 0), ModBlocks.deepslate_bricks.getIcon(side, 2)}
		: new IIcon[] {ModBlocks.cobbled_deepslate.getIcon(side, 0), ModBlocks.polished_deepslate.getIcon(side, 0)};
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableDeepslate;
	}

}
