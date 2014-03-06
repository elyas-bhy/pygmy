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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.client.pygmy.PygmyGameImpl;
import com.lib.pygmy.GameEntity;
import com.lib.pygmy.GameLevel;

/**
 * This class represents the view which shows the 
 * pieces of the board game on the screen.
 */
public class EntityView extends View {
	
	private String TAG = "EntityView";
	private Collection<GameEntity> entities;	// array that holds the entities
	private GameEntity entityDragged = null;				// variable to know what entity is being dragged
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
	 * @param context			Context parent.
	 * @param gameParameters	Parameters to set the board according to 
	 * 							the game chosen by user. 
	 */
	public EntityView(Context context, PygmyGameImpl game) {
		super(context);
		Log.d(TAG, "Constructor");
		setFocusable(true); // Necessary for getting the touch events

		// Initialise game
		GameLevel level = game.getContext().getCurrentLevel();
		entities = level.getUniverse().getGameEntities().values();
		Log.d(TAG, entities.toString());
	}

	@Override 
	protected void onDraw(Canvas canvas) {
		// setting the start point for the entities
		if (initial) {
			initial = false;
			
			int posX = 0;
			int posY = 0;
			Point coordXY;
			
			for (GameEntity entity : entities) {
				if (entity != null) {
					posX = entity.getBoundingPosition()[0];
					posY = entity.getBoundingPosition()[1];
					coordXY = GameBoardView.getTileCoord(posX, posY).getCoord();
					entity.setX(coordXY.x);
					entity.setY(coordXY.y);
				}
			}
		}

		//draw the entity on the canvas
		for (GameEntity entity : entities) {
			if (entity != null) {
				canvas.drawBitmap(entity.getBitmap(), entity.getX(), entity.getY(), null);
			}
		}
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();

		int X = (int)event.getX();
		int Y = (int)event.getY();
		
		//SelectedTileView.draw(canvas, Y, topLeft, bottomRight);

		switch (eventaction) { 

		// touch down so check if the finger is on an entity
		case MotionEvent.ACTION_DOWN: 
			for (GameEntity entity : entities) {
				// check all the bounds of the entity
				if (entity != null) {
					tileSize = GameBoardView.getTileCoord(entity.getPosition().x, 
							entity.getPosition().y).getTileSquareSize();
					if (X > entity.getX() && 
							X < entity.getX()+tileSize &&
								Y > entity.getY() && 
									Y < entity.getY()+tileSize) {
						// Get what entity is being dragged.
						entityDragged = entity;
						entityCurrentPosition.x = entity.getX();
						entityCurrentPosition.y = entity.getY();
						offset = tileSize/3;
						break;
					}
				}
			}
			break;

		// touch drag with the entity
		case MotionEvent.ACTION_MOVE:
			// move the entities the same as the finger
			if (entityDragged != null) {
				
				int nbRows = 8;
				int nbColumns = 8;
				// 72 .. 24
				Log.d(TAG, "tileSize: "+tileSize+" offset: "+offset);
				
				// 96 96 672 672
				int minX = tileSize+offset;
				int minY = tileSize+offset;
				int maxX = minX+(tileSize*nbRows);
				int maxY = minY+(tileSize*nbColumns);
				
				// Use the centre of the entity
				posibleColumn = (X * nbColumns)/maxX;
				posibleRow = (Y * nbRows)/maxY;
				
				Log.d(TAG, "X*nbColumns: "+(X*nbColumns));
				Log.d(TAG, "Y*nbRows: "+(Y*nbRows));
				Log.d(TAG, "X: "+X+" Y: "+Y);
				Log.d(TAG, "\t	currentPos (X, Y)-->("+posibleColumn +", "+posibleRow+")");
				
				int moveX = X-(tileSize/2);
				int moveY = Y-(tileSize/2);
				entityDragged.setX(moveX);
				entityDragged.setY(moveY);
				
				//TODO tile.setActive(); or SelectedTileView(canvas)
			}

			break; 

		case MotionEvent.ACTION_UP:
			Point move = GameBoardView.getTileCoord(posibleColumn-1, posibleRow-1).getCoord();
			// TODO if (((MovableEntity)entityDragged).isLegalMove(move))
			entityDragged.setX(move.x);
			entityDragged.setY(move.y);
			// else 
//			entityDragged.setX(entityCurrentPosition.x);
//			entityDragged.setY(entityCurrentPosition.y);
			
			// TODO visible(false) for image used to selected tile
			break; 
		} 
		// redraw the canvas
		invalidate(); 
		return true; 

	}
}
