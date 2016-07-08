package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Utility.KDdatabase;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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

        if(args.length == 0){
            help(sender);
            return true;
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("kd")){
                if(!sender.hasPermission("amejiManager.command.kd")){
                    Msg.warning(sender,"このコマンドを実行する権限がありません");
                    return true;
                }
                KDdatabase.sort_kd(sender, 10);
                return true;
            }
            else if(args[0].equalsIgnoreCase("kill") || args[0].equalsIgnoreCase("k")){
                if(!sender.hasPermission("amejiManager.command.kill")){
                    Msg.warning(sender,"このコマンドを実行する権限がありません");
                    return true;
                }
                KDdatabase.sort_kill(sender, 10);
                return true;
            }
            else if(args[0].equalsIgnoreCase("death") || args[0].equalsIgnoreCase("d")){
                if(!sender.hasPermission("amejiManager.command.death")){
                    Msg.warning(sender,"このコマンドを実行する権限がありません");
                    return true;
                }
                KDdatabase.sort_death(sender, 10);
                return true;
            }
            else if(args[0].equalsIgnoreCase("reset")){
                if(!sender.hasPermission("amejiManager.command.reset")){
                    Msg.warning(sender,"このコマンドを実行する権限がありません");
                    return true;
                }
                Msg.success(sender, "データベースをリセットしました");
                KDdatabase.resetAll();
                return true;
            }
            Msg.warning(sender, ChatColor.YELLOW + args[0] + ChatColor.RESET + " という引数は存在しません");
            return true;
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("kd")){
                if(!sender.hasPermission("amejiManager.command.kd")){
                    Msg.warning(sender,"このコマンドを実行する権限がありません");
                    return true;
                }
                KDdatabase.sort_kd(sender,Integer.parseInt(args[1]));
                return true;
            }
            else if(args[0].equalsIgnoreCase("kill") || args[0].equalsIgnoreCase("k")){
                if(!sender.hasPermission("amejiManager.command.kill")){
                    Msg.warning(sender,"このコマンドを実行する権限がありません");
                    return true;
                }
                KDdatabase.sort_kill(sender, Integer.parseInt(args[1]));
                return true;
            }
            else if(args[0].equalsIgnoreCase("death") || args[0].equalsIgnoreCase("d")){
                if(!sender.hasPermission("amejiManager.command.death")){
                    Msg.warning(sender,"このコマンドを実行する権限がありません");
                    return true;
                }
                KDdatabase.sort_death(sender, Integer.parseInt(args[1]));
                return true;
            }
            Msg.warning(sender, ChatColor.YELLOW + args[0] + ChatColor.RESET + " という引数は存在しません");
            return true;
        }

        help(sender);
        return true;
    }

    public void help(CommandSender sender){
        Msg.info(sender, " --- " + ChatColor.GRAY + ChatColor.BOLD + "Command Help"+ ChatColor.GRAY + " - " + ChatColor.BOLD + "Result" + ChatColor.RESET + " --- ");
        Msg.commandFormat(sender, "result", "resultコマンドのヘルプを表示");
        Msg.commandFormat(sender, "result kd    <数値>", "KDレート上位のプレイヤーを表示");
        Msg.commandFormat(sender, "result kill  <数値>", "キル数上位のプレイヤーを表示");
        Msg.commandFormat(sender, "result death <数値>", "デス数上位のプレイヤーを表示");
        Msg.commandFormat(sender, "result reset", "データベースを初期化");
    }
}
