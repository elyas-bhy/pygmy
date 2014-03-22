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
import java.util.Observable;
import java.util.Observer;

import com.lib.pygmy.base.ObservableValue;

/**
 * Encapsulates the game's state and levels
 */
public abstract class PygmyGame implements Game, Observer, Serializable {
	
	private static final long serialVersionUID = -6084846314735557726L;

	protected ObservableValue<Boolean> endOfGame;
	
	private PygmyGameContext context;
	private GameLevel currentPlayedLevel;
	private List<GameLevel> gameLevels;
	
	public PygmyGame() {
		context = new PygmyGameContext(this);
		gameLevels = new ArrayList<GameLevel>();
	}

	/**
	 * Initializes the game's configuration
	 */
	@Override
	public abstract void initGame();

	/**
	 * Starts the game by running the first level
	 */
	@Override
	public void start() {
		if (gameLevels == null) {
			throw new IllegalStateException("Game levels is null");
		}
		
		GameLevel level = gameLevels.get(0);
		if (level == null) {
			throw new IllegalStateException("No Level. Did you request it at setup?");
		}

		endOfGame = new ObservableValue<Boolean>(false);
		endOfGame.addObserver(this);
		
		context.setCurrentLevel(level);
		level.start();
	}
	
	/**
	 * Callback method called when a player has made a move
	 */
	@Override
	public void onPlayerMove(GameMove move) {
		context.onPlayerMove(move);
	}

	/**
	 * Returns the current game's context
	 */
	@Override
	public PygmyGameContext getContext() {
		return context;
	}
	
	/**
	 * Returns the IDs of the participant players
	 */
	@Override
	public List<String> getPlayerIds() {
		return context.getPlayerIds();
	}

	/**
	 * Sets the IDs of the participant players
	 */
	@Override
	public void setPlayerIds(List<String> playerIds) {
		if (playerIds == null || playerIds.isEmpty()) {
			throw new IllegalStateException("Players ID list is empty or null. Did you request it at setup?");
		}
		
		context.setPlayers(playerIds);
	}

	/**
	 * Returns the current player's ID
	 */
	@Override
	public String getCurrentPlayerId() {
		return context.getCurrentPlayerId();
	}
	
	/**
	 * Returns the game's levels
	 */
	@Override
	public List<GameLevel> getLevels() {
		return gameLevels;
	}

	/**
	 * Sets the game's levels
	 */
	@Override
	public void setLevels(List<GameLevel> levels) {
		if (levels == null || levels.isEmpty()) {
			throw new IllegalStateException("No Levels. Did you request it at setup?");
		}
		
		gameLevels = levels;
	}
	
	/**
	 * Returns the current level
	 */
	@Override
	public GameLevel getCurrentLevel() {
		return context.getCurrentLevel();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				//informationValue.setText("You win");
				currentPlayedLevel.end();
			}
		}
	}

	@Override
	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}
	
}