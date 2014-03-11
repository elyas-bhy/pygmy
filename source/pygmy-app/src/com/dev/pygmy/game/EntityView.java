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

package com.dev.pygmy.game;

import java.util.Collection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.dev.pygmy.PygmyApp;
import com.lib.pygmy.GameEntity;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;
import com.lib.pygmy.PygmyGame;
import com.lib.pygmy.view.Tile;

/**
 * This class represents the view which shows the 
 * pieces of the board game on the screen.
 */
public class EntityView extends View {
	
	private PygmyGame game;
	private Collection<GameEntity> entities;	// array that holds the entities
	private GameEntity draggedEntity = null;	// variable to know what entity is being dragged
	
	private boolean initial = true;
	private int tileSize = 0;
	private int offset = 0;
	
	
	private int possibleColumn = 0;
	private int possibleRow = 0;
	private Tile entityCurrentPosition;

	/**
	 * Default constructor.
	 * @param context		Context parent
	 */
	public EntityView(Context context) {
		super(context);
	}

	/**
	 * Constructs the view with the entities (pieces) of the board game.
	 * @param context		Context parent.
	 * @param game			Reference to the game context 
	 */
	public EntityView(Context context, PygmyGame game) {
		super(context);
		setFocusable(true); // Necessary for getting the touch events

		this.game = game;
		GameLevel level = game.getContext().getCurrentLevel();
		entities = level.getUniverse().getGameEntities().values();
	}
	
	private void initTiles() {
		Tile tile;
		Point p;
		for (GameEntity entity : entities) {
			if (entity != null) {
				p = entity.getCurrentTile().getPosition();
				tile = GameBoardView.getTileAt(p.x, p.y);
				entity.setCurrentTile(tile);
			}
		}
	}

	@Override 
	protected void onDraw(Canvas canvas) {
		// Setting the start point for the entities
		if (initial) {
			initial = false;
			initTiles();
		}

		// Draw the entity on the canvas
		Point coords;
		for (GameEntity entity : entities) {
			if (entity != null) {
				coords = entity.getCurrentTile().getCoordinates();
				canvas.drawBitmap(entity.getBitmap(), coords.x, coords.y, null);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		int nbRows = 8;
		int nbColumns = 8;
		// tileSize=72 .. offset=24
		//PygmyApp.logD("tileSize: "+tileSize+" offset: "+offset);
		
		// 96 96 672 672
		int minX = tileSize + offset;
		int minY = tileSize + offset;
		int maxX = minX + (tileSize * nbRows);
		int maxY = minY + (tileSize * nbColumns);

		switch (event.getAction()) { 

		// Touch down so check if the finger is on an entity
		case MotionEvent.ACTION_DOWN: 
			Point coords;
			Tile tile;
			for (GameEntity entity : entities) {
				// Check all the bounds of the entity
				if (entity != null) {
					tile = entity.getCurrentTile();
					coords = tile.getCoordinates();
					tileSize = tile.getWidth();
					
					if (x > coords.x && x < coords.x + tileSize 
					 && y > coords.y && y < coords.y + tileSize) {
						// Get what entity is being dragged.
						draggedEntity = entity;
						entityCurrentPosition = entity.getCurrentTile();
						offset = tileSize/3;
						break;
					}
				}
			}
			break;

		// Touch drag with the entity
		case MotionEvent.ACTION_MOVE:
			// Move the entities the same as the finger
			if (draggedEntity != null) {
				if (minX < x && x < maxX && minY < y && y < maxY) {
					// Identify the hovered tile
					possibleRow = (y * nbRows) / maxY;
					possibleColumn = (x * nbColumns) / maxX;
					Tile nextTile = GameBoardView.getTileAt(possibleRow-1, possibleColumn-1);
					
					// Show the future position of the entity
					GameViewManager.redrawOverlay();
					GameViewManager.getOverlay()
							.setCoordinates(nextTile.getCoordinates().x, 
											nextTile.getCoordinates().y, tileSize, tileSize);

					// Move entity
					draggedEntity.setCurrentTile(nextTile);

					PygmyApp.logD("X: " + x + " Y: " + y);
					PygmyApp.logD("\t	currentPos (X, Y)-->("+possibleRow +", "+possibleColumn+")");
					PygmyApp.logD("tileSize: "+tileSize);
				}
			}

			break;

		case MotionEvent.ACTION_UP:
			GameViewManager.resetOverlay();
			
			// Entity should not go outside of the board
			if (minX < x && x < maxX && minY < y && y < maxY) {
				Tile dst = GameBoardView.getTileAt(possibleRow-1, possibleColumn-1);
				GameMove move = new GameMove(draggedEntity, dst);
				game.onPlayerMove(move);
			} else {
				draggedEntity.setCurrentTile(entityCurrentPosition);
			}
			break;
			
		default:
			break;
		} 
		// Redraw the canvas
		invalidate(); 
		return true; 
	}
}