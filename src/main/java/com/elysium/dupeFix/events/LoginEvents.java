package com.elysium.dupeFix.events;

import com.elysium.dupeFix.mccr.Server;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            Server.giveStarterKit(player);
            welcomeNewPlayer(player);

            if (!Server.hasReceivedRandomItem(player)) {
                Server.givePlayerRandomItem(player);
            }
        } else {
            welcomeOldPlayer(player);
        }
    }

    public void welcomeNewPlayer(Player player) {
        Component welcomeMessage = Component.text("Welcome to Server ",
                NamedTextColor.GREEN).append(Component.text
                (player.getName(), NamedTextColor.BLUE)
        );

        player.sendActionBar(welcomeMessage);
    }

    public void welcomeOldPlayer(Player player) {
        Component welcomeMessage = Component.text("Welcome Back ",
                NamedTextColor.GREEN).append(Component.text
                (player.getName(), NamedTextColor.BLUE)
        );

        player.sendActionBar(welcomeMessage);
    }

}