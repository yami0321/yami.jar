package org.yami;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

public class Additional implements Listener {
	public Yami plugin;

	public Additional(Yami plugin) {
		this.plugin = plugin;
	}

	// 釣竿でとぶ
	@EventHandler
	public void onPlayerFishing(PlayerFishEvent e) {
		if (plugin.getConfig().getBoolean("settings.function.fish-fly")) {
			e.getPlayer().getItemInHand().setDurability((short) 100000);
			if ((e.getState() == State.IN_GROUND) || (e.getState() == State.FAILED_ATTEMPT)) {
				Block b = e.getHook().getLocation().getBlock();
				Material b0 = b.getRelative(BlockFace.UP).getType();
				Material b1 = b.getRelative(BlockFace.DOWN).getType();
				Material b2 = b.getRelative(BlockFace.NORTH).getType();
				Material b3 = b.getRelative(BlockFace.NORTH_EAST).getType();
				Material b4 = b.getRelative(BlockFace.EAST).getType();
				Material b5 = b.getRelative(BlockFace.SOUTH_EAST).getType();
				Material b6 = b.getRelative(BlockFace.SOUTH).getType();
				Material b7 = b.getRelative(BlockFace.SOUTH_WEST).getType();
				Material b8 = b.getRelative(BlockFace.WEST).getType();
				Material b9 = b.getRelative(BlockFace.NORTH_WEST).getType();
				if ((b.getType() == Material.AIR) && (b0 == Material.AIR) && (b1 == Material.AIR)
						&& (b2 == Material.AIR) && (b3 == Material.AIR) && (b4 == Material.AIR) && (b5 == Material.AIR)
						&& (b6 == Material.AIR) && (b7 == Material.AIR) && (b8 == Material.AIR)
						&& (b9 == Material.AIR)) {
					return;
				}
				e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(2F));
			}
		}
	}

	// 各種ダメージをキャンセル(雷以外)
	@EventHandler
	public void onFallDamage(final EntityDamageEvent e) {
		if (plugin.getConfig().getBoolean("settings.function.cancel-damage-except-for-lightning")) {
			if (e.getEntity().getType().equals(EntityType.PLAYER)) {
				if ((!e.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING))
						&& (!e.getCause().equals(EntityDamageEvent.DamageCause.SUICIDE))) {
					e.setCancelled(true);
				} else if (e.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)
						&& (e.getEntity() instanceof Player)) {
					new BukkitRunnable() {
						@Override
						public void run() {
							Player p = (Player) e.getEntity();
							p.setHealth(20D);
						}
					}.runTaskLater(plugin, 1);
				}
			}
		}
	}

	// コマンドログ出力
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCmdLog(PlayerCommandPreprocessEvent e) {
		if (plugin.getConfig().getBoolean("settings.function.command-log")) {
			if (e.getMessage().startsWith("/")) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.hasPermission("lobbyadmin.op")) {
						p.sendMessage(ChatColor.GREEN + "*" + ChatColor.GRAY + "[Log] " + e.getPlayer().getName()
								+ " : " + e.getMessage());
					}
				}
			}
		}
	}

	// ブレイズロッドで雷落とす
	@EventHandler
	public void onBlazeStrike(PlayerInteractEvent e) {
		if (plugin.getConfig().getBoolean("settings.function.blaze-rod-strike")) {
			ItemStack is = e.getItem();
			if ((e.getAction() == Action.PHYSICAL) || is == null || is.getType() == Material.AIR) {
				return;
			}
			if (is.getType() == Material.BLAZE_ROD) {
				Player p = (Player) e.getPlayer();
				if (!p.hasPermission("lobbyadmin.op")) {
					return;
				}
				Block target = getTargetBlock(p);
				if (target != null) {
					target.getWorld().strikeLightning(target.getLocation());
				}
			}
		}
	}

	private Block getTargetBlock(Player p) {
		BlockIterator it = new BlockIterator(p, 100);
		while (it.hasNext()) {
			Block b = it.next();
			if (b.getType() != Material.AIR) {
				return b;
			}
		}
		return null;
	}

	// ベッドクリックしてもなにも起こさない
	@EventHandler
	public void onBedClick(PlayerInteractEvent e) {
		if (plugin.getConfig().getBoolean("settings.protect.bed-click")) {
			if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
					&& (e.getClickedBlock().getType().equals(Material.BED_BLOCK))) {
				e.setCancelled(true);
			}
		}
	}
}
