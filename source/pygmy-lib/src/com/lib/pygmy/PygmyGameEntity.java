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

import android.graphics.Bitmap;

import com.lib.pygmy.base.Drawable;
import com.lib.pygmy.base.Overlappable;
import com.lib.pygmy.util.Point;

public abstract class PygmyGameEntity implements GameEntity,
		Drawable, Overlappable, Serializable {
	
	private static final long serialVersionUID = -5161169793526131313L;
	
	private GameLevel level;
	private String playerId;
	private Tile tile;
	private EntityType type;
	
	private transient Bitmap bitmap;

	public PygmyGameEntity(GameLevel level, String playerId, EntityType type, Point pos) {
		this.level = level;
		this.playerId = playerId;
		this.type = type;
		this.tile = new Tile(0,0,0);
		
		if ( (pos.x < 0 && pos.x >= level.getNumberRows()) ||
				(pos.y < 0 && pos.y >= level.getNumberColumns())) {
			throw new IllegalStateException("Position of the entity is out of the board.");
		}
		
		tile.setPosition(pos);
	}

	@Override
	public Tile getCurrentTile() {
		return tile;
	}
	
	@Override
	public void setCurrentTile(Tile tile) {
		this.tile = tile;
	}
	
	@Override
	public String getPlayerId() {
		return playerId;
	}
	
	@Override
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public void oneStepMove(GameMove move) {
		setCurrentTile(move.getDestination());
		oneStepMoveAddedBehavior();
	}

	@Override
	public void draw() {

	}

	@Override
	public PygmyGameContext getContext() {
		return level.getContext();
	}
	
	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	@Override
	public void setBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			throw new IllegalStateException("No Bitmap. Did you request it at setup?");
		}
		this.bitmap = bitmap;
	}

	@Override
	public EntityType getType() {
		return type;
	}

	@Override
	public abstract boolean isLegalMove(GameMove move);

	@Override
	public abstract void oneStepMoveAddedBehavior();

}
