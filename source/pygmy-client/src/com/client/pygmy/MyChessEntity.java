package com.client.pygmy;

// Create a movable entity for your game.

import android.content.Context;
import android.graphics.Point;

import com.lib.pygmy.GameMove;
import com.lib.pygmy.MovableEntity;
import com.lib.pygmy.Player;
import com.lib.pygmy.PygmyGameLevel;

public class MyChessEntity extends MovableEntity {

	public MyChessEntity(Context context, PygmyGameLevel level, Player player, 
			int resId, int x, int y) {
		super(context, level, player, resId, new Point(x, y));
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		
	}
	
	@Override
	public boolean isLegalMove(GameMove move) {
		System.out.println(this.getPosition().toString());
		//capacityOK?
		//checkPath...
		return true;
	}

}
