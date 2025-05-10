package me.bllry.client.features.modules.misc;

import me.bllry.client.features.commands.Command;
import me.bllry.client.features.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.UUID;

public class PearlNotify extends Module {
    private final HashMap<PlayerEntity, UUID> list;
    private Entity enderPearl;
    private boolean flag;

    public PearlNotify() {
        super("PearlNotify", "Notify pearl throws.", Category.MISC, true, false, false);
        this.list = new HashMap<PlayerEntity, UUID>();
    }

    @Override
    public void onEnable() {
        this.flag = true;
    }

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null) {
            return;
        }
        this.enderPearl = null;
        for (final Entity e : mc.world.getEntities()) {
            if (e instanceof EnderPearlEntity) {
                this.enderPearl = e;
                break;
            }
        }
        if (this.enderPearl == null) {
            this.flag = true;
            return;
        }
        PlayerEntity closestPlayer = null;
        for (final PlayerEntity entity : mc.world.getPlayers()) {
            if (closestPlayer == null) {
                closestPlayer = entity;
            } else {
                if (closestPlayer.distanceTo(this.enderPearl) <= entity.distanceTo(this.enderPearl)) {
                    continue;
                }
                closestPlayer = entity;
            }
        }
        if (closestPlayer == mc.player) {
            this.flag = false;
        }
        if (closestPlayer != null && this.flag) {
            String facing = this.enderPearl.getHorizontalFacing().toString();
            if (facing.equals("west")) {
                facing = "east";
            } else if (facing.equals("east")) {
                facing = "west";
            }

            String playerName = closestPlayer.getName().getString();
            String message;
            message = Formatting.LIGHT_PURPLE + playerName + Formatting.DARK_GRAY + " has just thrown a pearl heading " + facing + "!";


            Command.sendMessage(message);
            this.flag = false;
        }
    }
}