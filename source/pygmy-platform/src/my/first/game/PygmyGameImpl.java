package my.first.game;

import java.awt.Canvas;
import java.util.List;

import gameframework.base.ObservableValue;
import gameframework.game.Game;
import gameframework.game.GameLevel;
import gameframework.game.Player;

public class PygmyGameImpl implements Game {

	@Override
	public void createGUI() {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Canvas getCanvas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public ObservableValue<Boolean> endOfGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLevels(List<GameLevel> levels) {
		// TODO Auto-generated method stub
	}

	@Override
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentPlayer(Player player) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPlayers(int minPlayers, int maxPlayers) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setBoardDimensions(int columns, int rows) {
		// TODO Auto-generated method stub
	}
}
