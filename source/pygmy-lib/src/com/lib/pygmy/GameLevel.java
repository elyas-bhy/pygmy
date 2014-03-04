package com.lib.pygmy;

import android.graphics.Canvas;

public interface GameLevel {

	public void start();
	
	public void init();

	public Player getCurrentPlayer();

	public GameMap getMap();
	
	public Canvas getCanvas();
	
	public PygmyGameContext getContext();

	public void setDimensions(int rows, int col);

	public void addGameRule(GameRule rule);

	public void addEntity(GameEntity entity);

	public void addMovableEntity(MovableEntity entity);

	public void tryMove(GameMove move);

	public void end();
}
 