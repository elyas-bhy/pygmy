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

	public void setDimensions(int rows, int col) throws Exception;

	public int getBoardType();
	
	public void setBoardType(int type) throws Exception;
	
	public List<Paint> getColors();
	
	public void setColors(List<Paint> colors) throws Exception;

	public void addGameRule(GameRule rule);

	public void addEntity(GameEntity entity);

	public void tryMove(GameMove move);

	public void end();

}