package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.EtFuturumLootTables;
import ganymedes01.etfuturum.api.CompostingRegistry;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockComposter extends Block {

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
        this.setCreativeTab(EtFuturum.creativeTabBlocks);
        this.setLightOpacity(0);
        this.useNeighborBrightness = true;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.topIcon : p_149691_1_ == 0 ? bottomIcon : this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
        this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");

        this.compostIcon = p_149651_1_.registerIcon(this.getTextureName() + "_compost");
        this.fullCompostIcon = p_149651_1_.registerIcon(this.getTextureName() + "_ready");
    }

    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
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

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 6) {
            ItemStack stack = player.getCurrentEquippedItem();
            int chance = CompostingRegistry.getCompostChance(stack);
            if (chance > 0) {
                if (!world.isRemote) {
                    /*
                     * Takes the chance (which can be up to 600) and multiplies it by 0.01F (same as dividing by 100) to see how many times it passes 100
                     * Then I wrap it by 100 and check that against the chance to see if we should add one more to the fill level.
                     */
                    int fillAmount = (int) (chance * .01F) + (world.rand.nextInt(100) < chance % 100 ? 1 : 0);
                    if (fillAmount > 0) {
                        world.setBlockMetadataWithNotify(x, y, z, Math.min(meta + fillAmount, 6), 3);
                        world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, Reference.MCAssetVer + ":block.composter.fill_success", 1, 1);
                        if (fillAmount + meta > 5) {
                            world.scheduleBlockUpdate(x, y, z, this, world.rand.nextInt(10) + 10);
                        }
                    } else {
                        world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, Reference.MCAssetVer + ":block.composter.fill", 1, 1);
                    }
                    if (!player.capabilities.isCreativeMode) {
                        stack.stackSize--;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    world.spawnParticle("happyVillager", ((world.rand.nextDouble() * 0.875D) + 0.125D) + x, 0.25D + (0.125D * meta) + y, ((world.rand.nextDouble() * 0.875D) + 0.125D) + z, 0, 0, 0);
                }
                return true;
            }
        } else if (meta == 7) {
            if (!world.isRemote) {
                EntityItem item = new EntityItem(world, x + 0.5D, y + 1, z + 0.5D, EtFuturumLootTables.COMPOSTER_LOOT.getOneItem(world.rand));
                item.motionX = world.rand.nextDouble() * 0.5D;
                item.motionY = 0;
                item.motionZ = world.rand.nextDouble() * 0.5D;
                world.spawnEntityInWorld(item);
            }
            world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, Reference.MCAssetVer + ":block.composter.empty", 1, 1, true);
            world.setBlockMetadataWithNotify(x, y, z, 0, 3);
            return true;
        }
        return false;
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.getBlockMetadata(x, y, z) == 6) {
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, Reference.MCAssetVer + ":block.composter.ready", 1, 1);
            world.setBlockMetadataWithNotify(x, y, z, 7, 3);
        }
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
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return side != ForgeDirection.UP;
    }
}
