package my.first.game;

// Create a movable entity for your game.

import gameframework.base.Direction;

import java.awt.Canvas;

public class MyMovableEntity extends MovableEntity {

	public MyMovableEntity(Canvas defaultCanvas, String img) {
		super(defaultCanvas, img);
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		spriteManager.increment();
	}
	
	@Override
	public boolean isLegalMove(Direction move) {
		System.out.println(this.getPosition().toString());
		//capacityOK?
		//checkPath...
		return true;
	}

}
