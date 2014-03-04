package com.lib.pygmy;

import java.util.List;

import android.graphics.Canvas;

import com.lib.pygmy.base.ObservableValue;

public interface PygmyGame {

	public PygmyGame getGame();
	
	public PygmyGameContext getContext();
	
	public void initGame();
	
	public void createGUI();
	
	public List<Player> getPlayers();

	public Canvas getCanvas();

	public void start();

	public ObservableValue<Boolean> endOfGame();
	
	public void setLevels(List<GameLevel> levels);

	public Player getCurrentPlayer();

	void setPlayers(int minPlayers, int maxPlayers);
	
	public void setTitle(String title);
	
	public void setBoardDimensions(int rows, int columns);

	public void nextPlayer();
}
