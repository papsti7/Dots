package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;


/**
 * Created by Lisa on 27.04.2016.
 */
public class Target extends GameEntity {

    public Target()
    {
        body_ = new Circle(50.f,50.f,Constants.target_radius);
        speed_base_ = 1;
        health_ = 3;
        color_ = new Color(Color.ORANGE);
        target_pos_ = new Vector2(0,0);
        velocity_ = new Vector2(0,0);
        inertia_ = 0;
        texture_ = null;
        texture_array_ = new ArrayList<String>();
        points_ = 1;
        points_on_death_ = 3;
    }

    public Target(int x, int y, int radius, int health, ArrayList<String> textures) {
        this();
        //radius not used yet
        body_ = new Circle((float) x, (float) y, Gdx.graphics.getWidth() / 80.f);
        health_ = health;
        texture_array_ = new ArrayList<String>(textures);
        texture_ = texture_array_.get(2);
    }

    @Override
    protected void updateTarget(Vector2 target_pos) {
        Circle body = new Circle((Circle) body_);
        body.radius *= 4f;
        //System.out.println("Radius is: " + body.radius);
        if (body.contains(target_pos))
        {
            onContact();
        }
    }

    @Override
    protected void updatePosition() {

    }

    private void jump()
    {
        WorldRenderer.target_contact_ = 7;

        Vector2 new_pos = new Vector2(Utils.random_.nextInt(Gdx.graphics.getWidth()-64), Utils.random_.nextInt(Gdx.graphics.getHeight()-64));
        new_pos.add(32,32);
        ((Circle)body_).setPosition(new_pos);
    }

    @Override
    protected void onContact() {
        float value = Gdx.graphics.getDeltaTime();
        System.out.println("Target contact");

        decreaseHealth(value);

        System.out.println("Health = " + health_);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        Circle circle = (Circle) body_;
        Texture texture = WorldRenderer.entities_textures.get(texture_);
        spriteBatch.draw(texture,circle.x - texture.getWidth() / 2.f,circle.y - texture.getHeight() / 2.f);
    }

    @Override
    public void drawDebug(ShapeRenderer debugRenderer) {
        Circle body = (Circle) body_;
        Texture texture = WorldRenderer.entities_textures.get(texture_);
        Circle circle = new Circle(body.x, body.y, body.radius * 4.f);
        debugRenderer.setColor(new Color(Color.BROWN));
        debugRenderer.circle(circle.x, circle.y,circle.radius);
    }

    @Override
    public void kill() {
        health_ = 0;
        alive_ = false;
        System.out.println("Target is dead");
        GameScreen.worldController_.updateScore(points_on_death_);
        GameScreen.worldController_.world_.decreaseTargetCount();
    }

    @Override
    public void onDeath(WorldController controller) {

    }

    public void decreaseHealth(float value){
        if (value < health_)
        {
            float health_old = health_;
            health_-= value;

            if (Math.ceil(health_old) > Math.ceil(health_))
            {
                GameScreen.worldController_.updateScore(points_);
                texture_ = texture_array_.get((int)Math.ceil(health_) - 1);
                jump();
            }

        }
        else
        {
            kill();
        }
    }

    public Rectangle getRect(){
        Vector2 pos = Utils.getOriginOfRect(new Vector2(((Circle)body_).x, ((Circle)body_).y), ((Circle)body_));
        Rectangle rect = new Rectangle(pos.x, pos.y, ((Circle)body_).radius * 2.f, ((Circle)body_).radius * 2.f);
        return rect;
    }

}
