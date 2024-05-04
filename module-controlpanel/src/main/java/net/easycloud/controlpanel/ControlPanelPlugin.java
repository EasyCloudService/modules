package net.easycloud.controlpanel;

import de.flxwdev.ascan.AscanAPI;
import de.flxwdev.ascan.inventory.misc.InventoryConfig;
import lombok.Getter;
import net.easycloud.controlpanel.commands.ControlPanelCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ControlPanelPlugin extends JavaPlugin {
    @Getter
    private static ControlPanelPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        AscanAPI.init(this, new InventoryConfig());
        Bukkit.getConsoleSender().sendMessage("§7ControlPanel module was §9found§7.");

        getCommand("control-panel").setExecutor(new ControlPanelCommand());
    }
}
