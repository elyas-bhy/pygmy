package com.client.pygmy.entity;

import com.lib.pygmy.EntityType;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.PygmyGameEntity;
import com.lib.pygmy.util.Point;

public class MyChessEntity extends PygmyGameEntity {

	public MyChessEntity(GameLevel level, String playerId, EntityType type, Point p) {
		super(level, playerId, type, p);
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		
	}
	
	@Override
	public boolean isLegalMove(GameMove move) {
		System.out.println(this.getCurrentTile().getPosition().toString());
		return true;
	}

}