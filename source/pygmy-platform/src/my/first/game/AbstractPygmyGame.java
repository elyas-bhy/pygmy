package my.first.game;

import gameframework.game.Game;
import gameframework.game.GameDefaultImpl;

public abstract class AbstractPygmyGame implements PygmyGame {
	
	private Game game;
	
	public AbstractPygmyGame() {
		//game = new PygmyGameImpl();
		game = new GameDefaultImpl();
	}

	public Game getGame() {
		return game;
	}
	
	public abstract void initGame();
}
