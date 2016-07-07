package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.KDdatabase;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * amejiManager
 *
 * @auther syokkendesuyo
 */
public class Listener_KD implements Listener{

    @EventHandler
    public void death(EntityDeathEvent event){

        if(!(event.getEntity() instanceof Player)){
            return;
        }

        Player player = (Player) event.getEntity();

        if(!(player.hasPermission("amejiManager.death"))){
            return;
        }

        KDdatabase.addDeath(player.getUniqueId());

        Msg.info(player, ChatColor.GREEN + "キル数 " + ChatColor.GRAY + ">> " + ChatColor.RESET + KDdatabase.getKills(player.getUniqueId()) + ChatColor.DARK_GRAY
                + " | "+ ChatColor.RED   + "デス数 " + ChatColor.GRAY + ">> " + ChatColor.RESET + KDdatabase.getDeaths(player.getUniqueId()) + ChatColor.DARK_GRAY
                + " | "+ ChatColor.GOLD  + "KDレート "  + ChatColor.GRAY + ">> " + ChatColor.RESET + KDdatabase.getKD(player.getUniqueId()));

    }

    @EventHandler
    public void killer(PlayerDeathEvent event){

        if(!(event.getEntity().getKiller() instanceof Player)){
            return;
        }

        Player player = event.getEntity().getKiller();
        KDdatabase.addKill(player.getUniqueId());

        Msg.info(player, ChatColor.GREEN + "キル数 " + ChatColor.GRAY + ">> " + ChatColor.RESET + KDdatabase.getKills(player.getUniqueId()) + ChatColor.DARK_GRAY
                + " | "+ ChatColor.RED   + "デス数 " + ChatColor.GRAY + ">> " + ChatColor.RESET + KDdatabase.getDeaths(player.getUniqueId()) + ChatColor.DARK_GRAY
                + " | "+ ChatColor.GOLD  + "KDレート "  + ChatColor.GRAY + ">> " + ChatColor.RESET + KDdatabase.getKD(player.getUniqueId()));
    }
}
