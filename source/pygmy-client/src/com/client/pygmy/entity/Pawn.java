package com.client.pygmy.entity;

import android.graphics.Point;

import com.lib.pygmy.GameMove;
import com.lib.pygmy.Player;
import com.lib.pygmy.PygmyGameEntity;
import com.lib.pygmy.PygmyGameLevel;

public class Pawn extends PygmyGameEntity {

	public Pawn(PygmyGameLevel level, Player player, int resId, int x, int y) {
		super(level, player, resId, new Point(x, y));
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