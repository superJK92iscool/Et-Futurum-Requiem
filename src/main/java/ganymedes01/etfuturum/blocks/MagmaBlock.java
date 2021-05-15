package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import static net.minecraftforge.common.util.ForgeDirection.UP;

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
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	public static final DamageSource HOT_FLOOR = (new DamageSource("hotFloor")).setFireDamage();

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableMagmaBlock;
	}
	
	public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
		return side == UP;
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		Block block1 = world.getBlock(x, y+1, z);
		Block block2 = world.getBlock(x, y+2, z);
		
		if ( (block1 == Blocks.water || block1 == Blocks.flowing_water) && block2.isAir(world, x, y+2, z)) {
			world.setBlockToAir(x, y+1, z);
			//world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "random.fizz", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.fizz", 0.3F, 0.6F);
			
			/*
			if (world instanceof WorldServer) { //MC 1.12 code
				((WorldServer)world).spawnParticle("largesmoke", (double)x + 0.5D, (double)y + 0.25D, (double)z + 0.5D, 0.0D, 0.5D, 0.0D);
			}*/
		}
		
		if ( block1 == Blocks.ice) {
			 world.setBlock(x, y+1, z, world.provider.isHellWorld ? Blocks.air : Blocks.water, 0, 2);
			 if(!world.provider.isHellWorld)
				 world.markBlockForUpdate(x, y+1, z);
			//world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "random.fizz", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.splash", 0.3F, 0.6F);
			
		}
		
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		Block block1 = world.getBlock(x, y+1, z);
		Block block2 = world.getBlock(x, y+2, z);
		
		if ( (block1 == Blocks.water || block1 == Blocks.flowing_water) && block2.isAir(world, x, y+2, z)) {
			world.spawnParticle("explode", (double)x + 0.5D, (double)y + 1.0D, (double)z + 0.5D, 0.0D, 0.0D, 0.0D);
		}
		
		else if ( (block1 == Blocks.water || block1 == Blocks.flowing_water) && 
				(block2 == Blocks.water || block2 == Blocks.flowing_water) ) {
			world.spawnParticle("bubble", (double)x + 0.5D, (double)y + 1.1D, (double)z + 0.5D, 0.0D, 1.0D, 0.0D);
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		blockIcon = new InterpolatedIcon(textureName);
		if(p_149651_1_ instanceof TextureMap) {
			((TextureMap)p_149651_1_).setTextureEntry(textureName, (InterpolatedIcon)blockIcon);
		}
	}

}
