
package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Utility.syokkenItemLibrary;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
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
    public void tagDrop(PlayerDeathEvent event) {
        //nullチェック
        if (event.getEntity() == null) {
            return;
        }

        //ダメージを受けたプレイヤー
        Player player = event.getEntity();

        //ダメージを与えたEntity
        Entity killer = player.getKiller();

        //キラーが存在しない場合は無効
        if (killer == null) {
            return;
        }

        //プレイヤーのパーミッションを確認
        if (!(player.hasPermission("amejiManager.drop"))) {
            return;
        }

        //ロケーション情報を取得
        Location loc = player.getLocation();

        //アイテムの変数を宣言
        ItemStack item;

        //アイテムを生成
        if (player.getName().equalsIgnoreCase("syokkendesuyo")) {
            item = getItem({ChatColor.GRAY + "UUID:", ChatColor.GRAY + player.getUniqueId().toString(), "** ﾌﾟﾗｸﾞｲﾝ提供したﾋﾄ **"})
        } else {
            item = getItem({ChatColor.GRAY + "UUID:", ChatColor.GRAY + player.getUniqueId().toString()})
        }

        //デスしたところにアイテムをポロリ
        loc.getWorld().dropItem(loc, item);
    }

    public static item getItem (Strin lore[]){
        String lore[] = {ChatColor.GRAY + "UUID:", ChatColor.GRAY + player.getUniqueId().toString()};
        item = syokkenItemLibrary.custom_item(ChatColor.YELLOW + player.getName(), 1, Material.NAME_TAG, (short) 0, lore);
    }
}
