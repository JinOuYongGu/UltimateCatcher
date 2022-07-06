package com.songoda.ultimatecatcher.hook.hooks;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.songoda.ultimatecatcher.hook.ExternalHook;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ResidenceHook implements ExternalHook {
    private final ResidenceManager manager = Residence.getInstance().getResidenceManager();

    @Override
    public boolean shouldStopCapture(Player attemptingPlayer, Entity entityToCapture) {
        return shouldStopCaptureOnRes(attemptingPlayer, entityToCapture);
    }

    private boolean shouldStopCaptureOnRes(Player player, Entity entity) {
        ClaimedResidence res = manager.getByLoc(entity.getLocation());
        if (res == null) {
            return false;
        }

        ResidencePermissions resPerm = res.getPermissions();
        return !resPerm.playerHas(player, Flags.animalkilling, true);
    }
}
