package com.dev.pygmy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Entity {
	final private String TAG = "ENTITY";
	
	private Bitmap img; 			// the image of the entity
	private int coordX = 0; 		// the x coordinate at the canvas
	private int coordY = 0; 		// the y coordinate at the canvas
	private int id; 				// gives every entity his own id
	private static int count = 1;

	public Entity(Context context, int drawable) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(context.getResources(), drawable); 
		id=count;
		count++;
	}

	public static int getCount() {
		return count;
	}

	void setX(int x) {
		coordX = x;
	}

	public int getX() {
		return coordX;
	}

	void setY(int y) {
		coordY = y;
	}

	public int getY() {
		return coordY;
	}

	public int getID() {
		return id;
	}

	public Bitmap getBitmap() {
		return img;
	}
}