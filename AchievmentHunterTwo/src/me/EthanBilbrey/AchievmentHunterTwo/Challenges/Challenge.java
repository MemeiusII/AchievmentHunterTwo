package me.EthanBilbrey.AchievmentHunterTwo.Challenges;

public interface Challenge 
{
	public void startChallenge();
	
	public void sendStartMessage();
	
	public void sendCompletionMessage();
	
	public void startTimer();
	
	public void cancelTask();
	
	public void endChallenge();
}
