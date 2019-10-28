package me.zwoosks;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomTP extends JavaPlugin {
	
	
	public Permission playerPermission = new Permission("ctp");

	@Override
	public void onEnable() {
		
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(playerPermission);
		
		this.getConfig().addDefault("messages.nonpermission-create", "&4You don't have permissions for creating custom TPs!");
		this.getConfig().addDefault("messages.nonpermission-use", "&4You don't have permissions for using custom TPs!");
		this.getConfig().addDefault("messages.customTP-created", "&aYou've succefully created your own CustomTP!");
		this.getConfig().addDefault("messages.player-notfound", "&4The player is not online or doesn't exist!");
		this.getConfig().addDefault("messages.incorrectarguments", "&4Incorrect arguments!");
		this.getConfig().addDefault("messages.nonpermission-otherplayer", "&4The other player doesn't have permissions to create a CTP!");
		this.getConfig().addDefault("messages.other-notTCP", "&4The other player doesn't have any created TCP!");
		this.getConfig().addDefault("messages.help", "&bUse /createcustomtp to create your CustomTP, and /customtp <user> to teleport to a user's CustomTP!");
		
		this.getConfig().options().copyDefaults(true);
		
		saveConfig();
		reloadConfig();
		
	}
	
	
	@Override
	public void onDisable() {
		saveConfig();
	}
	
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		
		
		if(cmd.getName().equalsIgnoreCase("ctphelp") && sender instanceof Player) {
			
			if(player.hasPermission("ctp.use")) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.help")));
			}
			
		}
		
		
		
		
		
		
		if(cmd.getName().equalsIgnoreCase("createcustomtp") && sender instanceof Player) {
			
			
			if(player.hasPermission("ctp.create")) {
				
				String path = "users." + player.getName().toLowerCase();
				
				Location locationToSave = player.getLocation();
				
				this.getConfig().set(path, locationToSave);
				saveConfig();
				reloadConfig();
				
				
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.customTP-created")));
				
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.nonpermission-create")));
			}
			
			return true;
		}
		
		
		else if(cmd.getName().equalsIgnoreCase("customtp") && sender instanceof Player) {

			if(player.hasPermission("ctp.use")) {

				try {
					int lenght = args.length;
					
					if(lenght == 1) {
						boolean playerFound = false;
						
						
						
						for(Player toTCPPlayer : Bukkit.getServer().getOnlinePlayers()) {
							if(toTCPPlayer.getName().equalsIgnoreCase(args[0])) {
								
								if(toTCPPlayer.hasPermission("ctp.create")) {
									
									
									Location loc = (Location)getConfig().get("users." + toTCPPlayer.getName().toLowerCase());
									
									
									player.teleport(loc);

									
									playerFound = true;
									break;
									
									
								} else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.nonpermission-otherplayer")));
								}
								
							}
							
						}
						if(playerFound == false) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.player-notfound")));
						}
						
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.incorrectarguments")));
					}
					
					
					
					
				} catch(Exception e) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.other-notTCP")));
				}
				
				
				
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.nonpermission-use")));
			}
			
			return true;
		}
		
		return false;
		
	}
	
}


// player.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.other-notTCP")));