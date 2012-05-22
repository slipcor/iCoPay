package net.slipcor.icopay;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.iCo6.system.Accounts;
import com.iConomy.iConomy;

/**
 * main class
 * 
 * @author slipcor
 * 
 * @version: v0.0.2
 * 
 */

public class iCoPay extends JavaPlugin {
	private int pay_id = -1;

	@Override
	public void onEnable() {
		getConfig().addDefault("interval", 15);
		getConfig().addDefault("amount", 25.00d);
		getConfig().options().copyDefaults(true);
		saveConfig();

		int interval = getConfig().getInt("interval", 0) * 20 * 60;

		if (interval > 0) {
			pay_id = Bukkit
					.getServer()
					.getScheduler()
					.scheduleSyncRepeatingTask(this, autoPay, interval,
							interval);
		}
		Bukkit.getLogger().info(getDescription().getFullName() + " enabled");
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTask(pay_id);
		Bukkit.getLogger().info(getDescription().getFullName() + " disabled");
	}

	private Runnable autoPay = new Runnable() {
		public void run() {
			Player[] players = Bukkit.getOnlinePlayers();
			double d = getConfig().getDouble("amount", 25.00d);
			for (Player p : players) {
				try {
					iConomy.Accounts.get(p.getName()).getHoldings().add(d);
					p.sendMessage("§2[§fMoney§2] Your account had §f"
							+ iConomy.format(d) + " §2credited.");
				} catch (Exception e) {
					// I don't care
				}
				try {
					(new Accounts()).get(p.getName()).getHoldings().add(d);
					p.sendMessage("§2[§fMoney§2] Your account had §f"
							+ com.iCo6.iConomy.format(d) + " §2credited.");
				} catch (Exception e) {
					// I don't care
				}
				
			}
		}
	};
}