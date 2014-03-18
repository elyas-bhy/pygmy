package com.lib.pygmy;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.lib.pygmy.base.Drawable;
import com.lib.pygmy.base.Overlappable;

public abstract class PygmyGameEntity implements GameEntity,
		Drawable, Overlappable {

	private GameLevel level;
	private String playerId;
	private Tile tile;
	private Bitmap bitmap;
 
	private EntityType type;

	public PygmyGameEntity(GameLevel level, String playerId, EntityType type, Point pos) throws Exception {
		this.level = level;
		this.playerId = playerId;
		this.type = type;
		this.tile = new Tile(0,0,0);
		
		if ( (pos.x < 0 && pos.x >= level.getNumberRows()) ||
				(pos.y < 0 && pos.y >= level.getNumberColumns())) {
			throw new Exception("Position of the entity is out of the board.");
		}
		
		tile.setPosition(pos);
	}

	@Override
	public Tile getCurrentTile() {
		return tile;
	}
	
	@Override
	public void setCurrentTile(Tile tile) {
		this.tile = tile;
	}
	
	@Override
	public String getPlayerId() {
		return playerId;
	}
	
	@Override
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public void oneStepMove(GameMove move) {
		setCurrentTile(move.getMove());
		oneStepMoveAddedBehavior();
	}

	@Override
	public void draw() {

	}

	@Override
	public PygmyGameContext getContext() {
		return level.getContext();
	}
	
	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	@Override
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public EntityType getType() {
		return type;
	}

	@Override
	public abstract boolean isLegalMove(GameMove move);

	@Override
	public abstract void oneStepMoveAddedBehavior();

}
