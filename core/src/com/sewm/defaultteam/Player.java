package com.sewm.defaultteam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by stefan on 22.04.2016.
 */
public class Player {

    Circle surrounding_;
    Color color;
    static int score_ = 0;
    String texture_;

    public float getHealth() {
        return health_;
    }

    float health_;


    public Player()
    {
        surrounding_ = new Circle(Constants.player_spawn_x,  Constants.player_spawn_y, Constants.player_radius);
        color = new Color(Color.LIGHT_GRAY);
        health_ = 3.f;
        texture_ = "images/player_new.png";
    }

    public Player(int health, String texture)
    {
        this();
        health_ = (float) health;
        texture_ = texture;
    }

    public void update(Vector2 new_pos){

        surrounding_.setPosition(new_pos);
    }

    public void decreaseHealth(float value){
        health_ -= value;
    }

    public String getTexture()
    {
        return texture_;
    }

    public Rectangle getRect(){
       return new Rectangle(surrounding_.x - surrounding_.radius,
                                       surrounding_.y - surrounding_.radius,
                                       surrounding_.radius * 2,
                                       surrounding_.radius * 2);
    }


}
