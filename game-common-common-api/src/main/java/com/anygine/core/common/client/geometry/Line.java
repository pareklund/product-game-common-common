package com.anygine.core.common.client.geometry;

public class Line {

	public final Vector2 v1;
	public final Vector2 v2;

	private final float minX, maxX, minY, maxY;

	private Vector2 normal;
	private Vector2 normalized;

	public Line(Vector2 v1, Vector2 v2) {
		this.v1 = v1;
		this.v2 = v2;
		if (v1.X < v2.X) {
			minX = v1.X;
			maxX = v2.X;
		} else {
			minX = v2.X;
			maxX = v1.X;			
		}
		if (v1.Y < v2.Y) {
			minY = v1.Y;
			maxY = v2.Y;
		} else {
			minY = v2.Y;
			maxY = v1.Y;			
		}
	}

	public Vector2 getIntersection(Line l2) {
		return getIntersection(l2, false);
	}

	public Vector2 getIntersection(Line l2, boolean extrapolate) {
		float d = (v1.X - v2.X) * (l2.v1.Y - l2.v2.Y) 
		- (v1.Y - v2.Y) * (l2.v1.X - l2.v2.X);
		if (d == 0) {
			return null;
		}
		float xi = ( 
				(l2.v1.X - l2.v2.X) * (v1.X * v2.Y - v1.Y * v2.X) 
				- (v1.X - v2.X) * (l2.v1.X * l2.v2.Y - l2.v1.Y * l2.v2.X) ) 
				/ d;
		float yi = (
				(l2.v1.Y - l2.v2.Y) * (v1.X * v2.Y - v1.Y * v2.X)
				- (v1.Y - v2.Y) * (l2.v1.X * l2.v2.Y - l2.v1.Y * l2.v2.X))
				/ d;
		if (!extrapolate) {
			if (xi < minX || xi > maxX || yi < minY || yi > maxY) {
				return null;
			}
		}
		return new Vector2(xi, yi);
	}

	@Override
	public String toString() {
		return "(" + v1.X + ", " + v1.Y + ")-(" + v2.X + ", " + v2.Y + ")";
	}

	public Vector2 getNormal() {
		if (normal == null) {
			float dx = v2.X - v1.X;
			float dy = v2.Y - v1.Y;
			float length = (float) Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2))); 
			normal = new Vector2(-dy / length, dx / length);
		}
		return normal;
	}

	public Vector2 normalize() {
		if (normalized == null) {
			float dx = v2.X - v1.X;
			float dy = v2.Y - v1.Y;
			float length = (float) Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2))); 
			normalized = new Vector2(dx / length, dy / length);
		}
		return normalized;
	}

	public boolean contains(Vector2 point) {
		if (point.X < minX || point.X > maxX || point.Y < minY || point.Y > maxY) {
			return false;
		}
		return (Math.abs(v2.X - v1.X) / Math.abs(v2.Y - v1.Y)) == 
			(Math.abs(point.X - v1.X) / Math.abs(point.Y - v1.Y));
	}
}
