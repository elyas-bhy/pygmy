package gameframework.game;


public class GameMap {

	private int[][] map;
	
	public GameMap(int[][] map) {
		this.map = map;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}
	
	public void setValue(int x, int y, int value) {
		map[x][y] = value;
	}
}
