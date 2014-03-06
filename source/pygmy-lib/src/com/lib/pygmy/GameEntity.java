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
	
	public Point getPosition();
	
	public void setPosition(Point p);
	
	public int getPixelX();
	
	public void setPixelX(int x);
	
	public int getPixelY();
	
	public void setPixelY(int y);
	
	public Player getPlayer();
	
	public void setPlayer(Player player);
	
	public void oneStepMove(Point p);
	
	public boolean isLegalMove(GameMove move);
	
	public void oneStepMoveAddedBehavior();

	/**
	 * Returns the image file of the entity
	 */
	public Bitmap getBitmap();
}