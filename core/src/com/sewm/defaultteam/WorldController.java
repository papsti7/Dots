package com.sewm.defaultteam;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by stefan on 23.04.2016.
 */
public class WorldController {
    World world_;
    Player player_;
    ArrayList<Enemy> enemies_;

    public WorldController(World world){
        world_ = world;
        player_ = world_.getPlayer_();
        enemies_ = world_.getEnemies_();
    }

    public void update(Vector2 new_pos){

        player_.update(new_pos);
        for(Enemy enemy : enemies_){
            enemy.update(new Vector2(player_.surrounding_.x, player_.surrounding_.y));
        }
    }
}
