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

    public float getHealth_() {
        return health_;
    }

    float health_;

    static boolean get_damaged_;

    public Player(){
        surrounding_ = new Circle(Gdx.graphics.getWidth()/2.f, Gdx.graphics.getHeight()/2.f, 50.f);
        color = new Color(Color.LIGHT_GRAY);
        health_ = 3.f;
        get_damaged_ = false;
    }

    public void update(Vector2 new_pos){

        surrounding_.setPosition(Utils.getOriginOfRect(new_pos, surrounding_));

    }

    public void decreaseHealth(float value){
        health_ -= value;
    }

    public Rectangle getRect(){
        Rectangle rect = new Rectangle(surrounding_.x - surrounding_.radius,
                                       surrounding_.y - surrounding_.radius,
                                       surrounding_.radius * 2,
                                       surrounding_.radius * 2);
        return rect;
    }


}
