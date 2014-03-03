package com.lib.pygmy;

import com.lib.pygmy.base.Drawable;
import com.lib.pygmy.base.Overlappable;

import java.awt.Graphics;
import java.awt.Point;

public abstract class MovableEntity extends GameMovable
	implements Drawable, GameEntity, Overlappable {
	
	private PygmyGameLevel level;
	protected SpriteManager spriteManager = null;
	public static final int RENDERING_SIZE = 16;
	
	public MovableEntity(PygmyGameLevel level, Player player, String img, Point pos) {
		this.level = level;
		spriteManager = new SpriteManagerDefaultImpl(img, level.getCanvas(), RENDERING_SIZE, 6);
		spriteManager.setTypes("static");
		setPosition(pos);
		setPlayer(player);
	}
	
	@Override
	public void draw(Graphics g) {
		String spriteType = "static";
		spriteManager.reset();
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}
	
	@Override
	public PygmyGameContext getContext() {
		return level.getContext();
	}
	
	@Override
	public abstract void oneStepMoveAddedBehavior();
	
}