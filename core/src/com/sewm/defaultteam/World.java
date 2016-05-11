package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by stefan on 22.04.2016.
 */
public class World {
    static public Player player_;
    public EnemyAttribute enemy_easy_ = new EnemyAttribute(1, 1.f, 4, 1);
    public EnemyAttribute enemy_medium_ = new EnemyAttribute(8, 2.f, 13, 2);
    public EnemyAttribute enemy_hard_ = new EnemyAttribute(13, 3.f, 8, 3);
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
        entities_.add(new Enemy(new Vector2(1000.f, 300.f),enemy_easy_,1,1));
        entities_.add(new Enemy(new Vector2(100.f, 300.f),enemy_easy_,1,1));
        entities_.add(new Enemy(new Vector2(1000.f, 900.f),enemy_easy_,1,1));
        entities_.add(new Enemy(new Vector2(1200.f, 300.f),enemy_medium_,1,3));
        entities_.add(new Enemy(new Vector2(800.f, 300.f),enemy_hard_,1,5));
        entities_.add(new Target());
    }


}
