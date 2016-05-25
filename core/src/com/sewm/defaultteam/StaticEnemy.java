package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Admin on 25.05.2016.
 */
public class StaticEnemy extends Enemy {

    private Vector2 start_pos_;
    private Vector2 end_pos_;
    Rectangle start_rect_;
    Rectangle end_rect_;

    public StaticEnemy(Vector2 pos, EnemyAttribute difficulty, int points, int points_on_death, Vector2 start_pos, Vector2 end_pos){
        texture_ = null;
        texture_array_ = new ArrayList<String>();
        body_ = new Rectangle(pos.x, pos.y, Constants.enemy_width, Constants.enemy_height);
        speed_base_ = difficulty.speed_base_;
        health_ = difficulty.health_;
        color_ = new Color(Color.RED);
        velocity_ = new Vector2(0,0);
        inertia_ = difficulty.inertia_;
        difficulty_ = difficulty.difficulty_;
        points_ = points;
        points_on_death_ = points_on_death;
        start_pos_ = start_pos;
        end_pos_ = end_pos;
        start_rect_ = new Rectangle(start_pos_.x, start_pos_.y, Constants.player_radius * 2.f, Constants.player_radius * 2.f);
        end_rect_ = new Rectangle(end_pos_.x, end_pos_.y, Constants.player_radius * 2.f, Constants.player_radius * 2.f);
        target_pos_ = start_pos;
    }

    public StaticEnemy(EnemyAttribute difficulty, int points, int points_on_death, ArrayList<String> textures, Vector2 start_pos, Vector2 end_pos)
    {
        this(new Vector2(start_pos.x, start_pos.y), difficulty, points, points_on_death, start_pos, end_pos);
        texture_array_ = new ArrayList<String>(textures);
        texture_ = texture_array_.get(2);
    }


    @Override
    protected void updateTarget(Vector2 target_pos){
        Rectangle body = (Rectangle) body_;
        if(OverlapTester.overlapRectangles(body, start_rect_))
        {
            target_pos_ = end_pos_;
        }
        else if(OverlapTester.overlapRectangles(body, end_rect_))
        {
            target_pos_ = start_pos_;
        }
        if (body.contains(target_pos))//target pos is player pos
        {
            onContact();
        }
    }
}
