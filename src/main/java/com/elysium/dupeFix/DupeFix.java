package com.elysium.dupeFix;

import com.elysium.dupeFix.events.CancelAdvancementMessage;
import com.elysium.dupeFix.events.ConnectionEvents;
import com.elysium.dupeFix.events.LoginEvents;
import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public final class DupeFix extends JavaPlugin {

    private static DupeFix instance;

    public static DupeFix getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();
        getServer().getPluginManager().registerEvents(new LoginEvents(), this);
        getServer().getPluginManager().registerEvents(new CancelAdvancementMessage(), this);
        getServer().getPluginManager().registerEvents(new ConnectionEvents(), this);
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}