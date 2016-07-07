package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.syokkenItemLibrary;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * amejiManager
 *
 * @auther syokkendesuyo
 */
public class Listener_drop implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        //プレイヤー以外のデスだった場合キャンセル(多分ない)
        if(!(event.getEntity() instanceof Player)){
            return;
        }

        //例外処理をしたのでEntityをPlayer変数へ
        Player player = event.getEntity();

        //プレイヤーのパーミッションを確認
        if(!(player.hasPermission("amejiManager.drop"))){
            return;
        }

        //ロケーション情報を取得
        Location loc = player.getLocation();

        //アイテムの変数を宣言
        ItemStack item;

        //アイテムを生成
        if(player.getName().equalsIgnoreCase("syokkendesuyo")){
            String lore[] = {ChatColor.GRAY + "UUID:",ChatColor.GRAY + player.getUniqueId().toString(),"** ﾌﾟﾗｸﾞｲﾝ提供したﾋﾄ **"};
            item = syokkenItemLibrary.custom_item(ChatColor.YELLOW + player.getName(), 1, Material.NAME_TAG, (short) 0, lore);
        }
        else{
            String lore[] = {ChatColor.GRAY + "UUID:",ChatColor.GRAY + player.getUniqueId().toString()};
            item = syokkenItemLibrary.custom_item(ChatColor.YELLOW + player.getName(), 1, Material.NAME_TAG, (short) 0, lore);
        }


        //デスしたところにアイテムをポロリ
        loc.getWorld().dropItem(loc,item);
    }
}
