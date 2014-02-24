package gameframework.game;

import gameframework.base.Direction;
import my.first.game.MovableEntity;

public interface GameLevel {

	public void start();
	
	public void init();

	public Player getCurrentPlayer();

	public GameMap getMap();

	public void setDimensions(int rows, int col);

	public void addGameRule(GameRule rule);

	public void addEntity(GameEntity entity);

	public void addMovableEntity(MovableEntity me, int x, int y);

	public void tryMove(GameMovable entity, Direction move);

	public void end();
}
 