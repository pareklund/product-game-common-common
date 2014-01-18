package com.anygine.core.common.client.geometry;


public class Triangle {

	public static enum Direction {
		NW, NE, SE, SW
	}

	private final Direction direction;
	private final Line hypotenuse;
	private final Vector2 normal;
	private final Vector2 v1;
	private final Vector2 v2;
	private final Vector2 v3;
	//	private final float deltaX, deltaY;
	private final float deltaYdivDeltaX;
	private final float minX, maxX, minY, maxY;
	private Rectangle boundingBox;

	public Triangle(Direction direction, Vector2 v1, Vector2 v2, Vector2 v3) {
		// TODO: Set direction based on supplied vectors instead
		this.direction = direction;
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		// TODO: Calculate, instead of assuming
		this.hypotenuse = new Line(v1, v2);
		this.normal = hypotenuse.getNormal();

		if (direction == Direction.NW) {
			deltaYdivDeltaX = Math.abs(v2.Y - v1.Y) / Math.abs(v2.X - v1.X);
			//			deltaX = Math.abs(v2.X - v1.X);
			//			deltaY = Math.abs(v2.Y - v1.Y);
		} else if (direction == Direction.NE) {
			deltaYdivDeltaX = Math.abs(v1.Y - v2.Y) / Math.abs(v1.X - v2.X);
			//			deltaX = Math.abs(v1.X - v2.X);
			//			deltaY = Math.abs(v1.Y - v2.Y);
		} else if (direction == Direction.SE) {
			deltaYdivDeltaX = Math.abs(v2.Y - v1.Y) / Math.abs(v2.X - v1.X);
			//			deltaX = Math.abs(v2.X - v1.X);
			//			deltaY = Math.abs(v2.Y - v1.Y);
		} else {
			deltaYdivDeltaX = Math.abs(v1.Y - v2.Y) / Math.abs(v1.X - v2.X);
			//			deltaX = Math.abs(v1.X - v2.X);
			//			deltaY = Math.abs(v1.Y - v2.Y);
		}

		if (v1.X > v2.X) {
			if (v1.X > v3.X) {
				maxX = v1.X;
				minX = (v2.X < v3.X ? v2.X : v3.X);
			} else {
				maxX = v3.X;
				minX = (v1.X < v2.X ? v1.X : v2.X);
			}
		} else if (v2.X > v3.X) {
			maxX = v2.X;
			minX = (v1.X < v3.X ? v1.X : v3.X);
		} else {
			maxX = v3.X;
			minX = (v1.X < v2.X ? v1.X : v2.X);
		}
		if (v1.Y > v2.Y) {
			if (v1.Y > v3.Y) {
				maxY = v1.Y;
				minY = (v2.Y < v3.Y ? v2.Y : v3.Y);
			} else {
				maxY = v3.Y;
				minY = (v1.Y < v2.Y ? v1.Y : v2.Y);
			}
		} else if (v2.Y > v3.Y) {
			maxY = v2.Y;
			minY = (v1.Y < v3.Y ? v1.Y : v3.Y);
		} else {
			maxY = v3.Y;
			minY = (v1.Y < v2.Y ? v1.Y : v2.Y);
		}
	}

	public boolean contains(Vector2 v) {
		// Check if inside bounding box first 
		if (v.X < minX || v.X > maxX || v.Y < minY || v.Y > maxY) {
			return false;
		}
		if (direction == Direction.NW) {
			float vDeltaX = Math.abs(v.X - v1.X);
			float vDeltaY = Math.abs(v.Y - v1.Y);
			//			float v2DeltaX = Math.abs(v2.X - v1.X);
			//			float v2DeltaY = Math.abs(v2.Y - v1.Y);
			//			return (vDeltaY / vDeltaX) < (v2DeltaY / v2DeltaX);
			//			return (vDeltaY / vDeltaX) < (deltaY / deltaX);
			return (vDeltaY / vDeltaX) < deltaYdivDeltaX;
		} else if (direction == Direction.NE) {
			float vDeltaX = Math.abs(v.X - v2.X);
			float vDeltaY = Math.abs(v.Y - v2.Y);
			//			float v1DeltaX = Math.abs(v1.X - v2.X);
			//			float v1DeltaY = Math.abs(v1.Y - v2.Y);
			//			return (vDeltaY / vDeltaX) < (v1DeltaY / v1DeltaX);
			//			return (vDeltaY / vDeltaX) < (deltaY / deltaX);
			return (vDeltaY / vDeltaX) < deltaYdivDeltaX;
		} else if (direction == Direction.SE) {
			float vDeltaX = Math.abs(v.X - v1.X);
			float vDeltaY = Math.abs(v.Y - v1.Y);
			//			float v2DeltaX = Math.abs(v2.X - v1.X);
			//			float v2DeltaY = Math.abs(v2.Y - v1.Y);
			//			return (vDeltaY / vDeltaX) < (v2DeltaY / v2DeltaX);
			//			return (vDeltaY / vDeltaX) < (deltaY / deltaX);
			return (vDeltaY / vDeltaX) < deltaYdivDeltaX;
		} else {
			float vDeltaX = Math.abs(v.X - v2.X);
			float vDeltaY = Math.abs(v.Y - v2.Y);
			//			float v1DeltaX = Math.abs(v1.X - v2.X);
			//			float v1DeltaY = Math.abs(v1.Y - v2.Y);
			//			return (vDeltaY / vDeltaX) < (v1DeltaY / v1DeltaX);
			//			return (vDeltaY / vDeltaX) < (deltaY / deltaX);
			return (vDeltaY / vDeltaX) < deltaYdivDeltaX;
		} 
	}

	public Line getHypotenuse() {
		return hypotenuse;
	}

	public boolean intersects(Rectangle bounds) {
		if (boundingBox == null) {
			boundingBox = new Rectangle(minX, minY, maxX-minX, maxY-minY);
		}
		if (!bounds.Intersects(boundingBox)) {
			return false;
		}
		if (direction == Direction.NW) {
			if ((bounds.top + bounds.height < maxY) && (bounds.left + bounds.width < maxX)) {
				float deltaX = Math.abs(bounds.left + bounds.width - minX);
				float deltaY = Math.abs(bounds.top + bounds.height - maxY);
				boolean result = ( deltaX / deltaY ) > ((maxX - minX) / (maxY - minY));
				return result;
			} else {
				return true;
			}
		} else if (direction == Direction.NE) {
			if ((bounds.top + bounds.height < maxY) && (bounds.left > minX)) {
				float deltaX = Math.abs(bounds.left - maxX);
				float deltaY = Math.abs(bounds.top + bounds.height - maxY);
				return ( deltaX / deltaY ) > ((maxX - minX) / (maxY - minY));
			} else {
				return true;
			}
		} else if (direction == Direction.SE) {
			if ((bounds.top > minY) && (bounds.left > minX)) {
				float deltaX = Math.abs(bounds.left - minX);
				float deltaY = Math.abs(bounds.top - maxY);
				return ( deltaX / deltaY ) < ((maxX - minX) / (maxY - minY));
			} else {
				return true;
			}
		} else if (direction == Direction.SW) {
			if ((bounds.top > minY) && (bounds.left + bounds.width < maxX)) {
				float deltaX = Math.abs(bounds.left + bounds.width - maxX);
				float deltaY = Math.abs(bounds.top - maxY);
				return ( deltaX / deltaY ) < ((maxX - minX) / (maxY - minY));
			} else {
				return true;
			}
		}
		throw new RuntimeException("Illegal direction value: " + direction);
	}
}
