package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.Random;

import static net.minecraftforge.common.EnumPlantType.Nether;

public class BlockWitherRose extends BasicFlower {

	public BlockWitherRose() {
		setNames("wither_rose");
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity ent) {
		if (ent instanceof EntityLivingBase && w.difficultySetting != EnumDifficulty.PEACEFUL) {
			((EntityLivingBase)ent).addPotionEffect(new PotionEffect(Potion.wither.id, 120));
		}
	}
	
	@Override
	protected boolean canPlaceBlockOn(Block p_149854_1_)
	{
		return p_149854_1_ == Blocks.grass || p_149854_1_ == Blocks.dirt || p_149854_1_ == Blocks.farmland || p_149854_1_ == Blocks.soul_sand || p_149854_1_ == Blocks.netherrack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
	{
		p_149734_1_.spawnParticle("smoke", p_149734_2_ + 0.5D, p_149734_3_ + 0.5D, p_149734_4_ + 0.5D, 0, 0, 0);
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
	{    
		return Nether;
	}
}
