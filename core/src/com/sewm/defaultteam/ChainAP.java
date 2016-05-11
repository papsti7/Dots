package com.sewm.defaultteam;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 11/05/2016.
 */
public class ChainAP extends ActionPoint {

    public ChainAP(Vector2 position, boolean active, ActionPoint next)
    {
        texture_ = "images/ChainAP.png";
        body_ = new Rectangle(position.x, position.y, Constants.action_point_width, Constants.action_point_height);
        active_ = active;
        next_ = next;
    }
    @Override
    protected void trigger() {

    }

    @Override
    public void kill() {
        
    }
}
