package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by stefan on 22.04.2016.
 */
public class Enemy extends GameEntity {

    public static enum Difficulty{
        easy , medium, hard
    }



    private int difficulty_;
    public Enemy (){
        body_ = new Rectangle(20.f, 50.f, 20.f ,20.f);
        speed_base_ = 1;
        health_ = 3.f;
        color_ = new Color(Color.GREEN);
        target_pos_ = new Vector2(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
        velocity_ = new Vector2(0,0);
        inertia_ = 1;
        texture_ = "images/enemy.png";
        difficulty_ = 1;
        points_ = 1;
        points_on_death_ = 3;
    }

    public Enemy ( Vector2 pos, int speed, int lives, int inertia, int difficulty){
        body_ = new Rectangle(pos.x, pos.y, 20.f ,20.f);
        speed_base_ = speed;
        health_ = lives;
        color_ = new Color(Color.GREEN);
        target_pos_ = new Vector2(Gdx.graphics.getWidth() / 2.f, Gdx.graphics.getHeight() / 2.f);
        velocity_ = new Vector2(0,0);
        inertia_ = inertia;
        texture_ = "images/enemy.png";
        difficulty_ = difficulty;
    }

    public Enemy(Vector2 pos, EnemyAttribute difficulty, int points, int points_on_death){
        texture_ = "images/enemy.png";
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

    public Enemy(int x, int y, EnemyAttribute difficulty, int points, int points_on_death, String texture)
    {
        this(new Vector2(x, y), difficulty, points, points_on_death);
        texture_ = texture;
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
    @Override
    public void updatePosition(){
       Rectangle rect_body = (Rectangle)body_;
        Vector2 direction = rect_body.getPosition(new Vector2());
        //get direction to target(player)
        direction.x = target_pos_.x - direction.x;
        direction.y = target_pos_.y - direction.y;
        //calculate unit vector to just get the direction with length 1
        direction = Utils.getUnitVector(direction);
        //set length from diretion to speed
        direction.x *= (speed_base_ * 5) * Gdx.graphics.getDeltaTime();
        direction.y *= (speed_base_ * 5) * Gdx.graphics.getDeltaTime();
        //modify old velocity
        velocity_.x += (direction.x * 1.5f);
        velocity_.y += (direction.y * 1.5f);
        if(velocity_.len() > (2.f * inertia_))
        {
            velocity_ = Utils.getUnitVector(velocity_);
            velocity_.x *= 1.99f * inertia_;
            velocity_.y *= 1.99f * inertia_;
        }
        //create new pos for enemy
        Vector2 new_pos = rect_body.getPosition(new Vector2());
        

        new_pos.x += velocity_.x;
        new_pos.y += velocity_.y;

        rect_body.setPosition(new_pos);
    }

    @Override
    protected void onContact() {
        if(!StartPoint.immortal)
        {
            World.player_.decreaseHealth(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        Rectangle enemy_body = (Rectangle) body_;
        spriteBatch.draw(WorldRenderer.entities_textures.get(texture_), enemy_body.getX(), enemy_body.y);
    }

    @Override
    public void drawDebug(ShapeRenderer debugRenderer) {
        Rectangle enemy_body = (Rectangle) body_;
        Rectangle rect = new Rectangle(enemy_body.getX(), enemy_body.getY(), WorldRenderer.entities_textures.get(texture_).getWidth(), WorldRenderer.entities_textures.get(texture_).getHeight());
        if(this.difficulty_ == 1)
            debugRenderer.setColor(new Color(Color.BLUE));
        else if(this.difficulty_ == 2)
            debugRenderer.setColor(new Color(Color.GREEN));
        else if(this.difficulty_ == 3)
            debugRenderer.setColor(new Color(Color.RED));
        debugRenderer.rect(rect.getX(), rect.getY(), rect.width, rect.height);
    }

    @Override
    public void kill() {

    }


}
