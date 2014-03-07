package com.lib.pygmy;

import com.lib.pygmy.view.Tile;

public class GameMove {
	
	private GameEntity entity;
	private Tile move;
	
	public GameMove() {
		
	}

	public GameEntity getEntity() {
		return entity;
	}

	public void setEntity(GameEntity entity) {
		this.entity = entity;
	}

	public Tile getMove() {
		return move;
	}

	public void setMove(Tile move) {
		this.move = move;
	}

}
