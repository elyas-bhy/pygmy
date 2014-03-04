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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EntityImpl implements Entity {
	//final private String TAG = "ENTITY";
	
	private Bitmap img; 			// the image of the entity
	private int coordX = 0; 		// the x coordinate at the canvas
	private int coordY = 0; 		// the y coordinate at the canvas
	private int id; 				// gives every entity his own id
	private static int count = 1;
	private int [] tilePosition;	// position (x,y) of a 


	public EntityImpl(Context context, int row, int column, int drawable) {
		tilePosition = new int[2];
		tilePosition[0] = row;
		tilePosition[1] = column;
		
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(context.getResources(), drawable);
		id=count;
		count++;
	}

	/* (non-Javadoc)
	 * @see com.dev.pygmy.Entity#getBoundingPosition()
	 */
	public int[] getBoundingPosition() {
		return tilePosition;
	}

	public static int getCount() {
		return count;
	}

	/* (non-Javadoc)
	 * @see com.dev.pygmy.Entity#setX(int)
	 */
	@Override
	public void setX(int x) {
		coordX = x;
	}

	/* (non-Javadoc)
	 * @see com.dev.pygmy.Entity#getX()
	 */
	@Override
	public int getX() {
		return coordX;
	}

	/* (non-Javadoc)
	 * @see com.dev.pygmy.Entity#setY(int)
	 */
	@Override
	public void setY(int y) {
		coordY = y;
	}

	/* (non-Javadoc)
	 * @see com.dev.pygmy.Entity#getY()
	 */
	@Override
	public int getY() {
		return coordY;
	}

	/* (non-Javadoc)
	 * @see com.dev.pygmy.Entity#getID()
	 */
	@Override
	public int getID() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.dev.pygmy.Entity#getBitmap()
	 */
	@Override
	public Bitmap getBitmap() {
		return img;
	}
}