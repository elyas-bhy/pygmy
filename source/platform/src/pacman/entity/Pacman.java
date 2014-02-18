package pacman.entity;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManager;
import gameframework.game.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Pacman extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 16;
	protected boolean movable = true;
	protected boolean vulnerable = false;
	protected int vulnerableTimer = 0;

	public Pacman(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/pac1.gif",
				defaultCanvas, RENDERING_SIZE, 6);
		spriteManager.setTypes(
				//
				"right", "left", "up",
				"down",//
				"invulnerable-right", "invulnerable-left", "invulnerable-up",
				"invulnerable-down", //
				"unused", "static", "unused");
	}

	public void setInvulnerable(int timer) {
		vulnerableTimer = timer;
	}

	public boolean isVulnerable() {
		return (vulnerableTimer <= 0);
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;
		if (!isVulnerable()) {
			spriteType += "invulnerable-";
		}

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
			movable = false;
		}
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());

	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (movable) {
			spriteManager.increment();
			if (!isVulnerable()) {
				vulnerableTimer--;
			}
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
}
