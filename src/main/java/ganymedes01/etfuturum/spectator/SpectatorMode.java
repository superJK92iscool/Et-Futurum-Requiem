package ganymedes01.etfuturum.spectator;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.WeakHashMap;

public class SpectatorMode {
    public static final SpectatorMode INSTANCE = new SpectatorMode();
    public static final IEntitySelector EXCEPT_SPECTATING = new IEntitySelector() {
        @Override
        public boolean isEntityApplicable(Entity p_82704_1_) {
            return !(p_82704_1_ instanceof EntityPlayer) || !isSpectator((EntityPlayer) p_82704_1_);
        }
    };
    SpectatorMode() {

    }
    public static WorldSettings.GameType SPECTATOR_GAMETYPE = null;
    public static void init() {
        if(ConfigMixins.enableSpectatorMode)
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
        if(player == null)
            return false;
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
            else {
                if(!Minecraft.getMinecraft().theWorld.blockExists(event.x, event.y, event.z)) {
                    return;
                }
                Block block = Minecraft.getMinecraft().theWorld.getBlock(event.x, event.y, event.z);
                int meta = Minecraft.getMinecraft().theWorld.getBlockMetadata(event.x, event.y, event.z);
                if(!block.hasTileEntity(meta) || !(Minecraft.getMinecraft().theWorld.getTileEntity(event.x, event.y, event.z) instanceof IInventory)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onInteract(BlockEvent.PlaceEvent event) {
        if(isSpectator(event.player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        if(isSpectator(event.entityPlayer)) {
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

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void itemToss(ItemTossEvent event) {
        if(isSpectator(event.player)) {
            event.setCanceled(true);
            ItemStack item = event.entityItem.getEntityItem();
            event.player.inventory.addItemStackToInventory(item);
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
}
