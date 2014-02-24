package my.first.game;

import gameframework.game.GameRule;

public class EternalGameRule implements GameRule {

	@Override
	public boolean check() {
		return true;
	}
	
	@Override
	public String getMessage() {
		return "Playing";
	}

}
