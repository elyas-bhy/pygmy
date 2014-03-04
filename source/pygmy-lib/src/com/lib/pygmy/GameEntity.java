package com.lib.pygmy;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Interface of board game pieces (entities)
 * @author Pygmy
 */
public interface GameEntity {
	
	/**
	 * Returns the current game context
	 * @return
	 */
	public PygmyGameContext getContext();
	
	/**
	 * @return the initial position of the entity on the board.
	 */
	public int[] getBoundingPosition();

	/**
	 * Returns the current position
	 * @return
	 */
	public Point getPosition();
	
	/**
	 * Sets the X position of the image of the entity on the board 
	 * @param posX
	 */
	public void setX(int posX);

	/**
	 * Returns the X position of the entity
	 */
	public int getX();

	/**
	 * Sets the Y position of the image of the entity on the board 
	 * @param posY
	 */
	public void setY(int posY);

	/**
	 * Returns the Y position of the entity
	 */
	public int getY();

	/**
	 * Returns the identifier of the entity
	 */
	public int getId();

	/**
	 * Returns the image file of the entity
	 */
	public Bitmap getBitmap();
}