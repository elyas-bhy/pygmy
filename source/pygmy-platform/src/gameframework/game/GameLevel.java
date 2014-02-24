package gameframework.game;

import java.awt.Canvas;
import java.awt.Point;

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

	public void addMovableEntity(MovableEntity me, Point p);

	public void tryMove(GameMove move);

	public void end();
}
 