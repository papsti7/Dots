package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by stefan on 23.04.2016.
 */
public class WorldController {
    World world_;
    Player player_;
    ArrayList<GameEntity> entities_;
    Vector3 touchpoint_;
    private float ap_life_time_;
    private float ap_spawn_interval_;
    private boolean ap_present_;
    private ActionPoint first_ = null;

    public WorldController(World world){
        world_ = world;
        player_ = world_.getPlayer_();
        entities_ = world_.getEntities_();
        touchpoint_ = new Vector3();
        spawnAP();

    }

    public void update(Vector2 new_pos){

        if (GameScreen.is_touched)
        {
            player_.update(new_pos);
        }
        world_.time_ += Gdx.graphics.getDeltaTime();
        for (Iterator<Enemy> enemy = world_.inactive_enemies_.iterator(); enemy.hasNext();){
            Enemy current = enemy.next();
            if((float)(current.spawn_time_) <= world_.time_)
            {
                entities_.add(current);
                enemy.remove();
            }
        }
        float time = Gdx.graphics.getDeltaTime();
        if (first_ != null)
        {

            if (!ap_present_)
            {
                if (ap_spawn_interval_ > time)
                {
                    ap_spawn_interval_ -= time;
                }
                else
                {
                    spawnAP();
                }
            }
            else
            {
                if (ap_life_time_ > time)
                {
                    ap_life_time_ -= time;
                }
                else
                {
                    first_.kill();
                }
            }
        }

        updateEntities();
        cleanUp();
        /*System.out.println("Present? " + ap_present_);
        System.out.println("Interval: " + ap_spawn_interval_);
        System.out.println("Life time:" + ap_life_time_);
        */
    }

    public void updateEntities() {
        for (GameEntity entity : entities_)
        {
            entity.update(new Vector2(player_.surrounding_.x, player_.surrounding_.y));
        }
    }

    private void cleanUp() {
        for (Iterator<GameEntity> entity = entities_.iterator(); entity.hasNext();)
        {
            GameEntity current = entity.next();
            if (!current.isAlive_())
            {
                System.out.println("Something is dead..");
                current.onDeath(this);
                entity.remove();
            }

        }

    }

    public void refreshAP()
    {
        ap_present_ = false;
        ap_spawn_interval_ = Constants.action_point_spawn_interval_ * (Utils.random_.nextFloat() * 0.4f + 0.8f);

    }

    private void spawnAP()
    {
        ap_present_ = true;
        ap_life_time_ = Constants.action_point_life_span_ * (Utils.random_.nextFloat() * 0.4f + 0.8f);
        if (world_.inactive_aps_.size() > 0)
        {
            first_ = world_.inactive_aps_.get(0);
            world_.inactive_aps_.remove(0);
            ActionPoint current = first_;
            while (!current.getNext().equals(current))
            {
                world_.entities_.add(current);
                current = current.getNext();
            }
            world_.entities_.add(current);


        }
        else
        {
            first_ = null;
        }


    }


    public void updateScore(int delta)
    {
        Player.score_ += delta;
        GameScreen.worldRenderer_.updateScore(Player.score_);
    }
}
