package net.jp.minecraft.plugins.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;
import java.util.*;

/**
 * amejiManager
 *
 * @auther syokkendesuyo
 */
public class KDdatabase {

    private static int add;
    private static float kd;
    private static float kd_kill;
    private static float kd_death;
    private static HashMap<UUID,Integer> kills = new HashMap<UUID, Integer>();
    private static HashMap<UUID,Integer> deaths = new HashMap<UUID, Integer>();
    private static HashMap<UUID,Float> result = new HashMap<UUID, Float>();

    /**
     * キル数をインクリメント
     * @param uuid
     */
    public static void addKill(UUID uuid){
        if(kills.containsKey(uuid) == false){
            kills.put(uuid,1);
        }
        if(deaths.containsKey(uuid) == false){
            deaths.put(uuid,0);
        }
        else{
            //インクリメント
            add = kills.get(uuid) + 1;
            kills.put(uuid,add);
        }
    }

    /**
     * キル数を取得します
     * @param uuid
     * @return
     */
    public static int getKills(UUID uuid){
        return kills.get(uuid);
    }

    /**
     * 対称のプレイヤーのみキルデータをリセット
     * @param uuid
     */
    public static void resetkill(UUID uuid){
        if(kills.containsKey(uuid) == true){
            kills.remove(uuid);
        }
    }

    /**
     * デス数をインクリメント
     * @param uuid
     */
    public static void addDeath(UUID uuid){
        if(deaths.containsKey(uuid) == false){
            deaths.put(uuid,1);
        }
        if(kills.containsKey(uuid) == false){
            kills.put(uuid,0);
        }
        else{
            //インクリメント
            add = deaths.get(uuid) + 1;
            deaths.put(uuid,add);
        }
    }

    /**
     * デス数を取得します
     * @param uuid
     * @return
     */
    public static int getDeaths(UUID uuid){
        return deaths.get(uuid);
    }

    /**
     * 対称のプレイヤーのみデスデータをリセット
     * @param uuid
     */
    public static void resetDeath(UUID uuid){
        if(deaths.containsKey(uuid) == true){
            deaths.remove(uuid);
        }
    }

    /**
     * 全てのデータを削除します
     */
    public static void resetAll(){
        kills.clear();
        deaths.clear();
        result.clear();
    }

    public static float getKD(UUID uuid){
        kd_kill = kills.get(uuid);
        kd_death = deaths.get(uuid);

        kd = kd_kill/(kd_kill + kd_death);

        return bd(kd);
    }

    public static float colKD(float kill, float death){
        kd_kill = kill;
        kd_death = death;

        kd = kd_kill/(kd_kill + kd_death);

        return bd(kd);
    }

    /**
     * 呼び出すとKDレートが算出されるメソッド
     * resultのHashMapに保存されるだけで何も起こらない
     */
    public static void col_result(){
        //HashMapを初期化 - 初期化しない場合何度か計算を行った時予期しない不具合が起きる場合がある為
        result.clear();

        for(UUID uuid : kills.keySet()){
            result.put(uuid, colKD(kills.get(uuid), deaths.get(uuid)));
        }
    }

    /**
     * kdレートを表示しちゃうメソッド
     * @return
     */
    public static void sort_kd(CommandSender sender,int size){
        col_result();

        int cnt = 0;
        sender.sendMessage("");
        Msg.success(sender, " --- " + ChatColor.RED + "♔ Result ♔ "+ ChatColor.GRAY + "-" + ChatColor.RED + " KD TOP" + size + ChatColor.RESET + " --- ");
        for(Map.Entry<UUID, Float> e : result.entrySet()){
            cnt++;
            UUID pname = e.getKey();
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(pname);
            float score = e.getValue();
            Msg.success(sender, "  " + ChatColor.GRAY + ChatColor.BOLD + cnt + ChatColor.GRAY + ".  " + ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " - " + ChatColor.GOLD + score);
            if(cnt+1>size){
                return;
            }
        }
    }

    /**
     * Deathレートを表示しちゃうメソッド
     * @return
     */
    public static void sort_kill(CommandSender sender,int size){
        col_result();

        int cnt = 0;
        sender.sendMessage("");
        Msg.success(sender, " --- " + ChatColor.RED + "♔ Result ♔ "+ ChatColor.GRAY + "-" + ChatColor.RED + " KILL TOP" + size + ChatColor.RESET + " --- ");
        for(Map.Entry<UUID, Integer> e : kills.entrySet()){
            cnt++;
            UUID pname = e.getKey();
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(pname);
            int score = e.getValue();
            Msg.success(sender, "  " + ChatColor.GRAY + ChatColor.BOLD + cnt + ChatColor.GRAY + ".  " + ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " - " + ChatColor.GOLD + score);
            if(cnt+1>size){
                return;
            }
        }
    }

    /**
     * Deathレートを表示しちゃうメソッド
     * @return
     */
    public static void sort_death(CommandSender sender,int size){
        col_result();

        int cnt = 0;
        sender.sendMessage("");
        Msg.success(sender, " --- " + ChatColor.RED + "♔ Result ♔ "+ ChatColor.GRAY + "-" + ChatColor.RED + " DEATH TOP" + size + ChatColor.RESET + " --- ");
        for(Map.Entry<UUID, Integer> e : deaths.entrySet()){
            cnt++;
            UUID pname = e.getKey();
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(pname);
            int score = e.getValue();
            Msg.success(sender, "  " + ChatColor.GRAY + ChatColor.BOLD + cnt + ChatColor.GRAY + ".  " + ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " - " + ChatColor.GOLD + score);
            if(cnt+1>size){
                return;
            }
        }
    }

    /**
     * 少数を四捨五入するメソッド
     * @param num
     * @return
     */
    private static float bd(double num){
        BigDecimal bd = new BigDecimal(num);
        BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd2.floatValue();
    }
}
