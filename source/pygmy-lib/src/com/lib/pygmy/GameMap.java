package com.lib.pygmy;

import java.io.Serializable;

public class GameMap implements Serializable {
	
	private static final long serialVersionUID = -7726213218272397453L;
	
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
