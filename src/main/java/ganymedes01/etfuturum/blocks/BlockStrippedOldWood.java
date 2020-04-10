package ganymedes01.etfuturum.blocks;

import java.lang.reflect.Field;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemBlockGeneric;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.BlockOldLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class BlockStrippedOldWood extends BlockOldLog implements IConfigurable, ISubBlocksBlock {

    public BlockStrippedOldWood() {
        setBlockName(Utils.getUnlocalisedName("wood_stripped"));
        setCreativeTab(ConfigurationHandler.enableStrippedLogs && ConfigurationHandler.enableBarkLogs ? EtFuturum.creativeTab : null);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.field_150167_a = new IIcon[field_150168_M.length];
        this.field_150166_b = new IIcon[field_150168_M.length];

        for (int i = 0; i < this.field_150167_a.length; ++i) {
            this.field_150167_a[i] = iconRegister.registerIcon("stripped_" + field_150168_M[i] + "_log");
            this.field_150166_b[i] = iconRegister.registerIcon("stripped_" + field_150168_M[i] + "_log");
        }
    }

    @Override
    public boolean isEnabled() {
        return ConfigurationHandler.enableStrippedLogs && ConfigurationHandler.enableBarkLogs;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockGeneric.class;
    }

    public static void onPlayerInteract(PlayerInteractEvent event) {
        if (ConfigurationHandler.enableStrippedLogs)
            if (event.entityPlayer != null) {
                World world = event.entityPlayer.worldObj;
                if (event.action == Action.RIGHT_CLICK_BLOCK)
                    if (world.getBlock(event.x, event.y, event.z) == ModBlocks.log_bark) {
                        int logMeta = world.getBlockMetadata(event.x, event.y, event.z);
                        ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
                        if (stack != null && (stack.getItem() instanceof ItemAxe || isTinkersAxe(stack))) {
                            world.setBlock(event.x, event.y, event.z, ModBlocks.wood_stripped, logMeta, 2);
                            //world.setBlock(x, y, z, block, meta, notify)
                            event.entityPlayer.swingItem();
                            stack.damageItem(1, event.entityPlayer);
                            world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, Reference.MOD_ID + ":item.axe.strip", 1.0F, 0.8F);
                        }
                    }
            }
    }

    private static Item tinkersAxe;

    private static boolean isTinkersAxe(ItemStack stack) {
        if (EtFuturum.isTinkersConstructLoaded) {
            if (tinkersAxe == null)
                try {
                    Class<?> TinkerTools = Class.forName("tconstruct.tools.TinkerTools");
                    Field field = TinkerTools.getDeclaredField("axe");
                    field.setAccessible(true);
                    tinkersAxe = (Item) field.get(null);
                } catch (Exception e) {
                }
            return tinkersAxe == stack.getItem();
        }
        return false;
    }

}
