package gameframework.game;

public class GameMap {

	private int[][] map;
	
	public GameMap(int rows, int cols) {
		this.map = new int[rows][cols];
	}
	
	public int getValue(int x, int y) {
		return map[x][y];
	}
	
	public void setValue(int x, int y, int value) {
		map[x][y] = value;
	}
	
}
