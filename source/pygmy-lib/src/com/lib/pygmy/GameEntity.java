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

import android.graphics.Bitmap;

/**
 * Represents a board game entity
 * @author Pygmy
 * 
 */
public interface GameEntity {
	
	/**
	 * Returns the current game context
	 */
	public PygmyGameContext getContext();
	
	/**
	 * Returns the current tile occupied by this instance
	 */
	public Tile getCurrentTile();
	
	/**
	 * Sets the current tile occupied by this instance
	 * @param tile
	 */
	public void setCurrentTile(Tile tile);
	
	/**
	 * Returns the player ID associated to this instance
	 */
	public String getPlayerId();
	
	/**
	 * Sets the player ID associated to this instance
	 * @param playerId
	 */
	public void setPlayerId(String playerId);

	/**
	 * Returns the inflated bitmap of this instance
	 */
	public Bitmap getBitmap();
	
	/**
	 * Sets the inflated bitmap of this instance
	 * @param bitmap
	 */
	public void setBitmap(Bitmap bitmap);
	
	/**
	 * Returns the entity type of this instance
	 */
	public EntityType getType();
	
	/**
	 * Updates the entity's position by applying the passed move
	 * @param move
	 */
	public void oneStepMove(GameMove move);
	
	/**
	 * Verifies if the passed move is a valid move
	 * @param move
	 * @return true if the move is valid, false otherwise
	 */
	public boolean isLegalMove(GameMove move);
	
	/**
	 * Adds additional behaviour after a move has been made
	 */
	public void oneStepMoveAddedBehavior();
	
}