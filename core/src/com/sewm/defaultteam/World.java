package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 22.04.2016.
 */
public class World {
    static public Player player_;
    static public EnemyAttribute enemy_easy_ = new EnemyAttribute(1, 3.f, 2, 1);
    static public EnemyAttribute enemy_medium_ = new EnemyAttribute(8, 3.f, 6, 2);
    static public EnemyAttribute enemy_hard_ = new EnemyAttribute(20, 3.f, 5, 3);
    ArrayList<GameEntity> entities_;
    ArrayList<Enemy> inactive_enemies_;
    ArrayList<ActionPoint> inactive_aps_;
    int target_count_;
    float time_;

    int width_;
    int height_;

    public ArrayList<GameEntity> getEntities_() {
        return entities_;
    }

    public Player getPlayer_() {
        return player_;
    }

    public World() {
        player_ = new Player();
        Player.score_ = 0;
        entities_ = new ArrayList<GameEntity>();
        inactive_enemies_ = new ArrayList<Enemy>();
        inactive_aps_ = new ArrayList<ActionPoint>();
        time_ = 0.f;


        width_ = Gdx.graphics.getWidth();
        height_ = Gdx.graphics.getHeight();
    }

    public int getHeight_() {
        return height_;
    }

    public int getWidth_() {
        return width_;
    }

    public void parseLevelFile(String filename) throws IOException {
        Parser parser = new Parser(filename);
        parser.parseTextures();

        player_ = parser.parsePlayer();

        List<Target> targets = parser.parseTargets();
        entities_.addAll(targets);
        target_count_ += targets.size();

        inactive_enemies_.addAll(parser.parseEnemies());
        inactive_aps_.addAll(parser.parseActionPoints());
    }


