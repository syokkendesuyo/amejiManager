package net.jp.minecraft.plugins;

import net.jp.minecraft.plugins.Listener.Listener_death;
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

    public void onEnable(){
        getLogger().info("amejiManager が正常に読み込まれました");
        getLogger().info("Auther : syokkendesuyo");

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new Listener_death(), this);

        instance = this;

    }

    public static amejiManager getInstance()
    {
        return instance;
    }
}
