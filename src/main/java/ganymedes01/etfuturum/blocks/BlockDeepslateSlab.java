package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockDeepslateSlab extends BlockGenericSlab implements IConfigurable, IMultiStepSound {
	
	private final boolean brick;

	public BlockDeepslateSlab(boolean isDouble, boolean isBrick) {
		super(isDouble, Material.rock, isBrick ? (new String[] {"brick", "tile"}) : (new String[] {"cobbled", "polished"}));
		brick = isBrick;
		this.setHardness(3);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate" + (isBrick ? "_brick" : "") + "_slab"));
		this.setBlockTextureName("deepslate" + (isBrick ? "_brick" : "") + "_slab");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
		this.setStepSound(ConfigSounds.newBlockSounds ? isBrick ? ModSounds.soundDeepslateBricks : ModSounds.soundDeepslate : soundTypeStone);
	}

	@Override
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
		return brick ? new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.DEEPSLATE_BRICK_SLAB.get(), (BlockGenericSlab) ModBlocks.DOUBLE_DEEPSLATE_BRICK_SLAB.get()}
		: new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.DEEPSLATE_SLAB.get(), (BlockGenericSlab) ModBlocks.DOUBLE_DEEPSLATE_SLAB.get()};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return brick ? new IIcon[] {ModBlocks.DEEPSLATE_BRICKS.get().getIcon(side, 0), ModBlocks.DEEPSLATE_BRICKS.get().getIcon(side, 2)}
		: new IIcon[] {ModBlocks.COBBLED_DEEPSLATE.get().getIcon(side, 0), ModBlocks.POLISHED_DEEPSLATE.get().getIcon(side, 0)};
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableDeepslate;
	}

	@Override
	public SoundType getStepSound(IBlockAccess world, int x, int y, int z, int meta) {
		if(brick && (meta % 8) == 1)
			return ModSounds.soundDeepslateTiles;
		return this.stepSound;
	}

	@Override
	public boolean requiresNewBlockSounds() {
		return true;
	}

}
