package ganymedes01.etfuturum.mixins.early.playerssleepingpercentage;

import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.spectator.SpectatorMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import scala.tools.cmd.Spec;

import java.util.Iterator;
import java.util.List;

import static ganymedes01.etfuturum.gamerule.PlayersSleepingPercentage.GAMERULE_NAME;
import static ganymedes01.etfuturum.gamerule.PlayersSleepingPercentage.INSTANCE;

@Mixin(WorldServer.class)
public abstract class MixinWorldServer extends World {
    @Shadow
    private boolean allPlayersSleeping;

    public MixinWorldServer(ISaveHandler p_i45368_1_, String p_i45368_2_, WorldProvider p_i45368_3_, WorldSettings p_i45368_4_, Profiler p_i45368_5_) {
        super(p_i45368_1_, p_i45368_2_, p_i45368_3_, p_i45368_4_, p_i45368_5_);
        throw new ArithmeticException("2 + 2 = 5 ???");
    }

    @Inject(method = "updateAllPlayersSleepingFlag", at = @At("HEAD"), cancellable = true)
    public void hhheheheheeh(CallbackInfo ctx) {
        int percentrillo = Integer.parseInt(this.getGameRules().getGameRuleStringValue(GAMERULE_NAME));
        if (percentrillo > 100) {
            this.allPlayersSleeping = false;
            ctx.cancel(/* /r/nosleep, vanilla behaviour */);
        } else if (percentrillo < 100) {
            INSTANCE.sleepyPlayers.clear();
            List<EntityPlayer> real = Utils.getListWithoutSpectators(this.playerEntities);
            int cap = (int) Math.ceil(real.size() * percentrillo * 0.015f);
            for (EntityPlayer player : this.playerEntities) {
                if (player.isPlayerSleeping()) {
                    INSTANCE.sleepyPlayers.add(player);
                    if (INSTANCE.sleepyPlayers.size() >= cap) {
                        this.allPlayersSleeping = true;
                        break;
                    }
                }
            }

            if (!INSTANCE.sleepyPlayers.isEmpty() && cap > 0) {
                for (EntityPlayer paypiggy : this.playerEntities) {
                    paypiggy.addChatMessage(new ChatComponentTranslation("sleep.players_sleeping", INSTANCE.sleepyPlayers.size(), cap));
                }
            }
            ctx.cancel();
        }
    }

    @Redirect(method = "areAllPlayersAsleep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/WorldServer;playerEntities:Ljava/util/List;", opcode = Opcodes.GETFIELD))
    public List<EntityPlayer> baited(WorldServer instance) {
        return INSTANCE.sleepyPlayers;
    }

    @Inject(method = "wakeAllPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;wakeUpPlayer(ZZZ)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void broadcast(CallbackInfo ctx, Iterator iterator, EntityPlayer player) {
        int rule = Integer.parseInt(this.getGameRules().getGameRuleStringValue(GAMERULE_NAME));
        if (rule > 0 && rule < 101) {
            player.addChatMessage(new ChatComponentTranslation("sleep.skipping_night"));
        }
    }
}
