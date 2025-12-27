package me.EthanBilbrey.AchievmentHunterTwo.Challenges;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.EthanBilbrey.AchievmentHunterTwo.Main;
import me.EthanBilbrey.AchievmentHunterTwo.MyEvent;

public class Suicide implements Listener, Challenge
{
	
	private int taskId;
	private int ticks;

	@Override
	public void startChallenge() {
		// TODO Auto-generated method stub
		sendStartMessage();
		startTimer();
		
		for(Player p : Bukkit.getOnlinePlayers()) 
		{
			if(!p.equals(ChallengeManager.player)) 
			{
				p.performCommand("gamemode creative");
			}
		}
	}

	@Override
	public void sendStartMessage() {
		// TODO Auto-generated method stub
		ChallengeManager.player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have 5m to complete your achievment!");
		for(Player p : Bukkit.getOnlinePlayers()) 
		{
			p.sendMessage(ChatColor.YELLOW + "Baby sitters");
			p.sendMessage(ChatColor.YELLOW + "-" + ChatColor.WHITE + " Try to die before the Hunters can save you! Hunters get Creative!");
		}
	}

	@Override
	public void sendCompletionMessage() {
		// TODO Auto-generated method stub
		ChallengeManager.player.playSound(ChallengeManager.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0F, 1.0F);
		for(Player p : Bukkit.getOnlinePlayers()) 
		{
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + ChallengeManager.player.getDisplayName() + " completed their achievment!");
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " There are no more achievments remaining. " + ChallengeManager.player.getDisplayName() + " has won!" + "Hunters Lose!");
			if(p.equals(ChallengeManager.player)) 
			{
				p.sendTitle(ChatColor.GREEN + "You Win!", "",10, 160, 20);
			}
			else 
			{
				p.sendTitle(ChatColor.RED + "You Lose!", "", 10, 160, 20);
			}
		}
	}

	@Override
	public void startTimer() {
		// TODO Auto-generated method stub
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), () -> {
			ticks += 20;
			if(ticks == 6000) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Times up! " + ChallengeManager.player.getDisplayName() + " has not completed their Achievment");
				}
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					if(!p.equals(ChallengeManager.player)) 
					{
						p.performCommand("gamemode survival");
					}
				}
				ChallengeManager.deathStarted = false;
				Bukkit.getServer().getPluginManager().callEvent(new MyEvent());
				cancelTask();
			}
			else if(ticks == 3600) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "2 minutes left!");
				}
			}
			else if(ticks == 3800) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "1 minutes left!");
				}
			}
			else if(ticks >= 5800) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + ((6000 - ticks)/20) + " seconds left!");
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
		for(Player p : Bukkit.getOnlinePlayers()) 
		{
			if(!p.equals(ChallengeManager.player)) 
			{
				p.performCommand("gamemode survival");
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) 
	{
		if(ChallengeManager.deathStarted) 
		{
			if(e.getEntity().equals(ChallengeManager.player)) 
			{
				endChallenge();
				ChallengeManager.deathStarted = false;
			}
		}
	}

}
