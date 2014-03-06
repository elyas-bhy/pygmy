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

import com.client.pygmy.PygmyGameImpl;
import com.dev.pygmy.PygmyApp;
import com.lib.pygmy.GameEntity;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameMove;

/**
 * This class represents the view which shows the 
 * pieces of the board game on the screen.
 */
public class EntityView extends View {
	
	private PygmyGameImpl game;
	private Collection<GameEntity> entities;	// array that holds the entities
	private GameEntity draggedEntity = null;	// variable to know what entity is being dragged
	
	private boolean initial = true;
	private int tileSize = 0;
	private int offset = 0;
	
	
	private int posibleColumn = 0;
	private int posibleRow = 0;
	private Point entityCurrentPosition = new Point();

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
	public EntityView(Context context, PygmyGameImpl game) {
		super(context);
		setFocusable(true); // Necessary for getting the touch events

		this.game = game;
		GameLevel level = game.getContext().getCurrentLevel();
		entities = level.getUniverse().getGameEntities().values();
	}

	@Override 
	protected void onDraw(Canvas canvas) {
		// Setting the start point for the entities
		if (initial) {
			initial = false;
			
			Point coordXY;
			for (GameEntity entity : entities) {
				if (entity != null) {
					Point p = entity.getPosition();
					coordXY = GameBoardView.getTileCoord(p.x, p.y).getCoord();
					entity.setPixelX(coordXY.x);
					entity.setPixelY(coordXY.y);
				}
			}
		}

		// Draw the entity on the canvas
		for (GameEntity entity : entities) {
			if (entity != null) {
				canvas.drawBitmap(entity.getBitmap(), entity.getPixelX(), entity.getPixelY(), null);
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
		int minX = tileSize+offset;
		int minY = tileSize+offset;
		int maxX = minX + (tileSize * nbRows);
		int maxY = minY + (tileSize * nbColumns);

		switch (event.getAction()) { 

		// Touch down so check if the finger is on an entity
		case MotionEvent.ACTION_DOWN: 
			for (GameEntity entity : entities) {
				// Check all the bounds of the entity
				if (entity != null) {
					Point p = entity.getPosition();
					tileSize = GameBoardView.getTileCoord(p.x, p.y).getTileSquareSize();
					if (x > entity.getPixelX() && x < entity.getPixelX() + tileSize 
					 && y > entity.getPixelY() && y < entity.getPixelY() + tileSize) {
						// Get what entity is being dragged.
						draggedEntity = entity;
						entityCurrentPosition.x = entity.getPixelX();
						entityCurrentPosition.y = entity.getPixelY();
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
				
				GameViewManager.redrawOverlay();
				
				// Use the centre of the entity
				posibleColumn = (x * nbColumns) / maxX;
				posibleRow = (y * nbRows) / maxY;
				
				PygmyApp.logD("X: " + x + " Y: " + y);
				PygmyApp.logD("\t	currentPos (X, Y)-->("+posibleColumn +", "+posibleRow+")");

				if (minX < x && x < maxX && minY < y && y < maxY) {
					draggedEntity.setPixelX(x - tileSize/2);
					draggedEntity.setPixelY(y - tileSize/2);
				}
				PygmyApp.logD("tileSize: "+tileSize);
				
				//TODO tile.setActive(); or SelectedTileView(canvas)
			}

			break;

		case MotionEvent.ACTION_UP:
			if (minX < x && x < maxX && minY < y && y < maxY) {
				Point p = GameBoardView.getTileCoord(posibleColumn-1, posibleRow-1).getCoord();
				// TODO if (((MovableEntity)entityDragged).isLegalMove(move))
				draggedEntity.setPixelX(p.x);
				draggedEntity.setPixelY(p.y);
			} else {
				draggedEntity.setPixelX(entityCurrentPosition.x);
				draggedEntity.setPixelY(entityCurrentPosition.y);
			}
			
			// TODO visible(false) for image used to selected tile
			GameMove move = new GameMove();
			move.setEntity(draggedEntity);
			move.setMove(new Point(3,3));
			PygmyApp.logD("src: " + draggedEntity.getPosition());
			//game.onPlayerMove(move);
			PygmyApp.logD("dst: " + draggedEntity.getPosition());
			break;
			
		default:
			break;
		} 
		// Redraw the canvas
		invalidate(); 
		return true; 

	}
}