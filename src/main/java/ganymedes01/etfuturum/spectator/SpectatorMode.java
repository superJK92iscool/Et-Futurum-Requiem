package ganymedes01.etfuturum.spectator;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.core.utils.helpers.SafeEnumHelperClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

public class SpectatorMode {
	public static final SpectatorMode INSTANCE = new SpectatorMode();
	public static final IEntitySelector EXCEPT_SPECTATING = p_82704_1_ -> !(p_82704_1_ instanceof EntityPlayer) || !isSpectator((EntityPlayer) p_82704_1_);
	SpectatorMode() {

	}
	public static WorldSettings.GameType SPECTATOR_GAMETYPE = null;
	public static void init() {
		if(ConfigMixins.enableSpectatorMode)
			SPECTATOR_GAMETYPE = SafeEnumHelperClient.addGameType("spectator", 3, "Spectator");
	}

	public static boolean isSpectator(EntityPlayer player) {
		if(player == null || player instanceof FakePlayer || player.worldObj == null)
			return false;
		if(player.worldObj.isRemote) {
			if(player == FMLClientHandler.instance().getClient().thePlayer && FMLClientHandler.instance().getClient().playerController != null)
				return FMLClientHandler.instance().getClient().playerController.currentGameType == SPECTATOR_GAMETYPE;
			return false;
		}
		return player instanceof EntityPlayerMP && ((EntityPlayerMP) player).theItemInWorldManager.getGameType() == SPECTATOR_GAMETYPE;
	}

	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event) {
		if(isSpectator(event.entityPlayer)) {
			if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
				event.setCanceled(true);
			} else {
				if (!event.world.blockExists(event.x, event.y, event.z)) {
					event.setCanceled(true); //I don't trust other mods not to do weird shit in this scenario
					return;
				}
				TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
				if (!(te instanceof IInventory || te instanceof TileEntityEnderChest)) {
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
				if (event.player.worldObj.isRemote) {
					//Reloads the player model, fixes empty hand under certain conditions when switching from spectator mode
					//Just calls the render function for one frame to reload the player model. I do it this way to avoid access transforming, and to be sure the whole model is reloaded.
					if (event.player == FMLClientHandler.instance().getClient().thePlayer && FMLClientHandler.instance().getClient().playerController != null) {
						RenderManager.instance.renderEntityWithPosYaw(event.player, -180.0D, -180.0D, -180.0D, 0.0F, 0.0F);
					}
				}
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
}
