package my.first.game;

// Create a movable entity for your game.

import gameframework.game.GameMove;
import gameframework.game.MovableEntity;
import gameframework.game.PygmyGameLevel;

public class MyMovableEntity extends MovableEntity {

	public MyMovableEntity(PygmyGameLevel level, String img) {
		super(level, img);
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
