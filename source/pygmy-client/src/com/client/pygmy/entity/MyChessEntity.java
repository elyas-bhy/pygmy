package com.client.pygmy.entity;

import android.graphics.Point;

import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.Player;
import com.lib.pygmy.PygmyGameEntity;
import com.lib.pygmy.PygmyGameLevel;

public class MyChessEntity extends PygmyGameEntity {

	public MyChessEntity(GameLevel level, Player player, Integer resId, Point p) {
		super(level, player, resId, p);
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