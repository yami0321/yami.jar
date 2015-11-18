package org.yami;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equals("fly")) {
			if (sender.hasPermission("yami.fly")) {
				if (args.length == 0 && sender instanceof Player) {
					Player p = (Player) sender;
					if (p.getAllowFlight()) {
						p.setAllowFlight(false);
						p.sendMessage(ChatColor.RED + "空を飛べなくなります");
					} else {
						p.setAllowFlight(true);
						p.sendMessage(ChatColor.GOLD + "空を飛べるようになりました");
					}
				} else if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "/fly or /fly <Player>");
				}
				if (args.length > 0) {
					switch (args.length) {
					case 1: {
						Player p = Bukkit.getPlayer(args[0]);
						if (p == null) {
							sender.sendMessage(ChatColor.RED + "プレイヤーがいません。");
						} else {
							if (p.getAllowFlight()) {
								p.setAllowFlight(false);
								p.sendMessage(ChatColor.RED + "空を飛べなくなります");
							} else {
								p.setAllowFlight(true);
								p.sendMessage(ChatColor.GOLD + "空を飛べるようになりました");
							}
						}
					}
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "権限がありません。");
			}
		}
		return false;
	}
}
