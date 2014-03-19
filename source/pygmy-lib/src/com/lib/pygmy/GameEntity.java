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
 * Interface of board game pieces (entities)
 * @author Pygmy
 */
public interface GameEntity {
	
	/**
	 * Returns the current game context
	 * @return
	 */
	public PygmyGameContext getContext();
	
	public Tile getCurrentTile();
	
	public void setCurrentTile(Tile tile);
	
	public String getPlayerId();
	
	public void setPlayerId(String playerId);
	
	public void oneStepMove(GameMove move);
	
	public boolean isLegalMove(GameMove move);
	
	public void oneStepMoveAddedBehavior();

	public Bitmap getBitmap();
	
	public void setBitmap(Bitmap bitmap);
	
	public EntityType getType();
}