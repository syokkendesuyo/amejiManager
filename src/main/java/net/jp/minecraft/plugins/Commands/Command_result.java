package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Utility.KDdatabase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * amejiManager
 *
 * @auther syokkendesuyo
 */
public class Command_result implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        
        KDdatabase.sort_kd(sender);

        return true;
    }
}
