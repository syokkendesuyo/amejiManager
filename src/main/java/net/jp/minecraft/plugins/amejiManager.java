package net.jp.minecraft.plugins;

import net.jp.minecraft.plugins.Commands.Command_result;
import net.jp.minecraft.plugins.Listener.Listener_KD;
import net.jp.minecraft.plugins.Listener.Listener_drop;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * amejiManager
 *
 * @auther syokkendesuyo
 */
public class amejiManager extends JavaPlugin implements Listener {

    //インスタンス
    private static amejiManager instance;

    public void onEnable() {
        getLogger().info("amejiManager が正常に読み込まれました");
        getLogger().info("Auther : syokkendesuyo");

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new Listener_drop(), this);
        pm.registerEvents(new Listener_KD(), this);

        getCommand("result").setExecutor(new Command_result());

        instance = this;

    }

    public static amejiManager getInstance() {
        return instance;
    }
}
