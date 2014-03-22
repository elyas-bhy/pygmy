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

/**
 * Represents a move by associating the target entity with its 
 * future destination.
 * @author Pygmy
 *
 */
public class GameMove implements Serializable {
	
	private static final long serialVersionUID = -2310782677683578117L;
	
	private GameEntity entity;
	private Tile dest;
	
	public GameMove(GameEntity entity, Tile dest) {
		this.entity = entity;
		this.dest = dest;
	}

	/**
	 * Returns the entity to move
	 */
	public GameEntity getEntity() {
		return entity;
	}

	/**
	 * Sets the entity to move
	 * @param entity
	 */
	public void setEntity(GameEntity entity) {
		this.entity = entity;
	}

	/**
	 * Returns the entity's destination
	 */
	public Tile getDestination() {
		return dest;
	}

	/**
	 * Sets the entity's destination
	 * @param dest
	 */
	public void setDestination(Tile dest) {
		this.dest = dest;
	}

}