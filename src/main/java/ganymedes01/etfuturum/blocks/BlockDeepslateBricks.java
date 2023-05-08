package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.blocks.itemblocks.ItemBlockDeepslate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockDeepslateBricks extends BlockGeneric implements IMultiStepSound {
	
	public BlockDeepslateBricks() {
		super(Material.rock, "", "cracked", "", "cracked", "chiseled");
		this.setHardness(1.5F);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate_bricks"));
		this.setBlockTextureName("deepslate_bricks");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
		this.setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundDeepslateBricks : soundTypeStone);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++) {
			String name = getTextureName();
			if(i > 1)
				name = name.replace("bricks", "tiles");
			if ("".equals(types[i]))
				icons[i] = reg.registerIcon(name);
			else
				icons[i] = reg.registerIcon(i == 4 ? "chiseled_deepslate" : types[i] + "_" + name);
			}
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockDeepslate.class;
	}

	@Override
	public SoundType getStepSound(IBlockAccess world, int x, int y, int z, int meta) {
		if(meta == 2 || meta == 3) {
			return ModSounds.soundDeepslateTiles;
		}
		return this.stepSound;
	}

	@Override
	public boolean requiresNewBlockSounds() {
		return true;
	}
}
