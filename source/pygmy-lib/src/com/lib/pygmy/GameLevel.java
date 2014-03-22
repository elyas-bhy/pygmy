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

import com.lib.pygmy.util.Color;

/**
 * Encapsulates the state of a game level, namely its configuration and
 * its universe.
 * @author Pygmy
 *
 */
public interface GameLevel {

	/**
	 * Starts the level
	 */
	public void start();
	
	/**
	 * Initializes the level's configuration
	 */
	public void init();

	/**
	 * Returns the current player's ID
	 */
	public String getCurrentPlayerId();
	
	/**
	 * Returns the universe of this instance
	 */
	public GameUniverse getUniverse();
	
	/**
	 * Returns the current game's global context
	 */
	public PygmyGameContext getContext();

	/**
	 * Returns the level's number of rows
	 */
	public int getNumberRows();
	
	/**
	 * Returns the level's number of columns
	 */	
	public int getNumberColumns();

	/**
	 * Sets the level's dimensions
	 * @param rows
	 * @param col
	 */
	public void setDimensions(int rows, int col);

	/**
	 * Returns the level's board type
	 */
	public int getBoardType();
	
	/**
	 * Sets the level's board type
	 * @param type
	 */
	public void setBoardType(int type);
	
	/**
	 * Returns the tile colors of the level's board
	 */
	public List<Color> getColors();
	
	/**
	 * Sets the tile colors of the level's board
	 * @param colors
	 */
	public void setColors(List<Color> colors);

	/**
	 * Adds a game rule
	 * @param rule
	 */
	public void addGameRule(GameRule rule);

	/**
	 * Adds a game entity to the level's universe
	 * @param entity
	 */
	public void addEntity(GameEntity entity);

	/**
	 * Processes a move and verifies it against the game's rules
	 * @param move
	 * @throws IllegalMoveException if the move is not a valid move
	 */
	public void tryMove(GameMove move);

	/**
	 * Ends this level
	 */
	public void end();
	
}