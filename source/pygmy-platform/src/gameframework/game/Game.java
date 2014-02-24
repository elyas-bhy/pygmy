package gameframework.game;

import gameframework.base.ObservableValue;

import java.awt.Canvas;
import java.util.List;

public interface Game {
	
	public void createGUI();
	
	public List<Player> getPlayers();

	public Canvas getCanvas();

	public void start();

	public ObservableValue<Boolean> endOfGame();
	
	public void setLevels(List<GameLevel> levels);

	public Player getCurrentPlayer();

	public void setCurrentPlayer(Player player);

	void setPlayers(int minPlayers, int maxPlayers);
	
	public void setTitle(String title);
	
	public void setBoardDimensions(int rows, int columns);
}
