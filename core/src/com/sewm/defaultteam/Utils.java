package com.sewm.defaultteam;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Lisa on 27.04.2016.
 */
public class Utils {
    public static final Random random_ = new Random(System.currentTimeMillis());
    public static Vector2 getUnitVector(Vector2 vec){
        float length = vec.len();
        vec.x /= length;
        vec.y /= length;
        return vec;
    }

    public static Vector2 getOriginOfRect(Vector2 center_pos, Circle surrounding){
        return new Vector2(center_pos.x - surrounding.radius, center_pos.y - surrounding.radius);

    }




}
