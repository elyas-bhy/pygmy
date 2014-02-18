package gameframework.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import org.junit.Test;

public class IntersectToolsTest implements Movable {

	private Point direction;
	private Point position;

	@Test
	public void leftToRight() throws Exception {
		setPosition(0, 0);
		setDirection(1, 0);
		assertShape(0, 0, 3, 1);
	}

	@Test
	public void rightToLeft() throws Exception {
		setPosition(0, 0);
		setDirection(-1, 0);
		assertShape(-2, 0, 1, 1);
	}

	@Test
	public void upToDown() throws Exception {
		setPosition(0, 0);
		setDirection(0, 1);
		assertShape(0, 0, 1, 3);
	}

	@Test
	public void downToUp() throws Exception {
		setPosition(0, 0);
		setDirection(0, -1);
		assertShape(0, -2, 1, 1);
	}

	private void assertShape(int x1, int y1, int x2, int y2) {
		Shape intersectShape = IntersectTools.getIntersectShape(this, new DirectionDefaultImpl(direction));
		assertTrue(intersectShape instanceof Rectangle);
		Rectangle rectangle = (Rectangle) intersectShape;
		assertEquals(new Point(x1, y1), rectangle.getLocation());
		assertEquals(new Dimension(x2 - x1, y2 - y1), rectangle.getSize());
	}

	private void setDirection(int x, int y) {
		this.direction = new Point(x, y);
	}

	private void setPosition(int x0, int y0) {
		this.position = new Point(x0, y0);
	}

	@Override
	public Point getPosition() {
		return this.position;
	}

	@Override
	public Direction getSpeedVector() {
		return new DirectionDefaultImpl(direction);
	}

	@Override
	public void oneStepMove() {
		fail("No need to move this movable");
	}

	@Override
	public void setSpeedVector(Direction m) {
		fail("No need to set the speed vector");
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(position, new Dimension(1, 1));
	}

}
