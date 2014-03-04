package com.lib.pygmy;

import android.graphics.Point;

import com.lib.pygmy.base.Drawable;
import com.lib.pygmy.base.Overlappable;

public abstract class MovableEntity extends GameMovable
	implements Drawable, GameEntity, Overlappable {
	
	private PygmyGameLevel level;
	public static final int RENDERING_SIZE = 16;
	
	public MovableEntity(PygmyGameLevel level, Player player, String img, Point pos) {
		this.level = level;
		setPosition(pos);
		setPlayer(player);
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
	
}
