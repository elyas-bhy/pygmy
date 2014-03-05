package com.lib.pygmy;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.res.Resources;

import com.lib.pygmy.base.ObservableValue;

/**
 * Create a basic game application
 */
public abstract class PygmyGame implements Game, Observer {
	
	private String title = "Pygmy Game";
	private int rows = 8;
	private int columns = 8;
	protected ObservableValue<Boolean> endOfGame = null;
	
	private PygmyGameContext context;
	private GameLevel currentPlayedLevel = null;
	private List<GameLevel> gameLevels;
	
	public PygmyGame(Resources resources) {
		context = new PygmyGameContext(this, resources);
	}

	@Override
	public abstract void initGame();

	public void start() {
		endOfGame = new ObservableValue<Boolean>(false);
		endOfGame.addObserver(this);
		
		GameLevel level = gameLevels.get(0);
		context.setCurrentLevel(level);
		level.start();
	}

	@Override
	public void nextPlayer() {
		context.nextPlayer();
	}
	
	@Override
	public void onPlayerMove(GameMove move) {
		context.onPlayerMove(move);
	}

	@Override
	public PygmyGameContext getContext() {
		return context;
	}
	
	@Override
	public List<Player> getPlayers() {
		return context.getPlayers();
	}

	@Override
	public void setPlayers(int minPlayers, int maxPlayers) {
		context.setPlayers(minPlayers, maxPlayers);
	}

	@Override
	public Player getCurrentPlayer() {
		return context.getCurrentPlayer();
	}
	
	@Override
	public List<GameLevel> getLevels() {
		return gameLevels;
	}

	@Override
	public void setLevels(List<GameLevel> levels) {
		gameLevels = levels;
	}
	
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setBoardDimensions(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}

	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				//informationValue.setText("You win");
				currentPlayedLevel.end();
			}
		}
	}

	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

	public PygmyGame getGame() {
		return this;
	}
	
}