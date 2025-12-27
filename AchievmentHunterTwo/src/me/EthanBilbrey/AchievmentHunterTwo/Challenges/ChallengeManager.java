package me.EthanBilbrey.AchievmentHunterTwo.Challenges;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.EthanBilbrey.AchievmentHunterTwo.MyEvent;

public class ChallengeManager implements Listener
{
	public static Player player;
	public static boolean isActive;
	public static boolean FlowerActive;
	public static List<Challenge> list = new ArrayList<Challenge>();
	public static int count;
	public static boolean tagStarted;
	public static boolean lavaStarted;
	public static boolean tntStarted;
	public static boolean toolStarted;
	public static boolean punchStarted;
	public static boolean deathStarted;
	public static boolean llamaStarted;
	Horder hd = new Horder();
	Tag tg = new Tag();
	IronArmor ia = new IronArmor();
	FireryRevenge fr = new FireryRevenge();
	Modernization md = new Modernization();
	ExplosiveSurvivor es = new ExplosiveSurvivor();
	BrokenTool bt = new BrokenTool();
	Violence vi = new Violence();
	Suicide su = new Suicide();
	LlamaSpit ls = new LlamaSpit();

	
	public ChallengeManager() 
	{
		ChallengeManager.tagStarted = false;
		ChallengeManager.lavaStarted = false;
		ChallengeManager.tntStarted =false;
		ChallengeManager.toolStarted = false;
		ChallengeManager.punchStarted = false;
		ChallengeManager.deathStarted = false;
		ChallengeManager.llamaStarted = false;
	}
	
	public void setPlayer(Player player) 
	{
		ChallengeManager.player = player;
	}

	public void startChallenges() 
	{
		count = 0;
		
		list.add(hd);
		list.add(tg);
		list.add(ia);
		list.add(fr);
		list.add(md);
		list.add(es);
		list.add(bt);
		list.add(vi);
		list.add(ls);
		list.add(su);
		
		Bukkit.getServer().getPluginManager().callEvent(new MyEvent());
	}
	
	@EventHandler
	public void onMyEvent(MyEvent e)
	{
		if(count == 1) 
		{
			ChallengeManager.tagStarted = true;
		}
		if(count == 3) 
		{
			ChallengeManager.lavaStarted = true;
		}
		if(count == 5) 
		{
			ChallengeManager.tntStarted = true;
		}
		if(count == 6) 
		{
			ChallengeManager.toolStarted = true;
		}
		if(count == 7) 
		{
			ChallengeManager.punchStarted = true;
		}
		if(count == 8) 
		{
			ChallengeManager.llamaStarted = true;
		}
		if(count == 9) 
		{
			ChallengeManager.deathStarted = true;
		}
		if(count < list.size()) 
		{
			Challenge c = list.get(count);
			c.startChallenge();
			count++;
		}
	}
}
