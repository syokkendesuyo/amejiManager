package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Utility.KDdatabase;
import net.jp.minecraft.plugins.Utility.Msg;
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

        if(!(sender.hasPermission("amejiManager.command"))){
            Msg.warning(sender,"このコマンドを実行する権限がありません");
            return true;
        }

        if(args.length == 0 || args.length == 1){
            Msg.info(sender,"引数が足りません");
            return true;
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("kd")){
                KDdatabase.sort_kd(sender,Integer.parseInt(args[1]));
                return true;
            }
            else if(args[0].equalsIgnoreCase("kill") || args[0].equalsIgnoreCase("k")){
                KDdatabase.sort_kill(sender, Integer.parseInt(args[1]));
                return true;
            }
            else if(args[0].equalsIgnoreCase("death") || args[0].equalsIgnoreCase("d")){
                KDdatabase.sort_death(sender, Integer.parseInt(args[1]));
                return true;
            }
            Msg.info(sender,"引数がおかc");
        }
        return true;
    }
}
