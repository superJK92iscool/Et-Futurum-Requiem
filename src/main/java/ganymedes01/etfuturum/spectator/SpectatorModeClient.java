package ganymedes01.etfuturum.spectator;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;

import static ganymedes01.etfuturum.spectator.SpectatorMode.isSpectator;

public class SpectatorModeClient {
    public static final SpectatorModeClient INSTANCE = new SpectatorModeClient();
    private static void setBipedVisible(ModelBiped biped, boolean visible)
    {
        biped.bipedHead.showModel = visible;
        biped.bipedHeadwear.showModel = visible;
        biped.bipedBody.showModel = visible;
        biped.bipedRightArm.showModel = visible;
        biped.bipedLeftArm.showModel = visible;
        biped.bipedRightLeg.showModel = visible;
        biped.bipedLeftLeg.showModel = visible;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        if(isSpectator(event.entityPlayer)) {
            setBipedVisible(event.renderer.modelBipedMain, false);
            event.renderer.modelBipedMain.bipedHead.showModel = true;
            event.renderer.modelBipedMain.bipedHeadwear.showModel = true;
        } else {
            setBipedVisible(event.renderer.modelBipedMain, true);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderPlayerArmor(RenderPlayerEvent.Specials.Pre event) {
        if(isSpectator(event.entityPlayer)) {
            event.setCanceled(true);
        }
    }

    private static boolean hadHeldItemTooltips;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @SideOnly(Side.CLIENT)
    public void onOverlayRenderPre(RenderGameOverlayEvent.Pre event) {
        if(isSpectator(Minecraft.getMinecraft().thePlayer)) {
            if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR ||
                    event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
                event.setCanceled(true);
            }
            if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
                hadHeldItemTooltips = Minecraft.getMinecraft().gameSettings.heldItemTooltips;
                Minecraft.getMinecraft().gameSettings.heldItemTooltips = false;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public void onOverlayRenderPost(RenderGameOverlayEvent.Post event) {
        if(isSpectator(Minecraft.getMinecraft().thePlayer)) {
            if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
                Minecraft.getMinecraft().gameSettings.heldItemTooltips = hadHeldItemTooltips;
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onHandRender(RenderHandEvent event) {
        if(isSpectator(Minecraft.getMinecraft().thePlayer))
            event.setCanceled(true);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onFireRender(RenderBlockOverlayEvent event) {
        if(isSpectator(Minecraft.getMinecraft().thePlayer))
            event.setCanceled(true);
    }

    /* TODO look into increasing the distance instead of outright disabling it */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderFogDensity(EntityViewRenderEvent.FogDensity event) {
        if(event.entity instanceof EntityPlayer) {
            if(isSpectator((EntityPlayer)event.entity)) {
                if(event.block.getMaterial() == Material.water || event.block.getMaterial() == Material.lava) {
                    event.setCanceled(true);
                    event.density = 0;
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onBlockHighlight(DrawBlockHighlightEvent event) {
        if(isSpectator(event.player)) {
            Block block = Minecraft.getMinecraft().theWorld.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
            int meta = Minecraft.getMinecraft().theWorld.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ);
            if(!block.hasTileEntity(meta) || !(Minecraft.getMinecraft().theWorld.getTileEntity(event.target.blockX, event.target.blockY, event.target.blockZ) instanceof IInventory)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
        if(player != null && event.phase == TickEvent.Phase.START) {
            if(Minecraft.getMinecraft().playerController.currentGameType == SpectatorMode.SPECTATOR_GAMETYPE) {
                if(!player.capabilities.isFlying) {
                    player.capabilities.isFlying = true;
                    player.sendPlayerAbilities();
                }
            }
        }
    }
}
