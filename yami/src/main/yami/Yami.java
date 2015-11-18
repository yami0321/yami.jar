package org.yami;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Yami extends JavaPlugin implements Listener {
	static Plugin instance;
	private Date launchTime;
	public static Yami plugin;
	public static String prefix;

	public void onEnable() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new Additional(this), this);
		pm.registerEvents(new TabAPI(this), this);
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "有効になったよ");
		launchTime = DateUtils.addHours(new Date(), 1);

		getCommand("g").setExecutor(new cg());
		getCommand("head").setExecutor(new head());
		getCommand("fly").setExecutor(new Fly());
	}

	// チャットカラー
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		e.setFormat("<" + ChatColor.GREEN + e.getPlayer().getDisplayName() + ChatColor.WHITE + ">:" + ChatColor.YELLOW
				+ e.getMessage());
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if ((e.toWeatherState())) {
			e.setCancelled(true);
		}
	}

	// Join
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = (Player) e.getPlayer();
		// Player JoinMessage!
		e.setJoinMessage("");

		p.sendMessage(ChatColor.GREEN + "--------------------------------------");
		p.sendMessage(ChatColor.YELLOW + "Welcome To Yami0321 Server!!");
		p.sendMessage(ChatColor.YELLOW + "ようこそ！Yami0321サーバーへ!");
		p.sendMessage(ChatColor.YELLOW + p.getDisplayName() + "様、ごゆっくりどうぞ。");
		p.sendMessage(ChatColor.GREEN + "--------------------------------------");
	}

	// サーバーmotd
	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		Date currentTime = new Date();
		DateFormat formatter = SimpleDateFormat.getTimeInstance();
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

		// See if the game has event started
		if (launchTime == null) {
			event.setMotd("");
		} else {
			// Note: Doesn't take into account leap seconds, ect.
			long milliDelta = launchTime.getTime() - currentTime.getTime();

			if (milliDelta > 0) {
				event.setMotd(ChatColor.GREEN + "Yami0321 Server!");
			}
		}
	}

	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "無効になったよ");
	}

	public static Plugin getInstance() {
		return instance;
	}
}
