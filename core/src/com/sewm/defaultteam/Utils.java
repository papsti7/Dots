package com.sewm.defaultteam;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lisa on 27.04.2016.
 */
public class Utils {
    public static Vector2 getUnitVector(Vector2 vec){
        float length = vec.len();
        vec.x /= length;
        vec.y /= length;
        return vec;
    }

    public static Vector2 getOriginOfRect(Vector2 center_pos, Circle surrounding){
        Vector2 origin_pos = new Vector2(center_pos.x - surrounding.radius, center_pos.y - surrounding.radius);
        //System.out.println("new pos: " + origin_pos.x + "|" + origin_pos.y);
        return origin_pos;
    }

}
