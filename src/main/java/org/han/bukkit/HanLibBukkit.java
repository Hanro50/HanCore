package org.han.bukkit;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.han.debug.DLog;
import org.han.debug.Linker;
import org.han.debug.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.md_5.bungee.api.ChatColor;

public class HanLibBukkit extends JavaPlugin implements Linker {

	static Map<URL, JavaPlugin> data = new HashMap<URL, JavaPlugin>();

	public static void registerLogger(JavaPlugin plugin) {
		data.put(plugin.getClass().getProtectionDomain().getCodeSource().getLocation(), plugin);
	}

	private Logger getHanLibLogger(StackTraceElement LastObject) {
		Logger logger;
		try {
			logger = data.getOrDefault(
					Class.forName(LastObject.getClassName()).getProtectionDomain().getCodeSource().getLocation(), this)
					.getLogger();
		} catch (ClassNotFoundException e) {
			logger = this.getLogger();
		}
		return logger;
	}

	@Override
	public void print(@NotNull org.han.debug.Linker.LEVEL level, @NotNull StackTraceElement LastObject,
			@Nullable String message) {
		Logger logger = getHanLibLogger(LastObject);
	

		switch (level) {
		case err:
			logger.log(Level.SEVERE, ChatColor.RED + "[" + LastObject.getClassName() + "][ERROR:"
					+ LastObject.getLineNumber() + "]:" + message);
			break;
		case out:
			logger.log(Level.INFO, ChatColor.GREEN + "[L:" + LastObject.getLineNumber() + "]:" + message);
			break;
		case pek:
			logger.log(Level.FINEST, ChatColor.LIGHT_PURPLE + "[L:" + LastObject.getLineNumber() + "]:" + message);
			break;
		case rep:
			logger.log(Level.FINE, ChatColor.BLUE + "[L:" + LastObject.getLineNumber() + "]:" + message);
			break;
		case wrn:
			logger.log(Level.WARNING, ChatColor.YELLOW + "[L:" + LastObject.getLineNumber() + "]:" + message);
			break;
		default:
			logger.log(Level.FINER, ChatColor.WHITE + "[L:" + LastObject.getLineNumber() + "]:" + message);
			break;
		}
	}

	public void trace(@NotNull Throwable e, @NotNull StackTraceElement LastObject, @Nullable String message) {
		// TODO Auto-generated method stub
		Logger logger = getHanLibLogger(LastObject);
		logger.throwing(LastObject.getClassName(), LastObject.getMethodName(), e);
		e.printStackTrace();
	}

	@Override
	public void bundle(@NotNull String Startwith, String BundleAs) {

	}

	@Override
	public void onLoad() {
		Log.SystemDynamicLogObject = new DLog(this);
		Log.out("Loaded Hanro's file handling library. This is a dummy API that doesn't do anything on it's own");

	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {
	}

}
