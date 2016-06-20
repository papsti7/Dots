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
public abstract class Enemy extends GameEntity {

    public static enum Difficulty{
        easy , medium, hard
    }

    public int spawn_time_;
    protected int difficulty_;

    public int getDifficulty() {
        return difficulty_;
    }

    public int getSpawnTime() {
        return spawn_time_;
    }

    @Override
    public void updatePosition(){
       Rectangle rect_body = (Rectangle)body_;
       Vector2 direction = rect_body.getPosition(new Vector2());
        //get direction to target(player)
        if (Utils.random_.nextDouble() > 1.97)
        {
            direction.x = Utils.random_.nextFloat() - direction.x;
            direction.y = Utils.random_.nextFloat() - direction.y;
        }
        else
        {
            direction.x = target_pos_.x - direction.x;
            direction.y = target_pos_.y - direction.y;
        }

        //calculate unit vector to just get the direction with length 1
        direction = Utils.getUnitVector(direction);
        //System.out.println("direction :" + direction.x + ", " + direction.y);
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
        if(!Constants.immortal)
        {
            World.player_.decreaseHealth(Gdx.graphics.getDeltaTime() * Constants.enemy_damage);
            WorldRenderer.enemy_contact_ = 3;
        }

    }

    @Override
    public void decreaseHealth(float value) {

        if (value < health_)
        {
            float health_old = health_;
            health_-= value;

            if (Math.ceil(health_old) > Math.ceil(health_))
            {
                GameScreen.worldController_.updateScore(points_);
                //Todo: update Texture
                texture_ = texture_array_.get((int)Math.ceil(health_) - 1);
            }

        }
        else
        {
            kill();
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        Rectangle enemy_body = (Rectangle) body_;
        Texture texture = WorldRenderer.entities_textures.get(texture_);
        spriteBatch.draw(texture, enemy_body.getX() - texture.getWidth() / 2.f, enemy_body.y - texture.getHeight() / 2.f);
    }

    @Override
    public void drawDebug(ShapeRenderer debugRenderer) {
        Rectangle enemy_body = (Rectangle) body_;
        Texture texture = WorldRenderer.entities_textures.get(texture_);
        Rectangle rect = new Rectangle(enemy_body.getX() - texture.getWidth() / 2.f, enemy_body.getY() - texture.getHeight() / 2.f, texture.getWidth(), texture.getHeight());
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
        health_ = 0;
        alive_ = false;
    }

    @Override
    public void onDeath(WorldController worldController)
    {
        System.out.println("points on Death : " + points_on_death_);
        worldController.updateScore(points_on_death_);
    }


}
