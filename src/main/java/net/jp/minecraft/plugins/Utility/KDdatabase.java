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
    private static HashMap<String,Float> killsString = new HashMap<String, Float>();
    private static HashMap<UUID,Integer> deaths = new HashMap<UUID, Integer>();
    private static HashMap<String,Float> deathsString = new HashMap<String, Float>();
    private static HashMap<UUID,Float> result = new HashMap<UUID, Float>();
    private static HashMap<String,Float> resultString = new HashMap<String, Float>();

    /**
     * デバッグ
     */
    public static void debug(UUID uuid, int kill, int death) {
        kills.put(uuid, kill);
        deaths.put(uuid, death);
    }

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
            resultString.put(Bukkit.getOfflinePlayer(uuid).getName(), colKD(kills.get(uuid), deaths.get(uuid)));
        }
    }

    private static void killsHashMapKeysToString(){
        for(UUID uuid : kills.keySet()){
            killsString.put(Bukkit.getOfflinePlayer(uuid).getName(), (float)kills.get(uuid));
        }
    }

    private static void deathsHashMapKeysToString(){
        for(UUID uuid : deaths.keySet()){
            deathsString.put(Bukkit.getOfflinePlayer(uuid).getName(), (float)deaths.get(uuid));
        }
    }

    /**
     * kdレートを表示しちゃうメソッド
     * @return
     */
    public static void sort_kd(CommandSender sender,int size){
        col_result();

        sender.sendMessage("");
        Msg.success(sender, " --- " + ChatColor.RED + "♔ Result ♔ "+ ChatColor.GRAY + "-" + ChatColor.RED + " KD TOP" + size + ChatColor.RESET + " --- ");
        sort_down(sender, size, resultString, false);
    }

    /**
     * Deathレートを表示しちゃうメソッド
     * @return
     */
    public static void sort_kill(CommandSender sender,int size){
        col_result();
        killsHashMapKeysToString();

        int cnt = 0;
        sender.sendMessage("");
        Msg.success(sender, " --- " + ChatColor.RED + "♔ Result ♔ "+ ChatColor.GRAY + "-" + ChatColor.RED + " KILL TOP" + size + ChatColor.RESET + " --- ");
        sort_down(sender, size, killsString, true);
    }

    /**
     * Deathレートを表示しちゃうメソッド
     * @return
     */
    public static void sort_death(CommandSender sender,int size){
        col_result();
        deathsHashMapKeysToString();

        int cnt = 0;
        sender.sendMessage("");
        Msg.success(sender, " --- " + ChatColor.RED + "♔ Result ♔ "+ ChatColor.GRAY + "-" + ChatColor.RED + " DEATH TOP" + size + ChatColor.RESET + " --- ");
        sort_down(sender, size, deathsString, true);
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

    private static void sort_up(CommandSender sender, int size, HashMap map){
        //ソート用リスト
        int cnt = 0;
        List<Map.Entry<String, Float>> entries = new ArrayList<Map.Entry<String, Float>>(map.entrySet());

        //Comparator で Map.Entry の値を比較
        Collections.sort(entries, new Comparator<Map.Entry<String, Float>>() {
            //比較関数
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                return o1.getValue().compareTo(o2.getValue());    //昇順
            }
        });
        for (Map.Entry<String, Float> e : entries) {
            cnt++;
            Msg.success(sender, "  " + ChatColor.GRAY + ChatColor.BOLD + cnt + ChatColor.GRAY + ".  " + ChatColor.YELLOW + e.getKey() + ChatColor.GRAY + " - " + ChatColor.GOLD + e.getValue());
            if(cnt+1>size){
                return;
            }
        }
    }

    private static void sort_down(CommandSender sender, int size, HashMap map ,Boolean toInt){
        //ソート用リスト
        int cnt = 0;
        List<Map.Entry<String, Float>> entries = new ArrayList<Map.Entry<String, Float>>(map.entrySet());

        //Comparator で Map.Entry の値を比較
        Collections.sort(entries, new Comparator<Map.Entry<String, Float>>() {
            //比較関数
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                return o2.getValue().compareTo(o1.getValue());    //降順
            }
        });
        for (Map.Entry<String, Float> e : entries) {
            cnt++;
            if(toInt){
                float val = e.getValue();//直接キャストできなかったので変数化
                Msg.success(sender, "  " + ChatColor.GRAY + ChatColor.BOLD + cnt + ChatColor.GRAY + ".  " + ChatColor.YELLOW + e.getKey() + ChatColor.GRAY + " - " + ChatColor.GOLD + (int)val);
            } else {
                Msg.success(sender, "  " + ChatColor.GRAY + ChatColor.BOLD + cnt + ChatColor.GRAY + ".  " + ChatColor.YELLOW + e.getKey() + ChatColor.GRAY + " - " + ChatColor.GOLD + e.getValue());
            }
            if(cnt+1>size){
                return;
            }
        }
    }
}
