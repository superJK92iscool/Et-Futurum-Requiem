package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class BlockAmethystBlock extends Block implements IConfigurable {
	
	public BlockAmethystBlock() {
		this(Material.rock);
	}
	public BlockAmethystBlock(Material material) {
		super(material);
		setHardness(1.5F);
		setResistance(1.5F);
		setStepSound(ConfigWorld.enableNewBlocksSounds ? ModSounds.soundAmethystBlock : soundTypeGlass);
		setBlockTextureName("amethyst_block");
		setBlockName(Utils.getUnlocalisedName("amethyst_block"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent)
	{
		if (!world.isRemote && ent instanceof IProjectile) {
			 ent.playSound(Reference.MCv119 + ":block.amethyst_block.hit", 1.0F, 0.5F + world.rand.nextFloat() * 1.2F);
			 ent.playSound(Reference.MCv119 + ":block.amethyst_block.chime", 1.0F, 0.5F + world.rand.nextFloat() * 1.2F);
		}
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableAmethyst;
	}
}
