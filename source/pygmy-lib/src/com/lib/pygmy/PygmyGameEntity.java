package com.lib.pygmy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.lib.pygmy.base.Drawable;
import com.lib.pygmy.base.Overlappable;
import com.lib.pygmy.view.Tile;

public abstract class PygmyGameEntity implements GameEntity,
		Drawable, Overlappable {

	private Player player;
	private Tile tile;
 
	private int resId;
	private Bitmap img;
	private GameLevel level;

	public PygmyGameEntity(GameLevel level, Player player, Integer resId, Point pos) {
		this.level = level;
		this.player = player;
		this.resId = resId;
		this.tile = new Tile(0,0,0);
		tile.setPosition(pos);
		
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(level.getContext().getResources(), resId);
		img = Bitmap.createScaledBitmap(img, 70, 70, false);
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
	public Bitmap getBitmap() {
		return img;
	}

	@Override
	public int getResourceId() {
		return resId;
	}

	@Override
	public abstract boolean isLegalMove(GameMove move);

	@Override
	public abstract void oneStepMoveAddedBehavior();

}
