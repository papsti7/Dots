package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    public void parseLevelFile(String filename) throws IOException
    {
            Parser parser = new Parser(filename);

            parser.parseTextures();

            player_ = parser.parsePlayer();

            Target target;
            while((target = parser.parseTarget()) != null) {
                entities_.add(target);
            }

            Enemy enemy;
            while((enemy = parser.parseEnemy(this)) != null) {
                entities_.add(enemy);
            }

            //TODO: implement Actionpoints
            /*
            Actionpoint actionpoint;
            while((actionpoint = parser.parseActionpoint())) {
                entities_.add(actionpoint);
            }
            */
    }


}
