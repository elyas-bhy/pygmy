package my.first.game;

// Create a movable entity for your game.

import gameframework.game.GameMove;

import java.awt.Canvas;
import java.awt.Point;

public class MyMovableEntity extends MovableEntity {

	public MyMovableEntity(Canvas defaultCanvas, String img) {
		super(defaultCanvas, img);
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
