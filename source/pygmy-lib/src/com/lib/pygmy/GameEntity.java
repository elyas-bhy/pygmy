package com.lib.pygmy;

import android.graphics.Bitmap;

import com.lib.pygmy.view.Tile;

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
	
	public Tile getCurrentTile();
	
	public void setCurrentTile(Tile tile);
	
	public Player getPlayer();
	
	public void setPlayer(Player player);
	
	public void oneStepMove(GameMove move);
	
	public boolean isLegalMove(GameMove move);
	
	public void oneStepMoveAddedBehavior();

	/**
	 * Returns the image file of the entity
	 */
	public Bitmap getBitmap();
}