package com.sewm.defaultteam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by stefan on 22.04.2016.
 */
public class WorldRenderer {
    World world_;
    OrthographicCamera camera_;
    ShapeRenderer debugRenderer = new ShapeRenderer();


    public WorldRenderer(World world){
        world_ = world;
        camera_ = new OrthographicCamera(world.getWidth_(),world_.getHeight_());
        camera_.position.set(new Vector3(world_.getWidth_() / 2.f, world_.getHeight_() / 2.f, 0));
        camera_.update();
    }

    public void render(){
        debugRenderer.setProjectionMatrix(camera_.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        //rendering enemies
        for(Enemy enemy : world_.getEnemies_()){
            Rectangle rect = enemy.body_;
            float x1 = enemy.body_.x + rect.x;
            float y1 = enemy.body_.y + rect.y;
            debugRenderer.setColor(new Color(Color.BLUE));
            debugRenderer.rect(x1, y1, rect.width, rect.height);

        }
        Player player = world_.getPlayer_();
        Circle circle = new Circle(player.surrounding_);
        debugRenderer.setColor(new Color(Color.GOLD));
        debugRenderer.circle(circle.x, circle.y, circle.radius);
        debugRenderer.end();



    }
}
