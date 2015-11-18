package org.yami;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cg implements CommandExecutor {
	private String prefix = Yami.prefix;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(prefix + "ゲーム内チャットから実行してください。");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("Yami.gamemode")) {
			p.sendMessage(prefix + "権限がありま・・・しぇん");
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(prefix + "/gm 0,1,2,3");
			return true;
		}
		switch (args[0]) {
		case "0":
		case "survival":
		case "s": {
			p.setGameMode(GameMode.SURVIVAL);
			return true;
		}
		case "1":
		case "creative":
		case "c": {
			p.setGameMode(GameMode.CREATIVE);
			return true;
		}
		case "2":
		case "adventure":
		case "a": {
			p.setGameMode(GameMode.ADVENTURE);
			return true;
		}
		case "3":
		case "spectator":
		case "sp": {
			p.setGameMode(GameMode.SPECTATOR);
			return true;
		}
		default: {
			p.sendMessage(prefix + "/gm 0,1,2,3");
			return true;
		}
		}
	}
}
