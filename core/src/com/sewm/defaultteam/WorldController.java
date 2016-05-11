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
//    ArrayList<Enemy> enemies_;
//    ArrayList<Target> targets_;
    Vector3 touchpoint_;

    public WorldController(World world){
        world_ = world;
        player_ = world_.getPlayer_();
        entities_ = world_.getEntities_();
        touchpoint_ = new Vector3();
    }

    public void update(Vector2 new_pos, WorldRenderer worldRenderer){
        player_.update(new_pos);
        cleanUp();

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
            if (!entity.next().isAlive_())
            {
                entity.remove();
            }

        }

    }

    public void updateHealths(){
        updateTargetsHealth();
        if(!StartPoint.immortal)
            updatePlayerHealths();
    }

    void updateTargetsHealth(){
        Rectangle player = world_.getPlayer_().getRect();
        for(GameEntity target : world_.getEntities_()){
            if(target.getInertia_() == 0){
                if(OverlapTester.overlapRectangles(player , ((Target)target).getRect())){
                    target.onContact();
                }
            }
        }
    }

    void updatePlayerHealths(){
        Rectangle player = world_.getPlayer_().getRect();
        for(GameEntity enemy : world_.getEntities_()){
            if(enemy.getInertia_() != 0){
                if(OverlapTester.overlapRectangles(player , (Rectangle)(enemy.getBody_()))){
                    world_.getPlayer_().decreaseHealth(Gdx.graphics.getDeltaTime());
                }
            }
        }
    }









}
