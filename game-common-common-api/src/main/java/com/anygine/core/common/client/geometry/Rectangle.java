package com.anygine.core.common.client.geometry;

import com.anygine.core.common.client.annotation.Embeddable;


// TODO: Make fields private and provide accessor methods
@Embeddable
public class Rectangle { // extends JsonWritableBase implements JsonWritable {
	public final float left, top, width, height;

	public Rectangle(
	    float left, 
	    float top, 
	    float width, 
	    float height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	/*
	public Rectangle(Object fields) {
	  super(fields);
	  left = (float) fields.getNumber("Left");
    top = (float) fields.getNumber("Top");
    width = (float) fields.getNumber("Width");
    height = (float) fields.getNumber("Height");
	}
*/
	
  // Calculates the signed depth of intersection between two rectangles.
	// <returns>
	// The amount of overlap between two intersecting rectangles. These
	// depth values can be negative depending on which wides the rectangles
	// intersect. This allows callers to determine the correct direction
	// to push objects in order to resolve collisions.
	// If the rectangles are not intersecting, Vector2.Zero is returned.
	// </returns>
	public static Vector2 GetIntersectionDepth(Rectangle rectA, Rectangle rectB) {
		// Calculate half sizes.
		float halfWidthA = rectA.width / 2.0f;
		float halfHeightA = rectA.height / 2.0f;
		float halfWidthB = rectB.width / 2.0f;
		float halfHeightB = rectB.height / 2.0f;

		// Calculate centers.
		Vector2 centerA = new Vector2(rectA.left + halfWidthA, rectA.top + halfHeightA);
		Vector2 centerB = new Vector2(rectB.left + halfWidthB, rectB.top + halfHeightB);

		// Calculate current and minimum-non-intersecting distances between centers.
		float distanceX = centerA.X - centerB.X;
		float distanceY = centerA.Y - centerB.Y;
		float minDistanceX = halfWidthA + halfWidthB;
		float minDistanceY = halfHeightA + halfHeightB;

		// If we are not intersecting at all, return (0, 0).
		if (Math.abs(distanceX) >= minDistanceX || Math.abs(distanceY) >= minDistanceY)
			return Vector2.Zero;

		// Calculate and return intersection depths.
		float depthX = distanceX > 0 ? minDistanceX - distanceX : -minDistanceX - distanceX;
		float depthY = distanceY > 0 ? minDistanceY - distanceY : -minDistanceY - distanceY;
		return new Vector2(depthX, depthY);
	}

	// Gets the position of the center of the bottom edge of the rectangle.
	public Vector2 GetBottomCenter() {
		return new Vector2(left + width / 2.0f, top + height);
	}

	public Vector2 getBottomRight() {
		return new Vector2(left + width, top + height);
	}

	public float Right() {
		return left + width;
	}

	public float Bottom() {
		return top + height;
	}

	public static Vector2 GetBottomCenter(Rectangle r) {
		return new Vector2(r.left + r.width / 2, r.top + r.height);
	}

	public Vector2 Center() {
		return new Vector2(left + width / 2, top + height / 2);
	}

	public boolean Contains(Vector2 v) {
		return
		(v.X >= left && v.X < Right()) &&
		(v.Y > top && v.Y <= Bottom()) ;
	}

	public boolean Intersects(Rectangle r) {
		return !(
				left > r.Right()  ||
				top > r.Bottom() ||
				Right() < r.left ||
				Bottom() < r.top
		);
	}

	public Vector2 getTopLeft() {
		return new Vector2(left, top);
	}

	public Vector2 getBottomLeft() {
		return new Vector2(left, top + height);
	}

	public Vector2 getTopRight() {
		return new Vector2(left + width, top);
	}

	@Override
	public String toString() {
	  return left + "," + top + "," + width + "," + height;
	}

	/*
  @Override
  public JsonType getJsonType() {
    return JsonType.Rectangle;
  }

  @Override
  public void update(Object jsonObj) {
    // Class is immutable - no update supported
    // TODO: Possibly throw exception or log warning
  }

  @Override
  protected void _writeJson(Writer writer) {
    writer.value("Left", left);
    writer.value("Top", top);
    writer.value("Width", width);
    writer.value("Height", height);
  }
  */
}
