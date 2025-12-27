package me.EthanBilbrey.AchievmentHunterTwo.Challenges;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.EthanBilbrey.AchievmentHunterTwo.Main;
import me.EthanBilbrey.AchievmentHunterTwo.MyEvent;

public class Tag implements Challenge, Listener
{

	private int taskId;
	private int ticks;
	
	@Override
	public void startChallenge() {
		// TODO Auto-generated method stub
		sendStartMessage();
		startTimer();
	}

	@Override
	public void sendStartMessage() {
		// TODO Auto-generated method stub
		ChallengeManager.player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have 3m to complete your achievment!");
		for(Player p : Bukkit.getOnlinePlayers()) 
		{
			p.sendMessage(ChatColor.YELLOW + "Ultimate Tag");
			p.sendMessage(ChatColor.YELLOW + "-" + ChatColor.WHITE + " Avoid being taged by the hunters for 3m!");
		}
	}

	@Override
	public void sendCompletionMessage() {
		// TODO Auto-generated method stub
		ChallengeManager.player.playSound(ChallengeManager.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0F, 1.0F);
		for(Player p : Bukkit.getOnlinePlayers()) 
		{
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + ChallengeManager.player.getDisplayName() + " completed their achievment!");
		}
	}

	@Override
	public void startTimer() {
		// TODO Auto-generated method stub
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), () -> {
			ticks += 20;
			if(ticks == 3600) 
			{
				endChallenge();
				ChallengeManager.tagStarted = false;
			}
			else if(ticks == 2400) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "1 minute left!");
				}
			}
			else if(ticks >= 3400) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + ((3600 - ticks)/20) + " seconds left!");
				}
			}
		}, 0L, 20L);
	}

	@Override
	public void cancelTask() {
		// TODO Auto-generated method stub
		Bukkit.getScheduler().cancelTask(taskId);
	}

	@Override
	public void endChallenge() {
		// TODO Auto-generated method stub
		sendCompletionMessage();
		cancelTask();
		Bukkit.getServer().getPluginManager().callEvent(new MyEvent());
	}
	
	@EventHandler
	public void playerTagged(EntityDamageByEntityEvent e) 
	{
		if(ChallengeManager.tagStarted) 
		{
			if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) 
			{
				if(((Player) e.getEntity()).equals(ChallengeManager.player)) 
				{
					for(Player p : Bukkit.getOnlinePlayers()) 
					{
						p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player Tagged! " + ChallengeManager.player.getDisplayName() + " has failed their Achievment");
					}
					cancelTask();
					ChallengeManager.tagStarted = false;
				}
			}
		}
	}

}
