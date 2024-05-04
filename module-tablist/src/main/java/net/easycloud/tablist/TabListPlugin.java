package net.easycloud.tablist;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import net.easycloud.api.conf.FileHelper;
import net.kyori.adventure.text.Component;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Getter
@Plugin(id = "cloudtablist", name = "CloudTabList", authors = "FlxwDNS", version = "1.0.0")
@SuppressWarnings("unused")
public final class TabListPlugin {
    @Getter
    private static TabListPlugin instance;

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public TabListPlugin(ProxyServer server, Logger logger) {
        instance = this;

        this.server = server;
        this.logger = logger;

        server.getConsoleCommandSource().sendMessage(Component.text("§7TabList module was §9found§7."));
    }

    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {
        var path = Path.of("plugins").resolve("modules").resolve("TabListModule");
        if(!path.toFile().exists()) {
            path.toFile().mkdirs();
        }
        FileHelper.writeIfNotExists(path, new TabListConfiguration());
        var config = FileHelper.read(path, TabListConfiguration.class);

        TabListPlugin.getInstance().getServer().getScheduler().buildTask(TabListPlugin.getInstance(), () -> {
            TabListPlugin.getInstance().getServer().getAllPlayers().forEach(player -> {
                player.getTabList().setHeaderAndFooter(Component.text(replace(player, config.getHeader())), Component.text(replace(player, config.getFooter())));
            });
        }).repeat(2, TimeUnit.SECONDS).schedule();
    }


    private String replace(Player player, String text) {
        var server = player.getCurrentServer().orElse(null);
        if(server == null) {
            return "§cError";
        }
        //TODO DO PROXY NAME
        return text
                .replace("%online%", String.valueOf(this.server.getPlayerCount()))
                .replace("%max%", String.valueOf(this.server.getConfiguration().getShowMaxPlayers()))
                .replace("%server%", server.getServerInfo().getName())
                .replace("%proxy%", "Proxy-1")
                .replace("\\n", "\n");
    }
}
