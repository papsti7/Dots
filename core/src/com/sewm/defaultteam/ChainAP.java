package com.sewm.defaultteam;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;

/**
 * Created by Max on 11/05/2016.
 */
public class ChainAP extends ActionPoint {
    public ChainAP(int x, int y, boolean active, int number, String textures_string)
    {
        this(x, y, active, number);
        List<String> textures = WorldRenderer.entities_texture_strings.get(textures_string);
        texture_inactive_ = textures.get(0);
        texture_active_ = textures.get(1);
    }

    public ChainAP(int x, int y, boolean active, int number)
    {
        x *= Constants.virtual_to_real_x;
        y *= Constants.virtual_to_real_y;

        if (active) {
            activate();
        } else {
            deactivate();
        }

        next_ = this;
        body_ = new Rectangle(x, y, Constants.action_point_width, Constants.action_point_height);
        text_ = new TextObject(WorldRenderer.font_small_, WorldRenderer.spriteBatch_, x, y, String.valueOf(number));
    }

    @Override
    protected void trigger() {
        for (GameEntity entity : GameScreen.worldController_.entities_) {
            entity.decreaseHealth(1.f);
        }
        first_.kill();

    }

    @Override
    public void kill() {
        setAlive(false);
        assert next_ != null;
        if(next_ != this) {
            next_.kill();
        }
    }
}
