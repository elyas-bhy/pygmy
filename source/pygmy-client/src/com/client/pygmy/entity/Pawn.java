package com.client.pygmy.entity;

import android.graphics.Point;

import com.lib.pygmy.EntityType;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.Player;
import com.lib.pygmy.PygmyGameEntity;

public class Pawn extends PygmyGameEntity {

	public Pawn(GameLevel level, Player player, EntityType type, Point p) {
		super(level, player, type, p);
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