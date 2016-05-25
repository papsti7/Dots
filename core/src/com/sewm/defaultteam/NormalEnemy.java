package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Admin on 25.05.2016.
 */
public class NormalEnemy extends Enemy{

    public NormalEnemy(Vector2 pos, EnemyAttribute difficulty, int points, int points_on_death){
        texture_ = null;
        texture_array_ = new ArrayList<String>();
        body_ = new Rectangle(pos.x, pos.y, Constants.enemy_width, Constants.enemy_height);
        speed_base_ = difficulty.speed_base_;
        health_ = difficulty.health_;
        color_ = new Color(Color.RED);
        target_pos_ = new Vector2(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
        velocity_ = new Vector2(0,0);
        inertia_ = difficulty.inertia_;
        difficulty_ = difficulty.difficulty_;
        points_ = points;
        points_on_death_ = points_on_death;
    }

    public NormalEnemy(int x, int y, EnemyAttribute difficulty, int points, int points_on_death, ArrayList<String> textures, int spawn_time)
    {
        this(new Vector2(x, y), difficulty, points, points_on_death);
        spawn_time_ = spawn_time;
        texture_array_ = new ArrayList<String>(textures);
        texture_ = texture_array_.get(2);
    }

    @Override
    protected void updateTarget(Vector2 target_pos){
        target_pos_.x = target_pos.x;
        target_pos_.y = target_pos.y;
        Rectangle body = (Rectangle) body_;
        if (body.contains(target_pos))
        {
            onContact();
        }
    }

}
