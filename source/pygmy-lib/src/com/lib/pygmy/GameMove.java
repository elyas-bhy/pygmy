package com.lib.pygmy;

import android.graphics.Point;

public class GameMove {
	
	private GameEntity entity;
	private Point move;
	
	public GameMove() {
		
	}

	public GameEntity getEntity() {
		return entity;
	}

	public void setEntity(GameEntity entity) {
		this.entity = entity;
	}

	public Point getMove() {
		return move;
	}

	public void setMove(Point move) {
		this.move = move;
	}

}
