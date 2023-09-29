package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPackedMud extends BaseSubtypesBlock  {

	public BlockPackedMud() {
		super(Material.rock, "packed_mud", "mud_bricks");
		setHardness(1);
		setResistance(3);
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(EtFuturum.creativeTabBlocks);
		setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundPackedMud : Block.soundTypeMetal);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 1){
			return 1.5F;
		}

		return 1.0F;
	}
}
