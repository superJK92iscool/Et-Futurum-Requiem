package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MagmaBlock extends Block implements IConfigurable{
	
	public MagmaBlock()
	{
		super(Material.rock);
		setHardness(0.5F);
		setResistance(0.5F);
		setBlockTextureName("magma");
		this.setHarvestLevel("pickaxe", 0);
		setLightLevel(0.2F);
		setTickRandomly(true);
		setBlockName(Utils.getUnlocalisedName("magma"));
		setCreativeTab(ConfigurationHandler.enableNetherBlocks ? EtFuturum.creativeTab : null);
	}
	
	public static final DamageSource HOT_FLOOR = (new DamageSource("hotFloor")).setFireDamage();
	  
	  public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z)
	  {
	    float f = 0.125F;
	    return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - f, z + 1);
	  }
	  
	  public void onEntityCollidedWithBlock(World w, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity ent)
	  {
		  if (!ent.isImmuneToFire() && ent instanceof EntityLivingBase)
			  ent.attackEntityFrom(HOT_FLOOR, 1.0F);
	  }

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableNetherBlocks;
	}
	
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
		if (this == ModBlocks.magma && side == ForgeDirection.UP) {
			return true;
		}
		return false;
	}

}
