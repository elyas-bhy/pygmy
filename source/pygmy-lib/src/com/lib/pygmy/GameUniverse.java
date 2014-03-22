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

import java.util.Collection;

import com.lib.pygmy.util.TurnData;

/**
 * Manages the game entities lifecycles, positioning, and overlap processing.
 * @author Pygmy
 * 
 */
public interface GameUniverse {

	/**
	 * Returns the entity occupying the specified tile
	 * @param tile
	 */
	public GameEntity getEntityAt(Tile tile);
	
	/**
	 * Returns a collection of the universe's game entities
	 */
	public Collection<GameEntity> getGameEntities();
	
	/**
	 * Adds an entity to the universe
	 * @param gameEntity
	 */
	public void addGameEntity(GameEntity gameEntity);
	
	/**
	 * Removes an entity from the universe
	 * @param gameEntity
	 */
	public void removeGameEntity(GameEntity gameEntity);

	/**
	 * Handles a player move
	 * @param move
	 */
	public void processMove(GameMove move);
	
	/**
	 * Updates the universe state according to the passed response
	 * @param data
	 */
	public void updateData(TurnData data);
	
	/**
	 * Returns the universe's state in a compressed format
	 */
	public String getState();

}