    public void loadTestLevel() {
        WorldRenderer.entities_textures.put("images/player_new.png", new Texture(Gdx.files.internal("images/player_new.png")));
        WorldRenderer.entities_textures.put("images/target_health_1.png", new Texture(Gdx.files.internal("images/target_health_1.png")));
        WorldRenderer.entities_textures.put("images/target_health_2.png", new Texture(Gdx.files.internal("images/target_health_2.png")));
        WorldRenderer.entities_textures.put("images/target_health_3.png", new Texture(Gdx.files.internal("images/target_health_3.png")));
        WorldRenderer.entities_textures.put("images/enemy_health_1.png", new Texture(Gdx.files.internal("images/enemy_health_1.png")));
        WorldRenderer.entities_textures.put("images/enemy_health_2.png", new Texture(Gdx.files.internal("images/enemy_health_2.png")));
        WorldRenderer.entities_textures.put("images/enemy_health_3.png", new Texture(Gdx.files.internal("images/enemy_health_3.png")));
        WorldRenderer.entities_textures.put("images/enemy_medium_health_1.png", new Texture(Gdx.files.internal("images/enemy_medium_health_1.png")));
        WorldRenderer.entities_textures.put("images/enemy_medium_health_2.png", new Texture(Gdx.files.internal("images/enemy_medium_health_2.png")));
        WorldRenderer.entities_textures.put("images/enemy_medium_health_3.png", new Texture(Gdx.files.internal("images/enemy_medium_health_3.png")));
        WorldRenderer.entities_textures.put("images/enemy_strong_health_1.png", new Texture(Gdx.files.internal("images/enemy_strong_health_1.png")));
        WorldRenderer.entities_textures.put("images/enemy_strong_health_2.png", new Texture(Gdx.files.internal("images/enemy_strong_health_2.png")));
        WorldRenderer.entities_textures.put("images/enemy_strong_health_3.png", new Texture(Gdx.files.internal("images/enemy_strong_health_3.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_1.png", new Texture(Gdx.files.internal("images/enemy_static_1.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_2.png", new Texture(Gdx.files.internal("images/enemy_static_2.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_3.png", new Texture(Gdx.files.internal("images/enemy_static_3.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_medium_1.png", new Texture(Gdx.files.internal("images/enemy_static_medium_1.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_medium_2.png", new Texture(Gdx.files.internal("images/enemy_static_medium_2.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_medium_3.png", new Texture(Gdx.files.internal("images/enemy_static_medium_3.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_strong_1.png", new Texture(Gdx.files.internal("images/enemy_static_strong_1.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_strong_2.png", new Texture(Gdx.files.internal("images/enemy_static_strong_2.png")));
        WorldRenderer.entities_textures.put("images/enemy_static_strong_3.png", new Texture(Gdx.files.internal("images/enemy_static_strong_3.png")));
        WorldRenderer.entities_textures.put("images/action_point.png", new Texture(Gdx.files.internal("images/action_point.png")));
        WorldRenderer.entities_textures.put("images/action_point_active.png", new Texture(Gdx.files.internal("images/action_point_active.png")));

        ArrayList<String> enemy_easy_textures = new ArrayList<String>();
        enemy_easy_textures.add("images/enemy_health_3.png");
        enemy_easy_textures.add("images/enemy_health_2.png");
        enemy_easy_textures.add("images/enemy_health_1.png");

        ArrayList<String> enemy_medium_textures = new ArrayList<String>();
        enemy_medium_textures.add("images/enemy_medium_health_3.png");
        enemy_medium_textures.add("images/enemy_medium_health_2.png");
        enemy_medium_textures.add("images/enemy_medium_health_1.png");

        ArrayList<String> enemy_hard_textures = new ArrayList<String>();
        enemy_hard_textures.add("images/enemy_strong_health_3.png");
        enemy_hard_textures.add("images/enemy_strong_health_2.png");
        enemy_hard_textures.add("images/enemy_strong_health_1.png");

        ArrayList<String> enemy_static_hard_textures = new ArrayList<String>();
        enemy_static_hard_textures.add("images/enemy_static_strong_3.png");
        enemy_static_hard_textures.add("images/enemy_static_strong_2.png");
        enemy_static_hard_textures.add("images/enemy_static_strong_1.png");

        ArrayList<String> enemy_static_medium_textures = new ArrayList<String>();
        enemy_static_medium_textures.add("images/enemy_static_medium_3.png");
        enemy_static_medium_textures.add("images/enemy_static_medium_2.png");
        enemy_static_medium_textures.add("images/enemy_static_medium_1.png");

        ArrayList<String> enemy_static_textures = new ArrayList<String>();
        enemy_static_textures.add("images/enemy_static_3.png");
        enemy_static_textures.add("images/enemy_static_2.png");
        enemy_static_textures.add("images/enemy_static_1.png");

        ArrayList<String> enemy_static_easy_textures = new ArrayList<String>();
        enemy_static_easy_textures.add("images/enemy_static_3.png");
        enemy_static_easy_textures.add("images/enemy_static_2.png");
        enemy_static_easy_textures.add("images/enemy_static_1.png");

        ArrayList<String> target_textures = new ArrayList<String>();
        target_textures.add("images/target_health_3.png");
        target_textures.add("images/target_health_2.png");
        target_textures.add("images/target_health_1.png");

        WorldRenderer.entities_texture_strings.put("enemy_easy_textures", enemy_easy_textures);
        WorldRenderer.entities_texture_strings.put("enemy_medium_textures", enemy_medium_textures);
        WorldRenderer.entities_texture_strings.put("enemy_hard_textures", enemy_hard_textures);
        WorldRenderer.entities_texture_strings.put("enemy_static_hard_textures", enemy_static_hard_textures);
        WorldRenderer.entities_texture_strings.put("enemy_static_medium_textures", enemy_static_medium_textures);
        WorldRenderer.entities_texture_strings.put("enemy_static_easy_textures", enemy_static_easy_textures);
        WorldRenderer.entities_texture_strings.put("target_textures", target_textures);

        player_ = new Player();
        target_count_ = 30;
        entities_.add(new Target(500,100,10,3,target_textures));

        inactive_enemies_.add(new NormalEnemy(800,300,enemy_easy_,1,1,enemy_easy_textures, 1));
        inactive_enemies_.add(new StaticEnemy(enemy_easy_,1,1,enemy_static_textures, new Vector2(600, 600), new Vector2(800, 300), 8));
        inactive_enemies_.add(new StaticEnemy(enemy_easy_,1,1,enemy_static_textures, new Vector2(150, 150), new Vector2(400, 400), 15));
        inactive_enemies_.add(new StaticEnemy(enemy_easy_,1,1,enemy_static_textures, new Vector2(300, 150), new Vector2(500, 150), 22));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,enemy_static_medium_textures, new Vector2(200, 150), new Vector2(200, 600), 28));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,enemy_static_medium_textures, new Vector2(400, 150), new Vector2(400, 600), 33));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,enemy_static_medium_textures, new Vector2(600, 150), new Vector2(600, 600), 33));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,enemy_static_medium_textures, new Vector2(800, 150), new Vector2(800, 600), 36));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,enemy_static_medium_textures, new Vector2(1000, 150), new Vector2(1000, 600), 36));
        inactive_enemies_.add(new NormalEnemy(400,400,enemy_medium_,2,2,enemy_medium_textures,40));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,enemy_static_medium_textures, new Vector2(100, 400), new Vector2(1000, 400), 45));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,enemy_static_medium_textures, new Vector2(100, 200), new Vector2(1000, 200), 45));
        inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,enemy_static_hard_textures,new Vector2(600,400),new Vector2(800,400),55));
        inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,enemy_static_hard_textures,new Vector2(300,300),new Vector2(300,550),55));
        inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,enemy_static_hard_textures,new Vector2(200,400),new Vector2(200,600),55));
        inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,enemy_static_hard_textures,new Vector2(600,200),new Vector2(600,600),55));
        inactive_enemies_.add(new NormalEnemy(600,200,enemy_hard_,3,3,enemy_hard_textures,57));

        inactive_enemies_.add(new NormalEnemy(200, 600,enemy_easy_,1,1,enemy_easy_textures,66));
        inactive_enemies_.add(new NormalEnemy(200, 400,enemy_hard_,3,3,enemy_hard_textures,67));
        inactive_enemies_.add(new NormalEnemy(200, 200,enemy_medium_,2,2,enemy_medium_textures,65));
        inactive_enemies_.add(new NormalEnemy(1000, 600,enemy_easy_,1,1,enemy_easy_textures,66));
        inactive_enemies_.add(new NormalEnemy(1000, 400,enemy_hard_,3,3,enemy_hard_textures,68));
        inactive_enemies_.add(new NormalEnemy(1000, 200,enemy_medium_,2,2,enemy_medium_textures,67));
        //inactive_enemies_.add(new NormalEnemy(800,300,enemy_medium_,2,2,enemy_medium_textures, 3));
        //inactive_enemies_.add(new NormalEnemy(800,300,enemy_hard_,3,3,enemy_hard_textures, 5));

        //inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,enemy_static_hard_textures, new Vector2(100, 100), new Vector2(500, 600), 8));
        //inactive_enemies_.add(new StaticEnemy(enemy_medium_,3,3,enemy_static_medium_textures, new Vector2(500, 100), new Vector2(500, 600), 8));

        // entities_.add(new StaticEnemy(enemy_easy_,3,3,enemy_easy_textures, new Vector2(300, 100), new Vector2(700, 600)));

        ChainAP f1 = new ChainAP(400,400,true,0);
        ChainAP f2 = new ChainAP(500,450,false,0);
        ChainAP f3 = new ChainAP(800,400,false,0);
        f1.setFirst(f1);
        f1.setNext(f2);
        f2.setFirst(f1);
        f2.setNext(f3);
        f3.setFirst(f1);
        f3.setNext(f3);

        ChainAP f4 = new ChainAP(700,500,true,0);
        ChainAP f5 = new ChainAP(600,300,false,0);
        ChainAP f6 = new ChainAP(800,500,false,0);
        ChainAP f7 = new ChainAP(750,400,false,0);
        f4.setFirst(f4);
        f4.setNext(f5);
        f5.setFirst(f4);
        f5.setNext(f6);
        f6.setFirst(f4);
        f6.setNext(f7);
        f7.setFirst(f4);
        f7.setNext(f7);

        inactive_aps_.add(f1);
        inactive_aps_.add(f4);
    }

    public void decreaseTargetCount() {
        if(target_count_ > 1)
        {
            target_count_--;
        }
        else
        {
            target_count_ = 0;
            WinScreen winScreen  = new WinScreen();
            StartPoint.getInstance().setScreen(winScreen);
        }
    }
}