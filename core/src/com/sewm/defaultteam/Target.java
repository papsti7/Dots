package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Color;


/**
 * Created by Lisa on 27.04.2016.
 */
public class Target extends GameEntity {

    public Target()
    {
        body_ = new Circle(50.f,50.f,10);
        speed_base_ = 1;
        health_ = 3;
        color_ = new Color(Color.ORANGE);
        target_pos_ = new Vector2(0,0);
        velocity_ = new Vector2(0,0);
        inertia_ = 0;
        texture_ = "images/target.png";
    }

    public Target(int x, int y, int radius, int health, String texture)
    {
        this();
        body_ = new Circle(new Float(x), new Float(y), new Float(radius));
        health_ = health;
        texture_ = texture;
    }

    public Target(Shape2D body, int speed_base, int lives, Vector2 velocity, int inertia)
    {
        body_ = body;
        speed_base_ = speed_base;
        health_ = lives;
        color_ = new Color(Color.ORANGE);
        target_pos_ = new Vector2(0,0);
        velocity_ = velocity;
        inertia_ = inertia;
        texture_ = "images/target.png";
    }

    @Override
    protected void updateTarget(Vector2 target_pos) {
        Circle body = (Circle) body_;
        if (body.contains(target_pos))
        {
            onContact();
        }
    }

    @Override
    protected void updatePosition() {

    }

    @Override
    protected void onContact() {
        float value = Gdx.graphics.getDeltaTime();

        if(value > health_left_){
            health_ -= 1.f;

            if(health_ <= 0.f){
                setAlive_(false);
                Player.score_ += 1;
            }
            else{
                health_left_ = 1.f;
                Vector2 new_pos = new Vector2(Utils.random_.nextInt(Gdx.graphics.getWidth()), Utils.random_.nextInt(Gdx.graphics.getHeight()));
                ((Circle)body_).setPosition(new_pos);
            }
        }
        else{
            health_left_ -= value;
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        Circle circle = (Circle) body_;
        spriteBatch.draw(WorldRenderer.entities_textures.get(texture_),circle.x,circle.y);
    }

    @Override
    public void drawDebug(ShapeRenderer debugRenderer) {
        Circle body = (Circle) body_;
        Texture texture = WorldRenderer.entities_textures.get(texture_);
        Circle circle = new Circle(body.x + texture.getWidth() / 2, body.y +texture.getHeight()/2, texture.getWidth()/2 + 10.f);
        debugRenderer.setColor(new Color(Color.BROWN));
        debugRenderer.circle(circle.x, circle.y,circle.radius);
    }

    public void decreaseHealth(float value){
        if(health_ > value)
            health_ -= value;
        else
            setAlive_(false);
    }

    public Rectangle getRect(){
        Vector2 pos = Utils.getOriginOfRect(new Vector2(((Circle)body_).x, ((Circle)body_).y), ((Circle)body_));
        Rectangle rect = new Rectangle(pos.x, pos.y, ((Circle)body_).radius * 2.f, ((Circle)body_).radius * 2.f);
        return rect;
    }

}
