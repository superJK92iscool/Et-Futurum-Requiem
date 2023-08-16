package ganymedes01.etfuturum.blocks;

import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import com.google.common.collect.Maps;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.particle.ParticleHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBarrier extends BaseBlock {

	/**
	 * Caches barrier particles spawned, so we don't just spam barrier particles
	 */
	private static final Map<BlockPos, EntityFX> BARRIER_FX_MAP = new WeakHashMap<>();

	public BlockBarrier() {
		super(Material.rock);
		setBlockUnbreakable();
		setResistance(2000000F);
		setLightOpacity(0);
		setNames("barrier");
		useNeighborBrightness = true;
		canBlockGrass = false;
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ItemStack heldItem = FMLClientHandler.instance().getClient().thePlayer.getHeldItem();

		if (player.capabilities.isCreativeMode && heldItem != null && heldItem.getItem() == Item.getItemFromBlock(this)) {
			BlockPos pos = new BlockPos(x, y, z);
			if (!BARRIER_FX_MAP.containsKey(pos) || BARRIER_FX_MAP.get(pos).isDead) {
				EntityFX particle = ParticleHandler.BARRIER.spawn(world, x + .5F, y + .5F, z + .5F);
				BARRIER_FX_MAP.put(pos, particle);
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
