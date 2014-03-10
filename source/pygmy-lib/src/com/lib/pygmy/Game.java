package com.lib.pygmy;

import java.util.List;

import com.lib.pygmy.base.ObservableValue;

public interface Game {
	
	public void initGame();
	
	public void start();
	
	public void nextPlayer(String state);
	
	public void onPlayerMove(GameMove move);
	
	public PygmyGameContext getContext();
	
	public List<Player> getPlayers();
	
	void setPlayers(int minPlayers, int maxPlayers);
	
	public Player getCurrentPlayer();

	public List<GameLevel> getLevels();
	
	public void setLevels(List<GameLevel> levels);
	
	public GameLevel getCurrentLevel();
	
	public void setTitle(String title);
	
	public void setBoardDimensions(int rows, int columns);
	
	public ObservableValue<Boolean> endOfGame();
	
}