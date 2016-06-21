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

    public void parseLevelFile(String filename) throws Exception {
        Parser parser = new Parser(filename);

        player_ = parser.parsePlayer();

        List<Target> targets = parser.parseTargets();
        entities_.addAll(targets);
        target_count_ += targets.size();

        inactive_enemies_.addAll(parser.parseEnemies());
        inactive_aps_.addAll(parser.parseActionPoints());
    }


    public void loadTestLevel() {

        player_ = new Player();
        target_count_ = 30;
        entities_.add(new Target(500,100,10,3));

        inactive_enemies_.add(new NormalEnemy(1600,600,enemy_easy_,1,1,1));

        inactive_enemies_.add(new StaticEnemy(enemy_easy_,1,1,new Vector2(600, 600), new Vector2(800, 300), 8));
        inactive_enemies_.add(new StaticEnemy(enemy_easy_,1,1,new Vector2(1000, 450), new Vector2(1300, 800), 15));
        inactive_enemies_.add(new StaticEnemy(enemy_easy_,1,1,new Vector2(300, 150), new Vector2(1000, 150), 22));

        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,new Vector2(200, 150), new Vector2(200, 850), 28));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,new Vector2(600, 150), new Vector2(600, 850), 33));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,new Vector2(1000, 150), new Vector2(1000, 850), 33));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,new Vector2(1400, 150), new Vector2(1400, 850), 36));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,new Vector2(1800, 150), new Vector2(1800, 850), 36));

        inactive_enemies_.add(new NormalEnemy(1400,900,enemy_medium_,2,2,40));

        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,new Vector2(100, 800), new Vector2(1800, 800), 45));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,new Vector2(100, 500), new Vector2(1800, 500), 45));
        inactive_enemies_.add(new StaticEnemy(enemy_medium_,1,1,new Vector2(100, 200), new Vector2(1800, 200), 45));


        inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,new Vector2(600,400),new Vector2(1600,400),55));
        inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,new Vector2(1300,300),new Vector2(1300,950),55));
        inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,new Vector2(1500,500),new Vector2(1600,900),55));
        inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,new Vector2(500,200),new Vector2(1000,300),55));

        inactive_enemies_.add(new NormalEnemy(900,200,enemy_hard_,3,3,57));

        inactive_enemies_.add(new NormalEnemy(250, 900,enemy_easy_,1,1,66));
        inactive_enemies_.add(new NormalEnemy(250, 500,enemy_hard_,3,3,67));
        inactive_enemies_.add(new NormalEnemy(250, 200,enemy_medium_,2,2,65));
        inactive_enemies_.add(new NormalEnemy(1750, 900,enemy_easy_,1,1,66));
        inactive_enemies_.add(new NormalEnemy(1750, 500,enemy_hard_,3,3,68));
        inactive_enemies_.add(new NormalEnemy(1750, 200,enemy_medium_,2,2,67));
        //inactive_enemies_.add(new NormalEnemy(800,300,enemy_medium_,2,2,3));
        //inactive_enemies_.add(new NormalEnemy(800,300,enemy_hard_,3,3,5));

        //inactive_enemies_.add(new StaticEnemy(enemy_hard_,3,3,new Vector2(100, 100), new Vector2(500, 600), 8));
        //inactive_enemies_.add(new StaticEnemy(enemy_medium_,3,3,new Vector2(500, 100), new Vector2(500, 600), 8));

        // entities_.add(new StaticEnemy(enemy_easy_,3,3,new Vector2(300, 100), new Vector2(700, 600)));

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