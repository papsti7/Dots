package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by stefan on 22.04.2016.
 */
public class World {
    Player player_;



    ArrayList<GameEntity> entities_;
//    ArrayList<Enemy> enemies_;
//    ArrayList<Target> targets_;
    int width_;
    int height_;

    public ArrayList<GameEntity> getEntities_() {
        return entities_;
    }



    public Player getPlayer_() {
        return player_;
    }

    public World(){

        player_ = new Player();
        entities_ = new ArrayList<GameEntity>();



        width_ = Gdx.graphics.getWidth();
        height_ = Gdx.graphics.getHeight();
    }

    public int getHeight_() {
        return height_;
    }

    public int getWidth_() {
        return width_;
    }


    public void loadTestLevel()
    {
        /*entities_.add(new Enemy(new Vector2(1000.f, 300.f),1));
        entities_.add(new Enemy(new Vector2(100.f, 300.f),1));
        entities_.add(new Enemy(new Vector2(1000.f, 900.f),1));
        entities_.add(new Enemy(new Vector2(1200.f, 300.f),2));
        entities_.add(new Enemy(new Vector2(800.f, 300.f),3));*/
        entities_.add(new Target());
    }


}
