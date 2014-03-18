package com.lib.pygmy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;

import com.lib.pygmy.base.ObservableValue;

/**
 * To be implemented with respect to a specific game. Expected to initialise the
 * universe and the gameBoard
 */

public abstract class PygmyGameLevel implements GameLevel, Serializable {
	
	private static final long serialVersionUID = -5162807833660148717L;
	
	protected GameMap gameMap;
	protected GameUniverse universe;
	protected ObservableValue<Boolean> endOfGame;

	private int rows;
	private int columns;
	private int boardType;
	private List<Paint> colors;
	protected final Game game;
	protected List<GameRule> gameRules;

	public PygmyGameLevel(Game game, OverlapRulesApplier overlapRules) {
		this.game = game;
		this.gameRules = new ArrayList<GameRule>();
		
		OverlapProcessor overlapProcessor = new OverlapProcessorImpl();
		universe = new PygmyGameUniverse(this, overlapProcessor);
		overlapRules.setUniverse(universe);

		overlapProcessor.setOverlapRules(overlapRules);
		overlapProcessor.setUniverse(universe);
	}

	@Override
	public void start() {
		endOfGame = game.endOfGame();
		init();
	}
	
	public abstract void init();
	
	@Override
	public String getCurrentPlayerId() {
		return game.getCurrentPlayerId();
	}

	@Override
	public GameMap getMap() {
		return gameMap;
	}
	
	@Override
	public GameUniverse getUniverse() {
		return universe;
	}
	
	@Override
	public PygmyGameContext getContext() {
		return game.getContext();
	}

	@Override
	public void setDimensions(int rows, int cols) throws Exception {
		if (rows <= 0 || cols <= 0) {
			throw new Exception("Rows and Columns must be positive.");
		}
		if (rows > 16 || cols > 16) {
			throw new Exception("Dimension board could not be set.");
		}
		
		gameMap = new GameMap(rows, cols);
		for (int i = 0; i < cols; ++i) {
			for (int j = 0; j < rows; ++j) {
				gameMap.setValue(i, j, 5);
			}
		}
	}
	
	@Override
	public void addGameRule(GameRule rule) {
		gameRules.add(rule);
	}

	public void addEntity(GameEntity entity) {
		universe.addGameEntity(entity);
	}
	
	public void tryMove(GameMove move) {
		if (move.getEntity().isLegalMove(move)) {
			for (GameRule rule : gameRules) {
				if (!rule.check()) {
					System.out.println(rule.getMessage());
					end();
				}
			}
			makeMove(move);
		} else {
			// throw new IllegalMoveException();
		}
	}

	private void makeMove(GameMove move) {
		universe.processMove(move);
		game.nextPlayer(universe.getState());
	}

	public void end() {
		
	}
	
	public int getNumberRows() {
		return rows;
	}
	
	public int getNumberColumns() {
		return columns;
	}
	
	public int getBoardType() {
		return boardType;
	}
	
	public void setBoardType(int type) throws Exception {
		if (type < 0) {
			throw new Exception("Type must be positive.");
		}
		// Change this if the number of available boards changes.
		if (type > 2) {
			throw new Exception("Board's type does not exist.");
		}
		
		this.boardType = type;
	}
	
	public List<Paint> getColors() {
		return colors;
	}
	
	public void setColors(List<Paint> colors) throws Exception {
		if (colors.isEmpty()) {
			throw new Exception("Colors list is empty.");
		}

		this.colors = colors;
	}
}
