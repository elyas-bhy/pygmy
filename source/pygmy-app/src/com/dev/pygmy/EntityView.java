package com.dev.pygmy;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EntityView extends View {
	private String TAG="EntityView";
	private Entity[] entities; // array that holds the entities
	private int entityID = 0; // variable to know what entity is being dragged
	private boolean initial = true;

	public EntityView(Context context) {
		super(context);
		Log.d(TAG, "Constructor");
		setFocusable(true); //necessary for getting the touch events
		
		int numberOfEntities = 3;
		entities = new Entity[numberOfEntities];

		// declare each entity with the Entity class
		createGameEntities(context, entities);
	}
	
	private void createGameEntities(Context context, Entity [] entities) {
		entities[0] = new Entity(context,R.drawable.black_bishop);
		entities[1] = new Entity(context,R.drawable.black_king);
		entities[2] = new Entity(context,R.drawable.black_pawn);		
	}

	// the method that draws the entities
	@Override 
	protected void onDraw(Canvas canvas) {
		//canvas.drawColor(0xFFCCCCCC);     //if you want another background color

		// setting the start point for the entities
		if (initial) {
			initial = false;
			// FIXME: fix this bad hack
			int [][] coordXY = GameBoardView.getRectCoord();
			
			for (int index=0; index<entities.length; index++) {
				entities[index].setX(coordXY[index+1][0]);
				entities[index].setY(coordXY[index+1][1]);
			}
		}

		//draw the entity on the canvas
		for (Entity ent : entities) {
			canvas.drawBitmap(ent.getBitmap(), ent.getX(), ent.getY(), null);
		}
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction(); 

		int X = (int)event.getX(); 
		int Y = (int)event.getY(); 

		switch (eventaction) { 

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on an entity
			entityID = 0;
			for (Entity ent : entities) {
				// check all the bounds of the entity
				if (X > ent.getX() && X < ent.getX()+50 && Y > ent.getY() && Y < ent.getY()+50){
					entityID = ent.getID();
					break;
				}
			}
			break; 


		case MotionEvent.ACTION_MOVE:   // touch drag with the entity
			// move the entities the same as the finger
			if (entityID > 0) {
				entities[entityID-1].setX(X-25);
				entities[entityID-1].setY(Y-25);
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