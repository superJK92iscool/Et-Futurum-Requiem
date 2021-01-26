package ganymedes01.etfuturum.core.handlers;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockBlastFurnace;
import ganymedes01.etfuturum.blocks.MagmaBlock;
import ganymedes01.etfuturum.blocks.PrismarineBlocks;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ClientEventHandler {

    public static final ClientEventHandler INSTANCE = new ClientEventHandler();
    
    private ClientEventHandler() {
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void loadTextures(TextureStitchEvent.Pre event) {
        if (ConfigurationHandler.enablePrismarine)
            if (event.map.getTextureType() == 0) {
                TextureAtlasSprite icon = new InterpolatedIcon("prismarine_rough");
                if (event.map.setTextureEntry("prismarine_rough", icon))
                    ((PrismarineBlocks) ModBlocks.prismarine).setIcon(0, icon);
                else
                    ((PrismarineBlocks) ModBlocks.prismarine).setIcon(0, event.map.registerIcon("prismarine_rough"));
            }
		
		if (ConfigurationHandler.enableBlastFurnace) {
			if (event.map.getTextureType() == 0) {
				String s = "blast_furnace_front_on";
				TextureAtlasSprite icon = new InterpolatedIcon(s);
				if (event.map.setTextureEntry(s, icon))
					((BlockBlastFurnace) ModBlocks.lit_blast_furnace).setFurnaceFrontIcon(icon);
				else
					((BlockBlastFurnace) ModBlocks.lit_blast_furnace).setFurnaceFrontIcon(event.map.registerIcon(s));
			}
		}
		
		if (ConfigurationHandler.enableMagmaBlock) {
			if (event.map.getTextureType() == 0) {
				String s = "magma";
				TextureAtlasSprite icon = new InterpolatedIcon(s);
				if (event.map.setTextureEntry(s, icon))
					((MagmaBlock) ModBlocks.magma_block).setMagmaIcon(icon);
				else
					((MagmaBlock) ModBlocks.magma_block).setMagmaIcon(event.map.registerIcon(s));
			}
		}
    }

    @SubscribeEvent
    public void toolTipEvent(ItemTooltipEvent event) {
    	if(event.showAdvancedItemTooltips) {
        	event.toolTip.add("");
        	event.toolTip.add("\u00a78" + Item.itemRegistry.getNameForObject(event.itemStack.getItem()));
        	if(event.itemStack.stackTagCompound != null && !event.itemStack.stackTagCompound.hasNoTags())
            	event.toolTip.add("\u00a78NBT: " + event.itemStack.stackTagCompound.func_150296_c().size() + " Tag(s)");
    	}
    }
    
    @SubscribeEvent
    public void renderPlayerEventPre(RenderPlayerEvent.Pre event) {
        if (ConfigurationHandler.enableTransparentAmour) {
            OpenGLHelper.enableBlend();
            OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    @SubscribeEvent
    public void renderPlayerSetArmour(SetArmorModel event) {
        if (ConfigurationHandler.enableTransparentAmour) {
            OpenGLHelper.enableBlend();
            OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    @SubscribeEvent
    public void renderPlayerEventPost(RenderPlayerEvent.Post event) {
        if (ConfigurationHandler.enableTransparentAmour)
            OpenGLHelper.disableBlend();
    }
}