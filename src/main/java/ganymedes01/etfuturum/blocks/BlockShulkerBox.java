package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.GUIsID;
import ganymedes01.etfuturum.lib.RenderIDs;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockShulkerBox extends BlockContainer implements IConfigurable {
	
	@SideOnly(Side.CLIENT)
	private IIcon[] colorIcons = new IIcon[17];

	public BlockShulkerBox() {
		super(Material.rock);
		this.setStepSound(soundTypeStone);
		this.setHardness(2.5F);
		this.setHarvestLevel("pickaxe", 0);
		this.setResistance(2.5F);
		this.setBlockName(Utils.getUnlocalisedName("shulker_box"));
		this.setBlockTextureName("shulker_box");
		this.isBlockContainer = true;
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
		if (world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) != this)
			dir = dir.getOpposite();
		return dir.ordinal();
	}

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
		TileEntityShulkerBox box = (TileEntityShulkerBox)world.getTileEntity(x, y, z);
		if(stack.hasTagCompound()) {
			NBTTagList nbttaglist = stack.getTagCompound().getTagList("Items", 10);
			box.chestContents = new ItemStack[box.getSizeInventory()];

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound1.getByte("Slot") & 255;

				if (j >= 0 && j < box.chestContents.length)
				{
					box.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
			
			if(stack.getTagCompound().hasKey("Color")) {
				box.color = stack.getTagCompound().getInteger("Color");
			}

			if (stack.hasDisplayName())
			{
				box.func_145976_a(stack.getDisplayName());
			}
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister i)
	{
		blockIcon = colorIcons[0] = i.registerIcon("shulker_top");
		colorIcons[1] = i.registerIcon("shulker_top_white");
		colorIcons[2] = i.registerIcon("shulker_top_orange");
		colorIcons[3] = i.registerIcon("shulker_top_magenta");
		colorIcons[4] = i.registerIcon("shulker_top_light_blue");
		colorIcons[5] = i.registerIcon("shulker_top_yellow");
		colorIcons[6] = i.registerIcon("shulker_top_lime");
		colorIcons[7] = i.registerIcon("shulker_top_pink");
		colorIcons[8] = i.registerIcon("shulker_top_grey");
		colorIcons[9] = i.registerIcon("shulker_top_silver");
		colorIcons[10] = i.registerIcon("shulker_top_cyan");
		colorIcons[11] = i.registerIcon("shulker_top_purple");
		colorIcons[12] = i.registerIcon("shulker_top_blue");
		colorIcons[13] = i.registerIcon("shulker_top_brown");
		colorIcons[14] = i.registerIcon("shulker_top_green");
		colorIcons[15] = i.registerIcon("shulker_top_red");
		colorIcons[16] = i.registerIcon("shulker_top_black");
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        return world.getTileEntity(x, y, z) instanceof TileEntityShulkerBox ? Blocks.glowstone.getIcon(0, 0) : Blocks.redstone_block.getIcon(0, 0);
    }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (world.isRemote)
		{
			return true;
		}
		player.openGui(EtFuturum.instance, GUIsID.SHULKER_BOX, world, x, y, z);
		return true;
	}

	public IInventory func_149951_m(World p_149951_1_, int p_149951_2_, int p_149951_3_, int p_149951_4_)
	{
		Object object = p_149951_1_.getTileEntity(p_149951_2_, p_149951_3_, p_149951_4_);

		if(object == null)
			return null;
		
		return (IInventory)object;
	}
	
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
    	System.out.println(world.getTileEntity(x, y, z) instanceof TileEntityShulkerBox);
    	super.dropBlockAsItemWithChance(world, x, y, z, p_149690_5_, p_149690_6_, p_149690_7_);
    }
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		if (!((TileEntityShulkerBox)world.getTileEntity(x, y, z)).onBlockBreak(player)) return false;
		return super.removedByPlayer(world, player, x, y, z);
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntityShulkerBox container = (TileEntityShulkerBox) world.getTileEntity(x, y, z);
		if (container != null) container.onBlockDestroyed();
	}
    protected void dropBlockAsItem(World p_149642_1_, int p_149642_2_, int p_149642_3_, int p_149642_4_, ItemStack p_149642_5_)
    {
    }

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}
	
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {
        return true;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityShulkerBox();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
