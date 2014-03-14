package com.lib.pygmy;

import java.util.List;
import java.util.Map;

import com.lib.pygmy.base.ObservableValue;

public interface Game {
	
	public void initGame();
	
	public void start();
	
	public void nextPlayer(String state);
	
	public void onPlayerMove(GameMove move);
	
	public PygmyGameContext getContext();
	
	public List<Player> getPlayers();
	
	public void setPlayers(List<Player> players);
	
	public Player getCurrentPlayer();

	public List<GameLevel> getLevels();
	
	public void setLevels(List<GameLevel> levels);
	
	public GameLevel getCurrentLevel();
	
	public ObservableValue<Boolean> endOfGame();
	
	public Map<String,Object> getParameters();
	
}