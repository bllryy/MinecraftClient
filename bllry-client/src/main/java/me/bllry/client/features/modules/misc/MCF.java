package me.bllry.client.features.modules.misc;

import me.bllry.client.bllry;
import me.bllry.client.features.commands.Command;
import me.bllry.client.features.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class MCF extends Module {
    private boolean pressed;
    public MCF() {
        super("MCF", "Middle click friend", Category.MISC, true, false, false);
    }

    @Override public void onTick() {
        if (GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), 2) == 1) {
            if (!pressed) {
                Entity targetedEntity = mc.targetedEntity;
                if (!(targetedEntity instanceof PlayerEntity)) return;
                String name = ((PlayerEntity) targetedEntity).getGameProfile().getName();

                if (bllry.friendManager.isFriend(name)) {
                    bllry.friendManager.removeFriend(name);
                    Command.sendMessage(Formatting.RED + name + Formatting.RED + " has been unfriended.");
                } else {
                    bllry.friendManager.addFriend(name);
                    Command.sendMessage(Formatting.AQUA + name + Formatting.AQUA + " has been friended.");
                }

                pressed = true;
            }
        } else {
            pressed = false;
        }
    }
}
