package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by stefan on 22.04.2016.
 */
public class Enemy {

    Rectangle body_;
    int speed_;
    int lives_;
    Color color_;
    Vector2 target_pos_;

    public Enemy (){
        body_ = new Rectangle(20.f, 50.f, 20.f ,20.f);
        speed_ = 1;
        lives_ = 3;
        color_ = new Color(Color.BLUE);
        target_pos_ = new Vector2(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
    }

    public Enemy ( Vector2 pos, int speed, int lives){
        body_ = new Rectangle(pos.x, pos.y, 20.f ,20.f);
        speed_ = speed;
        lives_ = lives;
        color_ = new Color(Color.GREEN);
        target_pos_ = new Vector2(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
    }

    public void update(Vector2 target_pos){

        updateTarget(target_pos);
        updatePosition();
    }

    private void updateTarget(Vector2 target_pos){
        target_pos_.x = target_pos.x;
        target_pos_.y = target_pos.y;
    }

    private void updatePosition(){
        Vector2 distance = body_.getPosition(new Vector2());
        distance.x = target_pos_.x - distance.x;
        distance.y = target_pos_.y - distance.y;
        distance.x = distance.x * (0.005f * speed_);
        distance.y = distance.y * (0.005f * speed_);
        Vector2 new_pos = body_.getPosition(new Vector2());

        new_pos.x += distance.x;
        new_pos.y += distance.y;
        body_.setPosition(new_pos);
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
