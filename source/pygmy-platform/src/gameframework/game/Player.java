package gameframework.game;

import gameframework.base.Direction;

public class Player {
	
	private String id;

	public String getId() {
		return id;
	}
	
	public boolean play(GameMovable entity, Direction move) {
		return true;
	}
}
