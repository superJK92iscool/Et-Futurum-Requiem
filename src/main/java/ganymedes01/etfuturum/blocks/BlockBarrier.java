package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.particle.ParticleHandler;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBarrier extends Block implements IConfigurable {
	
	public BlockBarrier() {
		super(Material.rock);
		this.setBlockUnbreakable();
		this.setResistance(2000000F);
		this.setLightOpacity(0);
		this.setBlockTextureName("barrier");
		this.setBlockName(Utils.getUnlocalisedName("barrier"));
		this.useNeighborBrightness = true;
		this.canBlockGrass = false;
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{ 
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		Item heldItem;
		
		if (player.inventory.getCurrentItem() != null) {
			heldItem = player.inventory.getCurrentItem().getItem();
			Item barrier = Item.getItemFromBlock(this);
			
//            EntityFX particle = new BarrierParticleFX(world, x + .5F, y + .5F, z + .5F);
			
			if(player.capabilities.isCreativeMode && heldItem == barrier) {
//              Minecraft.getMinecraft().effectRenderer.addEffect(particle);
				ParticleHandler.BARRIER.spawn(world, x + .5F, y + .5F, z + .5F);
			}
		}
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return ConfigBlocksItems.enableBarrier;
	}

	@Override
	public boolean isNormalCube()
	{
		return true;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
	{
		return true;
	}
	
	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}

}
