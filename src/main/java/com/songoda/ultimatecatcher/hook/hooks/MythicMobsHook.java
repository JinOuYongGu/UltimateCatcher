package com.songoda.ultimatecatcher.hook.hooks;

import com.songoda.ultimatecatcher.hook.ExternalHook;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MythicMobsHook implements ExternalHook {

    @Override
    public boolean shouldStopCapture(Player attemptingPlayer, Entity entityToCapture) {
        return shouldStopCaptureIfMythicMob(attemptingPlayer, entityToCapture);
    }

    private boolean shouldStopCaptureIfMythicMob(Player player, Entity entity) {
        return MythicBukkit.inst().getAPIHelper().isMythicMob(entity);
    }
}
