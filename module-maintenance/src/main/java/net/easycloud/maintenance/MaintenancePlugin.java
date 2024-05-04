package net.easycloud.maintenance;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.easycloud.api.CloudDriver;
import net.easycloud.api.permission.PermissionUser;
import net.kyori.adventure.text.Component;

import java.util.logging.Logger;

@Getter
@Plugin(id = "cloudmaintenance", name = "CloudMaintenance", authors = "FlxwDNS", version = "1.0.0")
@SuppressWarnings("unused")
public final class MaintenancePlugin {
    @Getter
    private static MaintenancePlugin instance;

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public MaintenancePlugin(ProxyServer server, Logger logger) {
        instance = this;

        this.server = server;
        this.logger = logger;

        server.getConsoleCommandSource().sendMessage(Component.text("§7Maintenance module was §9found§7."));
    }

    @Subscribe
    public void onLogin(LoginEvent event) {
        if(CloudDriver.getInstance().getPermissionProvider().getUser(event.getPlayer().getUniqueId()) == null) {
            CloudDriver.getInstance().getPermissionProvider().getRepository().query().create(new PermissionUser(event.getPlayer().getUniqueId(), ""));
        }
        var permissions = CloudDriver.getInstance().getPermissionProvider().getUser(event.getPlayer().getUniqueId()).getPermissions();
        if(!permissions.contains("*") && !permissions.contains("cloud.maintenance") && !event.getPlayer().hasPermission("cloud.maintenance")) {
            event.setResult(LoginEvent.ComponentResult.denied(Component.text("§cMaintenance is enabled§8!")));
        }
    }

}
