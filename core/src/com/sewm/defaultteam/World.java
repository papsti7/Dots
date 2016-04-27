package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by stefan on 22.04.2016.
 */
public class World {
    Player player_;
    ArrayList<Enemy> enemies_;
    int width_;
    int height_;

    public ArrayList<Enemy> getEnemies_() {
        return enemies_;
    }

    public Player getPlayer_() {
        return player_;
    }

    public World(){
        enemies_ = new ArrayList<Enemy>();
        player_ = new Player();
        enemies_.add(new Enemy());
        enemies_.add(new Enemy(new Vector2(1000.f, 300.f), 2, 1, 3));
        width_ = Gdx.graphics.getWidth();
        height_ = Gdx.graphics.getHeight();
    }

    public int getHeight_() {
        return height_;
    }

    public int getWidth_() {
        return width_;
    }
}
