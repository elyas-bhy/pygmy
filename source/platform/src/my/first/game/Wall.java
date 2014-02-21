package my.first.game;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

public class Wall implements Drawable, MoveBlocker, GameEntity {
	
	private int x, y;
	protected static DrawableImage image = null;
	
	public Wall(Canvas canvas, int xx, int yy) {
		image = new DrawableImage("images/wall.gif", canvas);
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

}
