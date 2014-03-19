package com.lib.pygmy;

import java.io.Serializable;

public class GameMove implements Serializable {
	
	private static final long serialVersionUID = -2310782677683578117L;
	
	private GameEntity entity;
	private Tile dest;
	
	public GameMove(GameEntity entity, Tile dest) {
		this.entity = entity;
		this.dest = dest;
	}

	public GameEntity getEntity() {
		return entity;
	}

	public void setEntity(GameEntity entity) {
		this.entity = entity;
	}

	public Tile getDestination() {
		return dest;
	}

	public void setDestination(Tile dest) {
		this.dest = dest;
	}

}