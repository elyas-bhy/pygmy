package com.lib.pygmy;

public interface GameLevel {

	public void start();
	
	public void init();

	public String getCurrentPlayerId();

	public GameMap getMap();
	
	public GameUniverse getUniverse();
	
	public PygmyGameContext getContext();

	public void setDimensions(int rows, int col);

	public void addGameRule(GameRule rule);

	public void addEntity(GameEntity entity);

	public void tryMove(GameMove move);

	public void end();
}
 