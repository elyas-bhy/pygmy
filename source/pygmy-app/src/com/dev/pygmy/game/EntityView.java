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
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.client.pygmy.PygmyGameImpl;
import com.lib.pygmy.GameEntity;
import com.lib.pygmy.GameLevel;
import com.lib.pygmy.GameUniverse;

/**
 * This class represents the view which shows the 
 * pieces of the board game on the screen.
 */
public class EntityView extends View {
	
	private String TAG = "EntityView";
	private Collection<GameEntity> entities;	// array that holds the entities
	private int entityID = 0;				// variable to know what entity is being dragged
	private boolean initial = true;

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

		// Initialize game
		GameLevel level = game.getContext().getCurrentLevel();
		GameUniverse universe = level.getUniverse();
		Map<Point,GameEntity> map = universe.getGameEntities();
		entities = level.getUniverse().getGameEntities().values();
	}

	@Override 
	protected void onDraw(Canvas canvas) {
		// setting the start point for the entities
		if (initial) {
			initial = false;
			
			int posX = 0;
			int posY = 0;
			int[] coordXY;
			for (GameEntity entity : entities) {
				if (entity != null) {
					posX = entity.getBoundingPosition()[0];
					posY = entity.getBoundingPosition()[1];
					coordXY = GameBoardView.getCoord(posX, posY);				
					entity.setX(coordXY[0]);
					entity.setY(coordXY[1]);
					
//					Log.d(TAG, "X : "+posX);
//					Log.d(TAG, "Y : "+posY);
//					Log.d(TAG, "coordX : "+coordXY[0]);
//					Log.d(TAG, "coordY : "+coordXY[1]);
//					Log.d(TAG, "coordX+offset : "+coordXY[2]);
//					Log.d(TAG, "coordY+offset : "+coordXY[3]);
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

		switch (eventaction) { 

		// touch down so check if the finger is on an entity
		case MotionEvent.ACTION_DOWN: 
			entityID = 0;
			for (GameEntity entity : entities) {
				// check all the bounds of the entity
				if (entity != null) {
					if (X > entity.getX() && 
							X < entity.getX()+50 &&
								Y > entity.getY() && 
									Y < entity.getY()+50) {
						entityID = entity.getId();
						break;
					}
				}
			}
			break;

		// touch drag with the entity
		case MotionEvent.ACTION_MOVE:
			// move the entities the same as the finger
			if (entityID > 0) {
				//entities[entityID].setX(X-25);
				//entities[entityID].setY(Y-25);
			}

			break; 

		case MotionEvent.ACTION_UP:
			break; 
		} 
		// redraw the canvas
		invalidate(); 
		return true; 

	}
}