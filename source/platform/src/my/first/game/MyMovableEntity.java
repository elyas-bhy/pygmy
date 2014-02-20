package my.first.game;

// Create a movable entity for your game.

import java.awt.Canvas;

public class MyMovableEntity extends MovableEntities{

	public MyMovableEntity(Canvas defaultCanvas, String img) {
		super(defaultCanvas, img);
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		spriteManager.increment();
	}
	
	@Override
	public boolean isLegalMove(String move) {
		System.out.println(this.getPosition().toString());
		//capacityOK?
		//checkPath...
		return true;
	}

}
