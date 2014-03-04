package com.lib.pygmy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.lib.pygmy.base.Drawable;
import com.lib.pygmy.base.Overlappable;

public abstract class MovableEntity extends GameMovable implements Drawable,
		GameEntity, Overlappable {

	private Bitmap img; 			// the image of the entity
	private int coordX = 0; 		// the x coordinate at the canvas
	private int coordY = 0; 		// the y coordinate at the canvas
	private int id; 				// gives every entity his own id
	private static int count = 1;
	private int [] tilePosition;	// position (x,y) of a 

	private PygmyGameLevel level;

	public MovableEntity(Context context, PygmyGameLevel level, Player player, 
			int drawable, Point pos) {
		this.level = level;
		setPosition(pos);
		setPlayer(player);
		
		tilePosition = new int[2];
		tilePosition[0] = pos.x;
		tilePosition[1] = pos.y;
		
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(context.getResources(), drawable);
		id = count++;
	}

	/* (non-Javadoc)
	 * @see com.dev.pygmy.Entity#getBoundingPosition()
	 */
	public int[] getBoundingPosition() {
		return tilePosition;
	}

	@Override
	public void draw() {

	}

	@Override
	public PygmyGameContext getContext() {
		return level.getContext();
	}

	@Override
	public abstract void oneStepMoveAddedBehavior();

	public static int getCount() {
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dev.pygmy.Entity#setX(int)
	 */
	@Override
	public void setX(int x) {
		coordX = x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dev.pygmy.Entity#getX()
	 */
	@Override
	public int getX() {
		return coordX;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dev.pygmy.Entity#setY(int)
	 */
	@Override
	public void setY(int y) {
		coordY = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dev.pygmy.Entity#getY()
	 */
	@Override
	public int getY() {
		return coordY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dev.pygmy.Entity#getID()
	 */
	@Override
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dev.pygmy.Entity#getBitmap()
	 */
	@Override
	public Bitmap getBitmap() {
		return img;
	}

}
