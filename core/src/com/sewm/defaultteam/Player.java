package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.FileNotFoundException;

/**
 * Created by stefan on 22.04.2016.
 */
public class Player {

    Circle surrounding_;
    Color color;
    static int score_ = 0;
    String texture_;

    public float getHealth_() {
        return health_;
    }

    float health_;

    static boolean get_damaged_;


    public Player()
    {
        surrounding_ = new Circle((Gdx.graphics.getWidth()/2.f) + Gdx.graphics.getWidth() / 40.f , Gdx.graphics.getHeight()/2.f + Gdx.graphics.getWidth() / 40.f, Gdx.graphics.getWidth() / 40f);
        color = new Color(Color.LIGHT_GRAY);
        health_ = 3.f;
        get_damaged_ = false;
        texture_ = "images/player_new.png";
    }

    public Player(int health, String texture)
    {
        this();
        health_ = new Float(health);
        texture_ = texture;
    }

    public void update(Vector2 new_pos){

        surrounding_.setPosition(Utils.getOriginOfRect(new_pos, surrounding_));
        System.out.println(this.surrounding_.x + " " + this.surrounding_.y);

    }

    public void decreaseHealth(float value){
        health_ -= value;
    }

    public String getTexture()
    {
        return texture_;
    }

    public Rectangle getRect(){
        Rectangle rect = new Rectangle(surrounding_.x - surrounding_.radius,
                                       surrounding_.y - surrounding_.radius,
                                       surrounding_.radius * 2,
                                       surrounding_.radius * 2);
        return rect;
    }


}
