package gameframework.game;

public class PygmyGameContext {

	private PygmyGame game;
	private PygmyGameLevel currentLevel;
	private Player currentPlayer;
	
	public PygmyGameContext(PygmyGame game) {
		this.game = game;
	}
	
	public PygmyGame getGame() {
		return game;
	}
	
	public PygmyGameLevel getCurrentLevel() {
		return currentLevel;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
}
