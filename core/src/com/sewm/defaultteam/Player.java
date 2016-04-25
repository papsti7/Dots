package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by stefan on 22.04.2016.
 */
public class Player {
    Circle surrounding_;
    Color color;
    public Player(){
        surrounding_ = new Circle(Gdx.graphics.getWidth()/2.f, Gdx.graphics.getHeight()/2.f, 50.f);
        color = new Color(Color.YELLOW);
    }

    public void update(Vector2 new_pos){
        System.out.println("old pos: " + surrounding_.x + "|" + surrounding_.y);
        surrounding_.setPosition(getOriginOfRect(new_pos));
        System.out.println("updated pos: " + surrounding_.x + "|" + surrounding_.y);
        System.out.println(" ");
    }

    public Rectangle getRect(){
        Rectangle rect = new Rectangle(surrounding_.x,
                                       surrounding_.y,
                                       surrounding_.radius * 2,
                                       surrounding_.radius * 2);
        return rect;
    }

    Vector2 getOriginOfRect(Vector2 center_pos){
        Vector2 origin_pos = new Vector2(center_pos.x - surrounding_.radius, center_pos.y - surrounding_.radius);
        System.out.println("new pos: " + origin_pos.x + "|" + origin_pos.y);
        return origin_pos;
    }
}
