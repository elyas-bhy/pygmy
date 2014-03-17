package com.lib.pygmy;

import java.util.List;

import com.lib.pygmy.base.ObservableValue;

public interface Game {
	
	public void initGame();
	
	public void start();
	
	public void nextPlayer(String state);
	
	public void onPlayerMove(GameMove move);
	
	public PygmyGameContext getContext();
	
	public List<String> getPlayerIds();
	
	public void setPlayerIds(List<String> playerIds);
	
	public String getCurrentPlayerId();

	public List<GameLevel> getLevels();
	
	public void setLevels(List<GameLevel> levels);
	
	public GameLevel getCurrentLevel();
	
	public ObservableValue<Boolean> endOfGame();
}