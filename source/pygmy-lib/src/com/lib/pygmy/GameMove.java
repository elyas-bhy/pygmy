package com.lib.pygmy;

import java.awt.Point;

public class GameMove {
	
	private GameMovable entity;
	private Point move;
	
	public GameMove() {
		
	}

	public GameMovable getEntity() {
		return entity;
	}

	public void setEntity(GameMovable entity) {
		this.entity = entity;
	}

	public Point getMove() {
		return move;
	}

	public void setMove(Point move) {
		this.move = move;
	}

}
