package com.client.pygmy;

// Create a movable entity for your game.

import com.lib.pygmy.GameMove;
import com.lib.pygmy.MovableEntity;
import com.lib.pygmy.Player;
import com.lib.pygmy.PygmyGameLevel;

import java.awt.Point;

public class MyMovableEntity extends MovableEntity {
	
	private final static int SPRITE_SIZE = 16;

	public MyMovableEntity(PygmyGameLevel level, Player player, int x, int y) {
		super(level, player, "images/pac1.gif", new Point(x * SPRITE_SIZE, y * SPRITE_SIZE));
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		spriteManager.increment();
	}
	
	@Override
	public boolean isLegalMove(GameMove move) {
		System.out.println(this.getPosition().toString());
		//capacityOK?
		//checkPath...
		return true;
	}

}
