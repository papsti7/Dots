package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
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
    Vector3 touch_point_;
    private float ap_life_time_;
    private float ap_spawn_interval_;
    private boolean ap_present_;

    private float target_spawn_interval_;
    private ActionPoint first_ = null;
    ActionPointFactory action_point_factory_;


    public WorldController(World world){
        world_ = world;
        player_ = world_.getPlayer_();
        entities_ = world_.getEntities_();
        touch_point_ = new Vector3();
        action_point_factory_ = new ActionPointFactory();
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

        checkActionPoints();
        checkTarget();

        updateEntities();
        cleanUp();
        /*System.out.println("Present? " + ap_present_);
        System.out.println("Interval: " + ap_spawn_interval_);
        System.out.println("Life time:" + ap_life_time_);
        */
    }

    private void checkActionPoints()
    {
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
    }



    private void updateEntities() {
        for (GameEntity entity : entities_)
        {
            entity.update(new Vector2(player_.surrounding_.x, player_.surrounding_.y));
        }
    }

    private void cleanUp() {
        for (Iterator<GameEntity> entity = entities_.iterator(); entity.hasNext();)
        {
            GameEntity current = entity.next();
            if (!current.isAlive())
            {
                //System.out.println("Something is dead..");
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

            if (Constants.infinite_action_points)
            {
                world_.inactive_aps_.add(action_point_factory_.create("ChainAP",new ArrayList<Vector2>(),Utils.random_.nextInt(3)+2));
            }

        }
        else
        {
            first_ = null;
        }


    }



    public void updateScore(int delta)
    {
        Player.score_ += delta;
        System.out.println("score in updateScore: " + Player.score_);
        GameScreen.worldRenderer_.updateScore(Player.score_);
    }

    public void refreshTarget(){
        target_spawn_interval_ = Constants.target_spawn_interval * (Utils.random_.nextFloat() * 0.4f + 0.8f);
    }

    private void checkTarget(){
        if(target_spawn_interval_ != 0)
        {
            float delta_time = Gdx.graphics.getDeltaTime();
            if(delta_time >= target_spawn_interval_){
                target_spawn_interval_ = 0.f;

                Vector2 new_pos = new Vector2(Utils.random_.nextInt(Gdx.graphics.getWidth()-64), Utils.random_.nextInt(Gdx.graphics.getHeight()-64));
                new_pos.add(32,32);
                //System.out.println(WorldRenderer.entities_texture_strings.get("target_textures").size());
                world_.entities_.add(new Target((int)new_pos.x, (int)new_pos.y,0,Utils.random_.nextInt(3) + 1));
            }
            else
                target_spawn_interval_ -= delta_time;
        }
    }
}
