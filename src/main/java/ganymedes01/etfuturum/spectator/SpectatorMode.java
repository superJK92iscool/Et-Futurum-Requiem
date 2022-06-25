package ganymedes01.etfuturum.spectator;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.WeakHashMap;

public class SpectatorMode {
    public static final SpectatorMode INSTANCE = new SpectatorMode();
    SpectatorMode() {

    }
    public static WorldSettings.GameType SPECTATOR_GAMETYPE = null;
    public static void init() {
        if(ConfigFunctions.enableSpectatorMode)
            SPECTATOR_GAMETYPE = EnumHelperClient.addGameType("spectator", 3, "Spectator");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        World world = FMLClientHandler.instance().getWorldClient();
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

    public static boolean isSpectator(EntityPlayer player) {
        if(player.worldObj.isRemote) {
            if(player == Minecraft.getMinecraft().thePlayer)
                return Minecraft.getMinecraft().playerController.currentGameType == SPECTATOR_GAMETYPE;
            else
                return false;
        } else {
            return ((EntityPlayerMP)player).theItemInWorldManager.getGameType() == SPECTATOR_GAMETYPE;
        }
    }

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
    public void onInteract(PlayerInteractEvent event) {
        if(isSpectator(event.entityPlayer)) {
            if(event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
                event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onInteract(AttackEntityEvent event) {
        if(isSpectator(event.entityPlayer)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            boolean isSpec = isSpectator(event.player);
            if(!isSpec && event.player.noClip) {
                event.player.setInvisible(false);
            }
            event.player.noClip = isSpec;
            /* reuse value for spectator check */
            if(isSpec) {
                event.player.onGround = false;
                event.player.setInvisible(true);
            }

        }
    }

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event) {
        if(isSpectator(event.entityPlayer))
            event.newSpeed = 0;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onHotbarRender(RenderGameOverlayEvent.Pre event) {
        if(isSpectator(Minecraft.getMinecraft().thePlayer) && event.type == RenderGameOverlayEvent.ElementType.HOTBAR)
            event.setCanceled(true);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onFireRender(RenderBlockOverlayEvent event) {
        if(isSpectator(Minecraft.getMinecraft().thePlayer) && event.overlayType == RenderBlockOverlayEvent.OverlayType.FIRE)
            event.setCanceled(true);
    }
}
