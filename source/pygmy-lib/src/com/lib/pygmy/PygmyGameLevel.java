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
import com.lib.pygmy.util.Color;

/**
 * Basic implementation of a {@link GameLevel}
 * @author Pygmy
 *
 */
public abstract class PygmyGameLevel implements GameLevel, Serializable {
	
	private static final long serialVersionUID = -5162807833660148717L;
	
	protected final Game game;
	protected GameUniverse universe;
	protected ObservableValue<Boolean> endOfGame;

	private int rows;
	private int columns;
	private int boardType;
	private List<Color> colors;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		endOfGame = game.endOfGame();
		init();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public abstract void init();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCurrentPlayerId() {
		return game.getCurrentPlayerId();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GameUniverse getUniverse() {
		return universe;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PygmyGameContext getContext() {
		return game.getContext();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberRows() {
		return rows;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberColumns() {
		return columns;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBoardType() {
		return boardType;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Color> getColors() {
		return colors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setColors(List<Color> colors) {
		if (colors == null || colors.isEmpty()) {
			throw new IllegalStateException("Colors list is empty or null. Did you forget to setup it?");
		}

		this.colors = colors;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addGameRule(GameRule rule) {
		gameRules.add(rule);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntity(GameEntity entity) {
		universe.addGameEntity(entity);
	}
	
	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void end() {
		
	}
	
}