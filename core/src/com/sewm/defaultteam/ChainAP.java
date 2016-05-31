package com.sewm.defaultteam;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Max on 11/05/2016.
 */
public class ChainAP extends ActionPoint {

    public String first_ap_;
    public String next_ap_;

    public ChainAP(int x, int y, boolean active, int number, ArrayList<String> textures, String first_ap, String next_ap)
    {
        first_ap_ = first_ap;
        next_ap_ = next_ap;

        texture_inactive = textures.get(0);
        texture_active = textures.get(1);

        if (active)
        {
            activate();
        }
        else
        {
            texture_ = texture_inactive;
            active_ = false;
        }

        body_ = new Rectangle(x, y, Constants.action_point_width, Constants.action_point_height);
        text_ = new TextObject(WorldRenderer.font_small_,WorldRenderer.spriteBatch_,x,y, "" + number);
    }

    @Override
    protected void trigger() {

        for (GameEntity entity : GameScreen.worldController_.entities_){
            entity.decreaseHealth(1.f);
        }
        first_.kill();

    }


    @Override
    public void kill() {
        setAlive_(false);
        assert next_ != null;
        if(next_ != this)
            next_.kill();
    }
}
