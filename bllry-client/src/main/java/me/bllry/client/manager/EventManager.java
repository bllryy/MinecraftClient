package me.bllry.client.manager;

import com.google.common.eventbus.Subscribe;
import me.bllry.client.bllry;
import me.bllry.client.event.Stage;
import me.alpha432.oyvey.event.impl.*;
import me.bllry.client.event.impl.*;
import me.bllry.client.features.Feature;
import me.bllry.client.features.commands.Command;
import me.bllry.client.util.models.Timer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.Formatting;

public class EventManager extends Feature {
    private final Timer logoutTimer = new Timer();

    public void init() {
        EVENT_BUS.register(this);
    }

    public void onUnload() {
        EVENT_BUS.unregister(this);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        mc.getWindow().setTitle("bllry 0.0.3");
        if (!fullNullCheck()) {
//            bllry.inventoryManager.update();
            bllry.moduleManager.onUpdate();
            bllry.moduleManager.sortModules(true);
            onTick();
//            if ((HUD.getInstance()).renderingMode.getValue() == HUD.RenderingMode.Length) {
//                bllry.moduleManager.sortModules(true);
//            } else {
//                bllry.moduleManager.sortModulesABC();
//            }
        }
    }

    public void onTick() {
        if (fullNullCheck())
            return;
        bllry.moduleManager.onTick();
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == null || player.getHealth() > 0.0F)
                continue;
            EVENT_BUS.post(new DeathEvent(player));
//            PopCounter.getInstance().onDeath(player);
        }
    }

    @Subscribe
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (fullNullCheck())
            return;
        if (event.getStage() == Stage.PRE) {
            bllry.speedManager.updateValues();
            bllry.rotationManager.updateRotations();
            bllry.positionManager.updatePosition();
        }
        if (event.getStage() == Stage.POST) {
            bllry.rotationManager.restoreRotations();
            bllry.positionManager.restorePosition();
        }
    }

    @Subscribe
    public void onPacketReceive(PacketEvent.Receive event) {
        bllry.serverManager.onPacketReceived();
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket)
            bllry.serverManager.update();
    }

    @Subscribe
    public void onWorldRender(Render3DEvent event) {
        bllry.moduleManager.onRender3D(event);
    }

    @Subscribe public void onRenderGameOverlayEvent(Render2DEvent event) {
        bllry.moduleManager.onRender2D(event);
    }

    @Subscribe public void onKeyInput(KeyEvent event) {
        bllry.moduleManager.onKeyPressed(event.getKey());
    }

    @Subscribe public void onChatSent(ChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.cancel();
            try {
                if (event.getMessage().length() > 1) {
                    bllry.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                } else {
                    Command.sendMessage("Please enter a command.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage(Formatting.RED + "An error occurred while running this command. Check the log!");
            }
        }
    }
}