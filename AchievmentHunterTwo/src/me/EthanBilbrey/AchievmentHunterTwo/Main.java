package me.EthanBilbrey.AchievmentHunterTwo;

import java.util.ConcurrentModificationException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.EthanBilbrey.AchievmentHunterTwo.Challenges.BrokenTool;
import me.EthanBilbrey.AchievmentHunterTwo.Challenges.Challenge;
import me.EthanBilbrey.AchievmentHunterTwo.Challenges.ChallengeManager;
import me.EthanBilbrey.AchievmentHunterTwo.Challenges.ExplosiveSurvivor;
import me.EthanBilbrey.AchievmentHunterTwo.Challenges.FireryRevenge;
import me.EthanBilbrey.AchievmentHunterTwo.Challenges.LlamaSpit;
import me.EthanBilbrey.AchievmentHunterTwo.Challenges.Suicide;
import me.EthanBilbrey.AchievmentHunterTwo.Challenges.Tag;
import me.EthanBilbrey.AchievmentHunterTwo.Challenges.Violence;

public class Main extends JavaPlugin
{
	public boolean isStarted;
	public ChallengeManager cm = new ChallengeManager();
	
	@Override
	public void onEnable() 
	{
		isStarted = false;
		this.getCommand("ahstart").setExecutor(this);
		this.getCommand("ahstop").setExecutor(this);
		getServer().getPluginManager().registerEvents(new ChallengeManager(), this);
		getServer().getPluginManager().registerEvents(new Tag(), this);
		getServer().getPluginManager().registerEvents(new FireryRevenge(), this);
		getServer().getPluginManager().registerEvents(new ExplosiveSurvivor(), this);
		getServer().getPluginManager().registerEvents(new BrokenTool(), this);
		getServer().getPluginManager().registerEvents(new Violence(), this);
		getServer().getPluginManager().registerEvents(new Suicide(), this);
		getServer().getPluginManager().registerEvents(new LlamaSpit(), this);
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(command.getName().equals("ahstart") && sender instanceof Player && !isStarted) 
		{
			cm.setPlayer((Player) sender);
			cm.startChallenges();
			isStarted = true;
		}
		else if(command.getName().equals("ahstop") && sender instanceof Player && isStarted) 
		{
			ChallengeManager.list.get(ChallengeManager.count).cancelTask();
			isStarted = false;
			ChallengeManager.tagStarted = false;
			ChallengeManager.lavaStarted = false;
			ChallengeManager.tntStarted =false;
			ChallengeManager.toolStarted = false;
			ChallengeManager.punchStarted = false;
			ChallengeManager.deathStarted = false;
			ChallengeManager.llamaStarted = false;
			ChallengeManager.count = 0;
			try 
			{
				ChallengeManager.list.clear();
			}
			catch(ConcurrentModificationException e) {}
			
			for(Player p : Bukkit.getOnlinePlayers()) 
			{
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Challenge Stopped! ");
			}
		}
		
        return true;
    }
}
