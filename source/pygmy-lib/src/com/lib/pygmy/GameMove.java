package com.lib.pygmy;

import java.io.Serializable;

public class GameMove implements Serializable {
	
	private static final long serialVersionUID = -2310782677683578117L;
	
	private GameEntity entity;
	private Tile move;
	
	public GameMove(GameEntity entity, Tile move) {
		this.entity = entity;
		this.move = move;
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