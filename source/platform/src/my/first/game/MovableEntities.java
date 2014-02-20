package my.first.game;


import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManager;
import gameframework.game.SpriteManagerDefaultImpl;

public abstract class MovableEntities extends GameMovable implements Drawable, GameEntity,
Overlappable {
	
	protected SpriteManager spriteManager=null;
	public static final int RENDERING_SIZE = 16;
	
	
	public MovableEntities(Canvas defaultCanvas, String img) {
		this.spriteManager = new SpriteManagerDefaultImpl(img,
				defaultCanvas, RENDERING_SIZE, 6);
		spriteManager.setTypes(
				//
				"right", "left", "up",
				"down",//
				"invulnerable-right", "invulnerable-left", "invulnerable-up",
				"invulnerable-down", //
				"unused", "static", "unused");
	}
	
	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		if (tmp.getX() == 1) {
			spriteType += "right";
		} else if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType = "static";
			spriteManager.reset();
		}
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
		
	}
	
	@Override
	public abstract void oneStepMoveAddedBehavior();
	

}
