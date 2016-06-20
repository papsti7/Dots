package com.sewm.defaultteam;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Admin on 15.06.2016.
 */
public class OverlapTesterTest {

    @Test
    public void testOverlapRectangles() throws Exception {
        Rectangle rect1 = new Rectangle(0, 0, 100, 100);
        Rectangle rect2 = new Rectangle(10, 10, 40, 40);
        assertTrue(OverlapTester.overlapRectangles(rect1, rect2));

        Rectangle rect3 = new Rectangle(0, 0, 100, 100);
        Rectangle rect4 = new Rectangle(101, 101, 140, 140);
        assertFalse(OverlapTester.overlapRectangles(rect3, rect4));

        Rectangle rect5 = new Rectangle(0, 0, 100, 100);
        Rectangle rect6 = new Rectangle(90, 10, 140, 140);
        assertTrue(OverlapTester.overlapRectangles(rect5, rect6));
    }

    @Test
    public void testPointInRectangle() throws Exception {
        Rectangle rect1 = new Rectangle(0, 0, 100, 100);
        Vector2 point1 = new Vector2(10, 10);
        assertTrue(OverlapTester.pointInRectangle(rect1, point1));

        Vector2 point2 = new Vector2(100, 100);
        assertTrue(OverlapTester.pointInRectangle(rect1, point2));

        Vector2 point3 = new Vector2(120, 120);
        assertFalse(OverlapTester.pointInRectangle(rect1, point3));

    }

    @Test
    public void testPointInRectangle1() throws Exception {
        Rectangle rect1 = new Rectangle(0, 0, 100, 100);
        Vector2 point1 = new Vector2(10, 10);
        assertTrue(OverlapTester.pointInRectangle(rect1, point1.x, point1.y));

        Vector2 point2 = new Vector2(100, 100);
        assertTrue(OverlapTester.pointInRectangle(rect1, point2.x, point2.y));

        Vector2 point3 = new Vector2(120, 120);
        assertFalse(OverlapTester.pointInRectangle(rect1, point3.x, point3.y));
    }
}