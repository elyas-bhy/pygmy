package gameframework.game;

public interface GameLevel {
	
	public Player getCurrentPlayer();
	
	public void addGameRule(GameRule rule);
	
	public void start();
	
	public void tryMove(GameMovable entity, String move);
	
	public int[][] getMap();
}
