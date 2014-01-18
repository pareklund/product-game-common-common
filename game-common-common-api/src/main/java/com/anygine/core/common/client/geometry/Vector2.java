package com.anygine.core.common.client.geometry;

import com.anygine.core.common.client.annotation.Embeddable;

// TODO: Use this class consistently as immutable
// TODO: Make fields private and use private accessors
@Embeddable
public class Vector2 {

	public static final Vector2 Zero = new Vector2(0, 0);

	public float X;
	public float Y;

	public Vector2(float X, float Y) {
		this.X = X;
		this.Y = Y;
	}

	public Vector2() {}

/*	
	public Vector2(Json.Object fields) {
    X = (float) fields.getNumber("X");
    Y = (float) fields.getNumber("Y");
  }
*/
	
  public Vector2 sub(Vector2 v) {
		return new Vector2(X - v.X, Y - v.Y);
	}

	public float LengthSquared() {
		return X * X + Y * Y;
	}

	public Vector2 mul(float a) {
		return new Vector2(X * a, Y * a);
	}

	public Vector2 add(Vector2 v) {
		return new Vector2(X + v.X, Y + v.Y);
	}

	public Vector2 mul(Vector2 v) {
		return new Vector2(X * v.X, Y * v.Y);
	}

	@Override
	public String toString() {
		return "(" + X + ", " + Y + ")";
	}

	public Vector2 round() {
		return new Vector2(Math.round(X), Math.round(Y));
	}

	public float dotProduct(Vector2 v2) {
		return X * v2.Y + Y * v2.X;
	}

	public float length() {
		return (float) Math.sqrt(X * X + Y * Y);
	}

	public Vector2 normalize() {
		float length = (float) Math.sqrt((Math.pow(X, 2) + Math.pow(Y, 2))); 
		return new Vector2(X / length, Y / length);
	}

	public Vector2 negate() {
		return new Vector2(-X, -Y);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Vector2)) { 
			return false;
		}
		Vector2 v = (Vector2) o;
		return X == v.X && Y == v.Y;
	}
	
/*	
  @Override
  public JsonType getJsonType() {
    return JsonType.Vector2;
  }

  @Override
  public void update(Json.Object jsonObj) {
    X = update(X, jsonObj, "X");
    Y = update(Y, jsonObj, "Y");
  }

  @Override
  protected void _writeJson(Writer writer) {
    writer.value("X", X);
    writer.value("Y", Y);
  }
*/
}