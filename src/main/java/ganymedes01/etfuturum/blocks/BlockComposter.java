package ganymedes01.etfuturum.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockComposter extends Block implements IConfigurable {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;
	
	@SideOnly(Side.CLIENT)
	public IIcon compostIcon;
	@SideOnly(Side.CLIENT)
	public IIcon fullCompostIcon;
	
	public BlockComposter() {
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setHardness(0.6F);
		this.setResistance(0.6F);
		this.setHarvestLevel("axe", 0);
		this.setBlockName(Utils.getUnlocalisedName("composter"));
		this.setBlockTextureName("composter");
		this.setLightOpacity(500);
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		this.setLightOpacity(0);
		this.useNeighborBrightness = true;
		OreDictionary.registerOre("compost75", Items.wheat_seeds);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return p_149691_1_ == 1 ? this.topIcon : p_149691_1_ == 0 ? bottomIcon : this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		
		this.compostIcon = p_149651_1_.registerIcon(this.getTextureName() + "_compost");
		this.fullCompostIcon = p_149651_1_.registerIcon(this.getTextureName() + "_ready");
	}
	
    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBoundsForItemRender();
    }
    
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
    	
    	int meta = world.getBlockMetadata(x, y, z);
    	if(meta < 8) {
    		meta = Math.min(meta, 7);
        	ItemStack stack = player.getCurrentEquippedItem();
        	for(String oreName : EtFuturum.getOreStrings(stack)) {
        		String chanceKey = "compostChance";
        		if(oreName.startsWith(chanceKey)) {
        			try {
        				if(!world.isRemote) {
            				int chance = Math.min(100, Math.max(1, Integer.valueOf(oreName.substring(chanceKey.length()))));
                			if(world.rand.nextInt(100) < chance) {
                				world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
                			}
                			if(!player.capabilities.isCreativeMode) {
                    			stack.stackSize--;
                			}
            			}
        				for(int i = 0; i < 5; i++) {
            				world.spawnParticle("happyVillager", ((world.rand.nextDouble() * 0.875D) + 0.125D) + x, 0.25D + (0.125D * Math.min(meta, 6)) + y, ((world.rand.nextDouble() * 0.875D) + 0.125D) + z, 0, 0, 0);
        				}
            			return true;
        			} catch(NumberFormatException e) {
        				String itemName = Item.itemRegistry.getNameForObject(stack.getItem());
        				System.out.println("Item " + itemName + " had an incorrectly formatted composter tag! Got " + oreName + " instead.");
        				System.out.println("It should be formatted starting with \"compost\" and then a number from 1 to 100. Example: compost75 for a 75% composting chance.");
        				e.printStackTrace();
        			}
        		}
        	}
    	}
        return false;
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
	public int getRenderType() {
		return RenderIDs.COMPOSTER;
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.isTesting;
	}

}
