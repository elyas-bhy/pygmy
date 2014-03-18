package com.client.pygmy.entity;

import android.graphics.Point;

import com.lib.pygmy.EntityType;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.PygmyGameEntity;

public class MyChessEntity extends PygmyGameEntity {

	public MyChessEntity(GameLevel level, String playerId, EntityType type, Point p) throws Exception {
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