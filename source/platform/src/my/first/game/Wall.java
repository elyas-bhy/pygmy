package my.first.game;

import java.awt.Canvas;
import java.awt.Point;

import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;

public class Wall implements GameEntity {
	
	protected static DrawableImage image = null;
	int x;
	int y;
	
	public Wall(Canvas canvas, int xx, int yy) {
		image = new DrawableImage("images/wall.gif", canvas);
		x = xx;
		y = yy;
	}

	@Override
	public Point getPosition() {
		return new Point(x, y);
	}

}
