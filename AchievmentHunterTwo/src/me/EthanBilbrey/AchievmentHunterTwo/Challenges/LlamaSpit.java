package me.EthanBilbrey.AchievmentHunterTwo.Challenges;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.EthanBilbrey.AchievmentHunterTwo.Main;
import me.EthanBilbrey.AchievmentHunterTwo.MyEvent;

public class LlamaSpit implements Listener, Challenge
{
	
	private int taskId;
	private int ticks;
	private int spitCount;

	@Override
	public void startChallenge() {
		// TODO Auto-generated method stub
		sendStartMessage();
		startTimer();
		spitCount = 0;
	}

	@Override
	public void sendStartMessage() {
		// TODO Auto-generated method stub
		ChallengeManager.player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have 45m to complete your achievment!");
		for(Player p : Bukkit.getOnlinePlayers()) 
		{
			p.sendMessage(ChatColor.YELLOW + "Sticky Situation");
			p.sendMessage(ChatColor.YELLOW + "-" + ChatColor.WHITE + " Get spit on by a llama 6 times!");
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
			if(ticks == 54000) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					ChallengeManager.llamaStarted = false;
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Times up! " + ChallengeManager.player.getDisplayName() + " has not completed their Achievment");
				}
				ChallengeManager.llamaStarted = false;
				Bukkit.getServer().getPluginManager().callEvent(new MyEvent());
				cancelTask();
			}
			else if(ticks == 42000) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "10 minutes left!");
				}
			}
			else if(ticks == 48000) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "5 minutes left!");
				}
			}
			else if(ticks == 51600) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "2 minutes left!");
				}
			}
			else if(ticks >= 53400) 
			{
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
					p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + ((54000 - ticks)/20) + " seconds left!");
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
	public void onPlayerDamage(EntityDamageByEntityEvent e) 
	{
		if(ChallengeManager.llamaStarted) 
		{
			if(e.getEntity() instanceof Player && ((Player) e.getEntity()).equals(ChallengeManager.player)) 
			{
				if(e.getDamager() instanceof Projectile) 
				{
					Projectile pro = (Projectile) e.getDamager();
					if(pro.getShooter() instanceof Llama) 
					{
						spitCount++;
						ChallengeManager.player.sendMessage(ChatColor.GREEN + "(" + spitCount + "/6)");
						if(spitCount == 6) 
						{
							ChallengeManager.llamaStarted = false;
							endChallenge();
						}
					}
				}
			}
		}
	}

}
