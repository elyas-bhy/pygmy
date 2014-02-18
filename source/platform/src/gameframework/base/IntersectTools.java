package gameframework.base;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * Utility class providing static methods to compute intersections
 */
public class IntersectTools {

	/**
	 * Hides the constructor
	 */
	private IntersectTools() {
		super();
	}

	/**
	 * Return a polygonal zone (with max 6 sides) containing the whole path
	 * along the next step move.
	 * 
	 * @return Either a {@link java.awt.Rectangle rectangle} or an
	 *         {@link java.awt.Polygon hexagon}.
	 */
	public static Shape getIntersectShape(Movable movable,
			Direction speedVector) {
		// Compute the intersection shape using the movable properties
		int dX = (int) speedVector.getDirection().getX();
		int dY = (int) speedVector.getDirection().getY();

		// We need the bounding box of the movable
		int x1 = (int) movable.getPosition().getX();
		int y1 = (int) movable.getPosition().getY();
		int x2 = x1 + (int) movable.getBoundingBox().getWidth();
		int y2 = y1 + (int) movable.getBoundingBox().getHeight();

		// We build the intersection polygon now
		// As usual we need to consider the 8 direction to build different kind
		// of shape
		Shape intersectShape;

		// The first quarter of the plane
		if ((dX > 0) && (dY > 0)) {
			intersectShape = new Polygon();
			((Polygon) intersectShape).addPoint(x1, y1);
			((Polygon) intersectShape).addPoint(x1, y2);
			((Polygon) intersectShape).addPoint(x2, y2);
			((Polygon) intersectShape).addPoint(x2 + dX, y2 + dY);
			((Polygon) intersectShape).addPoint(x2 + dX, y1 + dY);
			((Polygon) intersectShape).addPoint(x1 + dX, y1 + dY);
		}
		// The second quarter of the plane
		else if ((dX < 0) && (dY > 0)) {
			intersectShape = new Polygon();
			((Polygon) intersectShape).addPoint(x1, y2);
			((Polygon) intersectShape).addPoint(x2, y2);
			((Polygon) intersectShape).addPoint(x2, y1);
			((Polygon) intersectShape).addPoint(x2 + dX, y1 + dY);
			((Polygon) intersectShape).addPoint(x1 + dX, y1 + dY);
			((Polygon) intersectShape).addPoint(x1 + dX, y2 + dY);
		}
		// The third quarter of the plane
		else if ((dX < 0) && (dY < 0)) {
			intersectShape = new Polygon();
			((Polygon) intersectShape).addPoint(x2, y2);
			((Polygon) intersectShape).addPoint(x2, y1);
			((Polygon) intersectShape).addPoint(x1, y1);
			((Polygon) intersectShape).addPoint(x1 + dX, y1 + dY);
			((Polygon) intersectShape).addPoint(x1 + dX, y2 + dY);
			((Polygon) intersectShape).addPoint(x2 + dX, y2 + dY);
		}
		// The fourth quarter of the plane
		else if ((dX > 0) && (dY < 0)) {

			intersectShape = new Polygon();
			((Polygon) intersectShape).addPoint(x2, y1);
			((Polygon) intersectShape).addPoint(x1, y1);
			((Polygon) intersectShape).addPoint(x1, y2);
			((Polygon) intersectShape).addPoint(x1 + dX, y2 + dY);
			((Polygon) intersectShape).addPoint(x2 + dX, y2 + dY);
			((Polygon) intersectShape).addPoint(x2 + dX, y1 + dY);
		}
		// And now the axis
		else if ((dX == 0) && (dY > 0)) {
			intersectShape = new Rectangle(x1, y1, x2 - x1, y2 - y1 + dY);
		} else if ((dX == 0) && (dY < 0)) {
			intersectShape = new Rectangle(x1, y1 + dY, x2 - x1, y2
					- (y1 + dY));
		} else if ((dX > 0) && (dY == 0)) {
			intersectShape = new Rectangle(x1, y1, x2 - x1 + dX, y2 - y1);
		} else if ((dX < 0) && (dY == 0)) {
			intersectShape = new Rectangle(x1 + dX, y1, x2 - (x1 + dX),
					y2 - y1);
		} else {
			intersectShape = new Rectangle(x1, y1, x2 - x1, y2 - y1);
		}
		return intersectShape;

	}

}
