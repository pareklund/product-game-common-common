package com.anygine.core.common.client.geometry;

import junit.framework.Assert;

import org.junit.Test;

import com.anygine.core.common.client.geometry.Triangle.Direction;

public class TriangleTest {

	@Test
	public void testIntersects() {
		Triangle triNW = new Triangle(
				Direction.NW, 
				new Vector2(0, 10),
				new Vector2(10, 0),
				new Vector2(10, 10));
		Rectangle rect = new Rectangle(0, 0, 10, 10);
		Assert.assertTrue(triNW.intersects(rect));
		rect = new Rectangle(2.5f, 2.5f, 2.5f, 2.5f);
		Assert.assertFalse(triNW.intersects(rect));
		rect = new Rectangle(2.5f, 2.5f, 5.0f, 5.0f);
		Assert.assertTrue(triNW.intersects(rect));
		rect = new Rectangle(2.5f, 2.5f, 2.5f, 2.5f);
		Assert.assertFalse(triNW.intersects(rect));
		rect = new Rectangle(7, 1, 1, 1);
		Assert.assertFalse(triNW.intersects(rect));
		rect = new Rectangle(7, 1.01f, 1, 1);
		Assert.assertTrue(triNW.intersects(rect));
		rect = new Rectangle(7, 2, 1, 1);
		Assert.assertTrue(triNW.intersects(rect));
	}
	
	@Test
	public void testContains() {
		
		/*
		int numIter = 10000000;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < numIter; i++) {
*/
		Triangle triNW = new Triangle(
				Direction.NW, 
				new Vector2(0, 10),
				new Vector2(10, 0),
				new Vector2(10, 10));
		Assert.assertTrue(triNW.contains(new Vector2(1, 9.5f)));
		Assert.assertFalse(triNW.contains(new Vector2(2.34f, 2.34f)));

		Triangle triNE = new Triangle(
					Direction.NE, 
					new Vector2(0, 0),
					new Vector2(10, 10),
					new Vector2(0, 10));
			Assert.assertFalse(triNE.contains(new Vector2(2.0f, 2.0f)));
			Assert.assertFalse(triNE.contains(new Vector2(7.0f, 7.0f)));
			Assert.assertFalse(triNE.contains(new Vector2(5.0f, 5.0f)));
			Assert.assertTrue(triNE.contains(new Vector2(5.0f, 5.01f)));
			Assert.assertFalse(triNE.contains(new Vector2(8.0f, 8.0f)));
			Assert.assertTrue(triNE.contains(new Vector2(8.0f, 8.01f)));

			Triangle triSE = new Triangle(
					Direction.SE, 
					new Vector2(10, 0),
					new Vector2(0, 10),
					new Vector2(0, 0));
			Assert.assertTrue(triSE.contains(new Vector2(2.34f, 2.35f)));
			Assert.assertFalse(triSE.contains(new Vector2(2.34f, 7.66f)));
			Assert.assertFalse(triSE.contains(new Vector2(8.0f, 2.0f)));
			Assert.assertTrue(triSE.contains(new Vector2(8.0f, 1.99f)));
			Assert.assertFalse(triSE.contains(new Vector2(1.0f, 9.5f)));
			Assert.assertFalse(triSE.contains(new Vector2(1.0f, 9.0f)));
			Assert.assertTrue(triSE.contains(new Vector2(1.0f, 8.9f)));

			Triangle triSW = new Triangle(
					Direction.SW, 
					new Vector2(10, 10),
					new Vector2(0, 0),
					new Vector2(10, 0));
			Assert.assertTrue(triSW.contains(new Vector2(2.0f, 1.99f)));
			Assert.assertFalse(triSW.contains(new Vector2(2.0f, 2.01f)));
			Assert.assertFalse(triSW.contains(new Vector2(2.0f, 2.0f)));
			Assert.assertFalse(triSW.contains(new Vector2(8.0f, 8.0f)));
			Assert.assertTrue(triSW.contains(new Vector2(8.0f, 7.99f)));
			Assert.assertFalse(triSW.contains(new Vector2(5.0f, 5.0f)));
			Assert.assertTrue(triSW.contains(new Vector2(5.0f, 4.99f)));
			Assert.assertTrue(triSW.contains(new Vector2(9.0f, 1.0f)));
			Assert.assertTrue(triSW.contains(new Vector2(9.0f, 8.99f)));
			Assert.assertFalse(triSW.contains(new Vector2(9.01f, 9.02f)));
/*
	}
		System.out.println(
				numIter 
				+ " iterations took " 
				+ (System.currentTimeMillis() - startTime)
				+ "ms");
				*/
	}
}
