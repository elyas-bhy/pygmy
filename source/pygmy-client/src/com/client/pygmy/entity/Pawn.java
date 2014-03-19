package com.client.pygmy.entity;

import com.lib.pygmy.EntityType;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.PygmyGameEntity;
import com.lib.pygmy.Tile;
import com.lib.pygmy.util.Point;

public class Pawn extends PygmyGameEntity {

	public Pawn(GameLevel level, String playerId, EntityType type, Point p) {
		super(level, playerId, type, p);
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		
	}
	
	@Override
	public boolean isLegalMove(GameMove move) {
		Tile src = getCurrentTile();
		Tile dst = move.getDestination();
		return (dst.getPosition().y - src.getPosition().y) == 0;
	}

}