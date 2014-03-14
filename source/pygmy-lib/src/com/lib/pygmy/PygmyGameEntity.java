package com.lib.pygmy;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.lib.pygmy.base.Drawable;
import com.lib.pygmy.base.Overlappable;
import com.lib.pygmy.view.Tile;

public abstract class PygmyGameEntity implements GameEntity,
		Drawable, Overlappable {

	private GameLevel level;
	private Player player;
	private Tile tile;
 
	private EntityType type;
	private transient Bitmap bitmap;

	public PygmyGameEntity(GameLevel level, Player player, EntityType type, Point pos) {
		this.level = level;
		this.player = player;
		this.type = type;
		this.tile = new Tile(0,0,0);
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
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void setPlayer(Player player) {
		this.player = player;
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
	public EntityType getType() {
		return type;
	}

	@Override
	public abstract boolean isLegalMove(GameMove move);

	@Override
	public abstract void oneStepMoveAddedBehavior();

}
