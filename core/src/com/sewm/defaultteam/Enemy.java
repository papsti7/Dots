package com.sewm.defaultteam;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.math.Rectangle;


/**
 * Created by stefan on 22.04.2016.
 */
public class Enemy {

    Rectangle body_;
    int speed_;
    int lives_;
    Color color_;

    public Enemy (){
        body_ = new Rectangle(20.f, 50.f, 20.f ,20.f);
        speed_ = 1;
        lives_ = 3;
        color_ = new Color(Color.BLUE);
    }

    public void setLives_(int lives_) {
        this.lives_ = lives_;
    }

    public int getLives_() {
        return lives_;
    }

    public void setColor_(Color color_) {
        this.color_ = color_;
    }
}
