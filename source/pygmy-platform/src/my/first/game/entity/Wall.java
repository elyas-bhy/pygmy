package my.first.game.entity;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.PygmyGameContext;
import gameframework.game.PygmyGameLevel;

import java.awt.Graphics;
import java.awt.Point;

public class Wall implements Drawable, GameEntity {
	
	private int x, y;
	private PygmyGameLevel level;
	private DrawableImage image = null;
	
	public Wall(PygmyGameLevel level, int xx, int yy) {
		this.level = level;
		image = new DrawableImage("images/wall.gif", level.getCanvas());
		x = xx;
		y = yy;
	}

	@Override
	public Point getPosition() {
		return new Point(x, y);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, 16, 16, null);
	}

	@Override
	public PygmyGameContext getContext() {
		return level.getContext();
	}

}
