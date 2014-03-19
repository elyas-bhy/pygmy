package com.lib.pygmy;

import java.util.List;

import android.graphics.Paint;

public interface GameLevel {

	public void start();
	
	public void init();

	public String getCurrentPlayerId();

	public GameMap getMap();
	
	public GameUniverse getUniverse();
	
	public PygmyGameContext getContext();

	public int getNumberRows();
	
	public int getNumberColumns();

	public void setDimensions(int rows, int col);

	public int getBoardType();
	
	public void setBoardType(int type);
	
	public List<Integer> getColors();
	
	public void setColors(List<Integer> colors);

	public void addGameRule(GameRule rule);

	public void addEntity(GameEntity entity);

	public void tryMove(GameMove move);

	public void end();
	
}