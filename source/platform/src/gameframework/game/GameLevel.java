package gameframework.game;

public interface GameLevel {
	
	public void start();
	
	public void addGameRule(GameRule rule);
	
	public int[][] getMap();
}
