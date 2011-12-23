package net.slipcor.icopay;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.iConomy;

/*
 * main class
 * 
 * author: slipcor
 * 
 * version: v0.0.0 - basic functionality
 * 
 * history:
 * 
 *     v0.0.0 - basic functionality
 * 
 */

public class iCoPay extends JavaPlugin {
	private final Logger log = Logger.getLogger("Minecraft");
	private int SPAWN_ID = -1;

	@Override
	public void onEnable() {
		log.info("[iCoPay] " + getDescription().getVersion() + " enabled");
		getConfig().addDefault("interval", 15);
		getConfig().addDefault("amount", 25.00d);
		getConfig().options().copyDefaults(true);
		saveConfig();

		int interval = getConfig().getInt("interval", 0) * 20 * 60;

		if (interval > 0) {
			SPAWN_ID = Bukkit
					.getServer()
					.getScheduler()
					.scheduleSyncRepeatingTask(this, autoSave, interval,
							interval);
		}

	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTask(SPAWN_ID);
		log.info("[iCoPay] " + getDescription().getVersion() + " disabled");
	}

	private Runnable autoSave = new Runnable() {
		public void run() {
			Player[] players = Bukkit.getOnlinePlayers();
			double d = getConfig().getDouble("amount", 25.00d);
			for (Player p : players) {
				try {
					iConomy.Accounts.get(p.getName()).getHoldings().add(d);
					p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.WHITE
							+ "Money" + ChatColor.DARK_GREEN + "] "
							+ ChatColor.DARK_GREEN + "Your account had "
							+ ChatColor.WHITE + iConomy.format(d)
							+ ChatColor.DARK_GREEN + " credited.");
				} catch (Exception e) {
					// I don't care
				}
			}
		}
	};
}