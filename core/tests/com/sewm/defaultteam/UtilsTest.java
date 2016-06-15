package com.sewm.defaultteam;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Lisa on 15.06.2016.
 */
public class UtilsTest {

    @Test
    public void testGetUnitVector() throws Exception {
        for (int x = 1; x < 100; x++)
            for (int y = 1; y < 100; y++) {
                Vector2 vector = new Vector2(x, y);
                assertTrue(Math.abs(Utils.getUnitVector(vector).len() - 1) < 0.000005f);
            }
    }

    @Test
    public void testGetOriginOfRect() throws Exception {
        Vector2 circle_center = new Vector2(300,300);
        Circle circle = new Circle(300,300,10);
        int radius = 10;
        Vector2 return_value = Utils.getOriginOfRect(circle_center,circle);
        assertTrue(return_value.x == 290 && return_value.y == 290);
    }

}