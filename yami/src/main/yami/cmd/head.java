package org.yami;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class head implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (label.equals("head")){
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			meta.setOwner(p.getPlayer().getName());
			meta.setDisplayName(ChatColor.GREEN + p.getPlayer().getName() + "のヘッド");
			skull.setItemMeta(meta);

			p.getPlayer().getInventory().addItem(skull);
			p.getPlayer().sendMessage(ChatColor.GREEN + p.getDisplayName() + "様の頭を配布します。");
		}
		return false;
	}
}
