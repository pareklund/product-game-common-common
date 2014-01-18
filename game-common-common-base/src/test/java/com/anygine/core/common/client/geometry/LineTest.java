package com.anygine.core.common.client.geometry;

import org.junit.Assert;
import org.junit.Test;

public class LineTest {

	@Test
	public void testContains() {
		Line line = new Line(new Vector2(0, 0), new Vector2(10, 10));
		Assert.assertTrue(line.contains(new Vector2(5, 5)));
	}
	
	@Test
	public void testGetNormal() {
		Line line = new Line(new Vector2(0, 0), new Vector2(10, 10));
		System.out.println("Normal: " + line.getNormal());
		line = new Line(new Vector2(0, 10), new Vector2(10, 0));
		System.out.println("Normal: " + line.getNormal());
		line = new Line(new Vector2(10, 10), new Vector2(0, 0));
		System.out.println("Normal: " + line.getNormal());
		line = new Line(new Vector2(10, 0), new Vector2(0, 10));
		System.out.println("Normal: " + line.getNormal());
	}
	
	@Test
	public void testNormalize() {
		Line line = new Line(new Vector2(0, 0), new Vector2(10, 10));
		System.out.println("Normal: " + line.normalize());
		line = new Line(new Vector2(0, 10), new Vector2(10, 0));
		System.out.println("Normal: " + line.normalize());
		line = new Line(new Vector2(10, 10), new Vector2(0, 0));
		System.out.println("Normal: " + line.normalize());
		line = new Line(new Vector2(10, 0), new Vector2(0, 10));
		System.out.println("Normal: " + line.normalize());
	}
	
	@Test
	public void testIntersection() {
		Line line = new Line(new Vector2(0, 0), new Vector2(10, 10));
		intersect(line);
		line = new Line(new Vector2(0, 5), new Vector2(10, 5));
		intersect(line);
	}
	
	private void intersect(Line line) {
		System.out.println();
		System.out.println(line.getIntersection(
				new Line(new Vector2(0, 10), new Vector2(10, 0))));
		System.out.println(line.getIntersection(
				new Line(new Vector2(2, 0), new Vector2(2, 10))));
		System.out.println(line.getIntersection(
				new Line(new Vector2(0, 7), new Vector2(10, 7))));
		System.out.println(line.getIntersection(
				new Line(new Vector2(1, 1), new Vector2(9, 9))));
		System.out.println(line.getIntersection(
				new Line(new Vector2(9, 0), new Vector2(2, 9))));
		System.out.println(line.getIntersection(
				new Line(new Vector2(8, 3), new Vector2(1, 9))));
		System.out.println(line.getIntersection(
				new Line(new Vector2(1, 3), new Vector2(9, 7))));
		System.out.println(line.getIntersection(
				new Line(new Vector2(7, 10), new Vector2(9.5f, 8))));
		
	}
}
