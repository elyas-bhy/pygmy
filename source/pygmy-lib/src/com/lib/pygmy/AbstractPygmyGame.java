package com.lib.pygmy;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.lib.pygmy.base.ObservableValue;

/**
 * Create a basic game application
 */
public abstract class AbstractPygmyGame implements PygmyGame, Observer {
	
	protected String title = "Default Game";
	protected static final int SPRITE_SIZE = 16;
	protected int rows = 31;
	protected int columns = 28;
	
	private PygmyGameContext context;
	private PygmyGameLevel currentPlayedLevel = null;

	protected int levelNumber;
	protected List<GameLevel> gameLevels;
	
	// initialized before each level
	protected ObservableValue<Boolean> endOfGame = null;


	public AbstractPygmyGame(Resources resources) {
		context = new PygmyGameContext(this, resources);
	}

	public void createGUI() {
		
	}

	public Canvas getCanvas() {
		return null;
	}

	public void start() {
		levelNumber = 0;
		for (GameLevel level : gameLevels) {
			endOfGame = new ObservableValue<Boolean>(false);
			endOfGame.addObserver(this);
		}
		gameLevels.get(0).start();
	}

	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				//informationValue.setText("You win");
				currentPlayedLevel.end();
			}
		}
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
	public void setPlayers(int minPlayers, int maxPlayers) {
		context.setPlayers(minPlayers, maxPlayers);
		createGUI();
	}
	
	@Override
	public List<Player> getPlayers() {
		return context.getPlayers();
	}

	@Override
	public Player getCurrentPlayer() {
		return context.getCurrentPlayer();
	}
	
	@Override
	public void nextPlayer() {
		context.nextPlayer();
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
	
	public PygmyGame getGame() {
		return this;
	}
	
	@Override
	public PygmyGameContext getContext() {
		return context;
	}

	@Override
	public abstract void initGame();
}
