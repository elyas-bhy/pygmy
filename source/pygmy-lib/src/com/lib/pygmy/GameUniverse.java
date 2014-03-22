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
 * Manages the lifecycle of the level's game entities
 * @author Pygmy
 * 
 */
public interface GameUniverse {

	public void addGameEntity(GameEntity gameEntity);

	public void removeGameEntity(GameEntity gameEntity);

	public GameEntity getEntityAt(Tile tile);

	public Collection<GameEntity> getGameEntities();

	public void processMove(GameMove move);
	
	public void updateData(TurnData data);
	
	public String getState();

}
