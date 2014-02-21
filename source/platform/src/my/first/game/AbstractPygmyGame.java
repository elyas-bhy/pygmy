package my.first.game;

import gameframework.game.Game;

public abstract class AbstractPygmyGame implements PygmyGame {
	
	private Game game;
	
	public AbstractPygmyGame() {
		game = new PygmyGameImpl();
	}

	public Game getGame() {
		return game;
	}
	
	public abstract void initGame();
}
