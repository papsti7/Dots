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

    protected ActionPoint next_ = null;

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
    }

    @Override
    public void drawDebug(ShapeRenderer debugRenderer) {
        Rectangle body = (Rectangle) body_;
        Rectangle rect = new Rectangle(body.getX(), body.getY(),
                WorldRenderer.entities_textures.get(texture_).getWidth(),
                WorldRenderer.entities_textures.get(texture_).getHeight());
        debugRenderer.setColor(Color.GOLD);
        debugRenderer.rect(rect.getX(), rect.getY(), rect.width, rect.height);
    }

    public void activate()
    {
        active_ = true;
    }

    protected abstract void trigger();
}
