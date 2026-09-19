package com.elysium.dupeFix.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class ConnectionEvents implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(null);
        broadcastJoinMessage(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(null);
        broadcastLeaveMessage(player);
    }

    public void broadcastJoinMessage(Player player) {
        Component joinMessage = Component.text("[", NamedTextColor.WHITE).append(
                Component.text("+", NamedTextColor.GREEN).append(
                        Component.text("] " , NamedTextColor.WHITE).append(
                                Component.text(player.getName(), NamedTextColor.WHITE)
                        )
                )
        );

        Bukkit.broadcast(joinMessage);

    }

    public void broadcastLeaveMessage(Player player) {
        Component leaveMessage = Component.text("[", NamedTextColor.WHITE).append(
                Component.text("+", NamedTextColor.RED).append(
                        Component.text("] " , NamedTextColor.WHITE).append(
                                Component.text(player.getName(), NamedTextColor.WHITE)
                        )
                )
        );

        Bukkit.broadcast(leaveMessage);

    }
}
