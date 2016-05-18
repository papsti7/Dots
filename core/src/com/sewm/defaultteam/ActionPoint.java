package com.sewm.defaultteam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 11/05/2016.
 */
public abstract class ActionPoint extends GameEntity{

    protected boolean active_ = false;

    public void setFirst_(ActionPoint first_) {
        this.first_ = first_;
    }
    public void decreaseHealth(float value){}

    protected ActionPoint first_ = null;
    protected ActionPoint next_ = null;

    protected TextObject text_ = null;

    public ActionPoint getNext() {
        return next_;
    }

    public void setNext(ActionPoint next_) {
        this.next_ = next_;
    }

    @Override
    protected void updateTarget(Vector2 target_pos) {
        Rectangle body = (Rectangle) body_;
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
        if (active_)
        {
            try
            {
                if (next_.equals(this))
                {
                    trigger();
                }
                else
                {
                    next_.activate();
                    deactivate();

                }
            }
            catch(NullPointerException e)
            {
                System.out.println("Null pointer exception in ActionPoint.onContact(): next_ is null");
            }

        }
    }



    @Override
    public void draw(SpriteBatch spriteBatch) {
        Rectangle body = (Rectangle) body_;
        spriteBatch.draw(WorldRenderer.entities_textures.get(texture_), body.x,body.y);
        /*if (text_ != null)
        {
            text_.draw();
        }*/

    }

    @Override
    public void drawDebug(ShapeRenderer debugRenderer) {
        Rectangle body = (Rectangle) body_;
        Rectangle rect = new Rectangle(body.getX(), body.getY(),
                WorldRenderer.entities_textures.get(texture_).getWidth()+1,
                WorldRenderer.entities_textures.get(texture_).getHeight()+1);
        debugRenderer.setColor(Color.GOLD);
        debugRenderer.rect(rect.getX(), rect.getY(), rect.width, rect.height);
    }

    public void activate()
    {
        active_ = true;
        texture_ = "images/action_point_active.png";
    }

    protected abstract void trigger();
    public void deactivate(){
        active_ = false;
        texture_ = "images/action_point.png";
    }
}
