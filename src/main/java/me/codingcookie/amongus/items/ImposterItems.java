package me.codingcookie.amongus.items;

import me.codingcookie.amongus.AmongUs;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.ChatColor.*;

public class ImposterItems {

    private final AmongUs plugin;

    private ItemStack imposterSword;
    private ItemStack sabotageItem;

    public ImposterItems(AmongUs plugin) {
        this.plugin = plugin;
        makeImposterSword();
        makeSabotageItem();
    }

    public ItemStack makeImposterSword() {
        imposterSword = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta swordMeta = imposterSword.getItemMeta();
        swordMeta.setDisplayName(RED + "" + BOLD + "IMPOSTER SWORD");
        imposterSword.setItemMeta(swordMeta);
        return imposterSword;
    }

    public ItemStack makeSabotageItem() {
        sabotageItem = new ItemStack(Material.BELL);
        ItemMeta sabotageMeta = sabotageItem.getItemMeta();
        sabotageMeta.setDisplayName(RED + "" + BOLD + "SABOTAGE");
        sabotageItem.setItemMeta(sabotageMeta);
        return sabotageItem;
    }
}
