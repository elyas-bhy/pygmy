package pacman.entity;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Ghost extends GameMovable implements Drawable, GameEntity,
		Overlappable {
	protected static DrawableImage image = null;
	protected boolean movable = true;
	protected int afraidTimer = 0;
	protected int maxAfraidTimer = 0;
	protected boolean active = true;
	private final SpriteManagerDefaultImpl spriteManager;
	public static final int RENDERING_SIZE = 16;

	public Ghost(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/ghost.gif",
				defaultCanvas, RENDERING_SIZE, 6);
		spriteManager.setTypes(
				//
				"left",
				"right",
				"up",
				"down",//
				"beginAfraid-left",
				"beginAfraid-right",
				"beginAfraid-up",
				"beginAfraid-down", //
				"endAfraid-left", "endAfraid-right",
				"endAfraid-up",
				"endAfraid-down", //
				"inactive-left", "inactive-right", "inactive-up",
				"inactive-down", //
				"unused");
	}

	public boolean isAfraid() {
		return afraidTimer > 0;
	}

	public void setAfraid(int timer) {
		maxAfraidTimer = afraidTimer = timer;
	}

	public boolean isActive() {
		return active;
	}

	public void setAlive(boolean aliveState) {
		active = aliveState;
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		if (!isActive()) {
			spriteType = "inactive-";
		} else if (afraidTimer > maxAfraidTimer / 2) {
			spriteType = "beginAfraid-";
		} else if (isAfraid()) {
			spriteType = "endAfraid-";
		}

		if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType += "right";
		}

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (movable) {
			spriteManager.increment();
			if (isAfraid()) {
				afraidTimer--;
			}
		}
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
}
