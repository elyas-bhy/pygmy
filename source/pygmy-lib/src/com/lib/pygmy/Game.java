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

import java.util.List;

import com.lib.pygmy.base.ObservableValue;

/**
 * Encapsulates the game's state and levels
 */
public interface Game {
	
	/**
	 * Initializes the game's configuration
	 */
	public void initGame();
	
	/**
	 * Starts the game by running the first level
	 */
	public void start();
	
	/**
	 * Callback method called when a player has made a move
	 */
	public void onPlayerMove(GameMove move);
	
	/**
	 * Returns the current game's global context
	 */
	public PygmyGameContext getContext();
	
	/**
	 * Returns the IDs of the participant players
	 */
	public List<String> getPlayerIds();
	
	/**
	 * Sets the IDs of the participant players
	 */
	public void setPlayerIds(List<String> playerIds);
	
	/**
	 * Returns the current player's ID
	 */
	public String getCurrentPlayerId();

	/**
	 * Returns the game's levels
	 */
	public List<GameLevel> getLevels();
	
	/**
	 * Sets the game's levels
	 */
	public void setLevels(List<GameLevel> levels);
	
	/**
	 * Returns the current level
	 */
	public GameLevel getCurrentLevel();
	
	/**
	 * 
	 * @return true if the game has ended, false otherwise
	 */
	public ObservableValue<Boolean> endOfGame();
		
}