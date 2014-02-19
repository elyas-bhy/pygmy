package my.first.game;

// Create a movable entity for your game.

import java.awt.Canvas;
import java.awt.Rectangle;


public class MyMovableEntity extends MovableEntities{


	public MyMovableEntity(Canvas defaultCanvas, String img) {
		super(defaultCanvas, img);
	}


	@Override
	public void oneStepMoveAddedBehavior() {
		spriteManager.increment();
	}
	

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
	


	@Override
	public boolean isLegalMove(String move) {
		System.out.println(this.getPosition().toString());
		//capacityOK?
		//checkPath...
		return true;
	}


}
