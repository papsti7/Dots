package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;

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


    public void loadTestLevel() {
        WorldRenderer.entities_textures.put("images/player_new.png", new Texture(Gdx.files.internal("images/player_new.png")));
        WorldRenderer.entities_textures.put("images/target.png", new Texture(Gdx.files.internal("images/target.png")));
        WorldRenderer.entities_textures.put("images/enemy_health_1.png", new Texture(Gdx.files.internal("images/enemy_health_1.png")));
        WorldRenderer.entities_textures.put("images/enemy_health_2.png", new Texture(Gdx.files.internal("images/enemy_health_2.png")));
        WorldRenderer.entities_textures.put("images/enemy_health_3.png", new Texture(Gdx.files.internal("images/enemy_health_3.png")));
        WorldRenderer.entities_textures.put("images/action_point.png", new Texture(Gdx.files.internal("images/action_point.png")));
        WorldRenderer.entities_textures.put("images/action_point_active.png", new Texture(Gdx.files.internal("images/action_point_active.png")));

        player_ = new Player();
        entities_.add(new Target(50,50,10,3,"images/target.png"));
        entities_.add(new Enemy(800,300,enemy_easy_,1,1,"images/enemy_health_3.png"));
        ChainAP f1 = new ChainAP(400,400,true,0);
        ChainAP f2 = new ChainAP(500,450,false,0);
        ChainAP f3 = new ChainAP(800,400,false,0);
        f1.setFirst_(f1);
        f1.setNext(f2);
        f2.setFirst_(f1);
        f2.setNext(f3);
        f3.setFirst_(f1);
        f3.setNext(f3);
        entities_.add(f1);
        entities_.add(f2);
        entities_.add(f3);



    }
}
