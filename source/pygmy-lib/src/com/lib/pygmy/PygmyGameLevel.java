/*
 * Copyright (C) 2014 Pygmy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lib.pygmy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lib.pygmy.base.ObservableValue;

/**
 * To be implemented with respect to a specific game. Expected to initialise the
 * universe and the gameBoard
 */

public abstract class PygmyGameLevel implements GameLevel, Serializable {
	
	private static final long serialVersionUID = -5162807833660148717L;
	
	protected final Game game;
	protected GameUniverse universe;
	protected ObservableValue<Boolean> endOfGame;

	private int rows;
	private int columns;
	private int boardType;
	private List<Integer> colors;
	private List<GameRule> gameRules;

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
	public GameUniverse getUniverse() {
		return universe;
	}
	
	@Override
	public PygmyGameContext getContext() {
		return game.getContext();
	}

	@Override
	public void setDimensions(int rows, int cols) {
		if (rows <= 0 || cols <= 0) {
			throw new IllegalStateException("Rows and Columns must be positive.");
		}
		if (rows > 16 || cols > 16) {
			throw new IllegalStateException("Dimension board could not be set.");
		}
		
		this.rows = rows;
		this.columns = cols;
	}
	
	@Override
	public void addGameRule(GameRule rule) {
		gameRules.add(rule);
	}

	@Override
	public void addEntity(GameEntity entity) {
		universe.addGameEntity(entity);
	}
	
	@Override
	public void tryMove(GameMove move) {
		if (move.getEntity().isLegalMove(move)) {
			for (GameRule rule : gameRules) {
				if (!rule.check()) {
					System.out.println(rule.getMessage());
					end();
				}
			}
			universe.processMove(move);
		} else {
			throw new IllegalMoveException();
		}
	}

	@Override
	public void end() {
		
	}
	
	@Override
	public int getNumberRows() {
		return rows;
	}
	
	@Override
	public int getNumberColumns() {
		return columns;
	}
	
	@Override
	public int getBoardType() {
		return boardType;
	}
	
	@Override
	public void setBoardType(int type) {
		if (type < 0) {
			throw new IllegalStateException("Type must be positive.");
		}
		// Change this if the number of available boards changes.
		if (type > 2) {
			throw new IllegalStateException("Board's type does not exist.");
		}
		
		this.boardType = type;
	}
	
	@Override
	public List<Integer> getColors() {
		return colors;
	}

	@Override
	public void setColors(List<Integer> colors) {
		if (colors == null) {
			throw new IllegalStateException("Colors list is null.");
		}

		this.colors = colors;
	}
	
}