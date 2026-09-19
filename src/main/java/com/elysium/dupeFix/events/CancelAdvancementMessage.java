package com.elysium.dupeFix.events;

import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class CancelAdvancementMessage implements Listener {
    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();

        Advancement advancement = event.getAdvancement();
        AdvancementProgress progress = player.getAdvancementProgress(advancement);

        for (String criteria : progress.getAwardedCriteria()) {
            progress.revokeCriteria(criteria);
        }

    }
}
