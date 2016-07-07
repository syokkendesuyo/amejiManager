package net.jp.minecraft.plugins.Utility;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.*;

/**
 * amejiManager
 *
 * @auther syokkendesuyo
 */
public class KDdatabase {

    public static int add;
    public static float kd;
    public static float kd_kill;
    public static float kd_death;
    public static HashMap<UUID,Integer> kills = new HashMap<UUID, Integer>();
    public static HashMap<UUID,Integer> deaths = new HashMap<UUID, Integer>();
    public static HashMap<UUID,Float> result = new HashMap<UUID, Float>();

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
        for(UUID uuid : kills.keySet()){
            result.put(uuid, colKD(kills.get(uuid), deaths.get(uuid)));
        }
    }

    /**
     * ハッシュマップを渡すとソートされたArrayで返してくれるメソッド
     * @return
     */
    public static void sort_kd(CommandSender sender){
        col_result();

        int cnt = 1;
        Msg.success(sender, " --- " + ChatColor.RED + "Result "+ ChatColor.GRAY + "-" + ChatColor.RED + " KD TOP5" + ChatColor.RESET + " --- ");
        for(Map.Entry<UUID, Float> e : result.entrySet()){
            cnt++;
            UUID pname = e.getKey();
            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(pname);
            float score = e.getValue();
            Msg.success(sender, ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " - " + ChatColor.GOLD + score);
            if(cnt>5){
                return;
            }
        }
    }

    /**
     * 少数を四捨五入するメソッド
     * @param num
     * @return
     */
    public static float bd(double num){
        BigDecimal bd = new BigDecimal(num);
        BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd2.floatValue();
    }
}
