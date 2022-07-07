package ganymedes01.etfuturum.spectator;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.core.utils.helpers.SafeEnumHelperClient;
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
			SPECTATOR_GAMETYPE = SafeEnumHelperClient.addGameType("spectator", 3, "Spectator");
	}

	public static boolean isSpectator(EntityPlayer player) {
		if(player == null)
			return false;
		if(player.worldObj.isRemote) {
			if(player == Minecraft.getMinecraft().thePlayer)
				return Minecraft.getMinecraft().playerController.currentGameType == SPECTATOR_GAMETYPE;
			else
				return false;
		}
		return ((EntityPlayerMP)player).theItemInWorldManager.getGameType() == SPECTATOR_GAMETYPE;
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
}
