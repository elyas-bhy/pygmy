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

package com.dev.pygmy;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * This class represents the view which shows the pieces of the board game on the screen.
 */
public class EntityView extends View {
	private String TAG="EntityView";
	private Entity[] entities;		// array that holds the entities
	private int entityID = 0;		// variable to know what entity is being dragged
	private boolean initial = true;

	/**
	 * Constructor by default. 
	 * @param context
	 */
	public EntityView(Context context) {
		super(context);
	}

	/**
	 * Constructs the view with the entities (pieces) of the board game.
	 * @param context
	 * @param gameParameters
	 */
	public EntityView(Context context, HashMap<String, Object> gameParameters) {
		super(context);
		Log.d(TAG, "Constructor");
		setFocusable(true); //necessary for getting the touch events

		// Initialise game
		entities = (Entity[])gameParameters.get("entities");
	}

	@Override 
	protected void onDraw(Canvas canvas) {
		//canvas.drawColor(0xFFCCCCCC);     //if you want another background color

		// setting the start point for the entities
		if (initial) {
			initial = false;
			// FIXME: fix this bad hack, getRectCoord is a static method.
			int [][] coordXY = GameBoardView.getRectCoord();

			for (int id=1; id<entities.length; id++) {
				if (entities[id] != null) {
					entities[id].setX(coordXY[id][0]);
					entities[id].setY(coordXY[id][1]);
				}
			}
		}

		//draw the entity on the canvas
		for (Entity ent : entities) {
			if (ent != null) {
				canvas.drawBitmap(ent.getBitmap(), ent.getX(), ent.getY(), null);
			}
		}
	}

	/**
	 * Events when touching the screen
	 */
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();

		int X = (int)event.getX();
		int Y = (int)event.getY();

		switch (eventaction) { 

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on an entity
			entityID = 0;
			for (Entity ent : entities) {
				// check all the bounds of the entity
				if (ent != null) {
					if (X > ent.getX() && 
							X < ent.getX()+50 &&
								Y > ent.getY() && 
									Y < ent.getY()+50) {
						entityID = ent.getID();
						break;
					}
				}
			}
			break;

		case MotionEvent.ACTION_MOVE:   // touch drag with the entity
			// move the entities the same as the finger
			if (entityID > 0) {
				entities[entityID].setX(X-25);
				entities[entityID].setY(Y-25);
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