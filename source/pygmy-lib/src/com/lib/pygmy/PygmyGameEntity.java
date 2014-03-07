package com.lib.pygmy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.lib.pygmy.base.Drawable;
import com.lib.pygmy.base.Overlappable;

public abstract class PygmyGameEntity implements GameEntity,
		Drawable, Overlappable {

	private Player player;
	private Point position = new Point();
	private int pixelX;
	private int pixelY;
 
	private Bitmap img; 		// the image of the entity
	private PygmyGameLevel level;

	public PygmyGameEntity(PygmyGameLevel level, Player player, int drawable, Point pos) {
		this.level = level;
		this.position = pos;
		this.player = player;
		
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(level.getContext().getResources(), drawable);
		img = Bitmap.createScaledBitmap(img, 70, 70, false);
	}

	@Override
	public Point getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(Point p) {
		position.x = p.x;
		position.y = p.y;
	}

	@Override
	public int getPixelX() {
		return pixelX;
	}

	@Override
	public void setPixelX(int x) {
		this.pixelX = x;
	}
	
	@Override
	public void setPixelY(int y) {
		this.pixelY = y;
	}
	
	@Override
	public int getPixelY() {
		return pixelY;
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
	public void oneStepMove(Point p) {
		setPosition(p);
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
	public abstract boolean isLegalMove(GameMove move);

	@Override
	public abstract void oneStepMoveAddedBehavior();

}
