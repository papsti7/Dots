package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

/**
 * Created by stefan on 22.04.2016.
 */
public class WorldRenderer {
    World world_;
    OrthographicCamera camera_;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    SpriteBatch spriteBatch_;

    static public Texture enemy_texture_;
    static public Texture player_texture_;
    static public Texture target_texture_;
    java.util.Map<Integer, Texture> player_health_map;
    boolean debug_;
    float counter_;


    public WorldRenderer(World world, boolean debug){
        world_ = world;
        camera_ = new OrthographicCamera(world.getWidth_(),world_.getHeight_());
        camera_.position.set(new Vector3(world_.getWidth_() / 2.f, world_.getHeight_() / 2.f, 0));
        camera_.update();
        debug_ = debug;
        spriteBatch_ = new SpriteBatch();
        loadTextures();
        shapeRenderer.setAutoShapeType(true);
        counter_ = 0;
    }

    public void render(){
            spriteBatch_.begin();
            drawPlayer();
            drawEntities();
        spriteBatch_.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(counter_ < 2)
            shapeRenderer.setColor(new Color(Color.GREEN));
        else if(counter_ >= 2 && counter_ < 4)
            shapeRenderer.setColor(new Color(Color.ORANGE));
        else
            shapeRenderer.setColor(new Color(Color.RED));

        if(GameScreen.is_touched == true)
        {
            float new_radius = world_.getPlayer_().surrounding_.radius * 1.5f;
            shapeRenderer.circle(world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y,new_radius);
        }
        else
        {
            float new_radius = world_.getPlayer_().surrounding_.radius;
            shapeRenderer.circle(world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y, new_radius);
        }

        shapeRenderer.end();

        counter_ += Gdx.graphics.getDeltaTime();
        if(debug_ == true)
            drawDebug();


    }


    void loadTextures(){
        enemy_texture_ = new Texture(Gdx.files.internal("images/enemy.png"));
        player_texture_ = new Texture(Gdx.files.internal("images/player_new.png"));
        target_texture_ = new Texture(Gdx.files.internal("images/target.png"));

        player_health_map = new HashMap<Integer, Texture>();
        player_health_map.put(3, new Texture(Gdx.files.internal("images/player_health_high.png")));
        player_health_map.put(2, new Texture(Gdx.files.internal("images/player_health_medium.png")));
        player_health_map.put(1, new Texture(Gdx.files.internal("images/player_health_low.png")));
    }

    void drawEntities()
    {
        for (GameEntity entity : world_.getEntities_())
        {
            entity.draw(spriteBatch_);
        }
    }



    void drawPlayer(){
        //spriteBatch_.draw(player_texture_, world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y);

        float radius = world_.getPlayer_().surrounding_.radius;
        Vector2 player_pos = new Vector2(world_.getPlayer_().surrounding_.x - radius, world_.getPlayer_().surrounding_.y - radius);
        spriteBatch_.draw(player_texture_, player_pos.x, player_pos.y);
        if(counter_ < 2)
            spriteBatch_.draw(player_health_map.get(3), player_pos.x, player_pos.y);
        else if(counter_ >= 2 && counter_ < 4)
            spriteBatch_.draw(player_health_map.get(2), player_pos.x, player_pos.y);
        else
            spriteBatch_.draw(player_health_map.get(1), player_pos.x, player_pos.y);


    }



    void drawDebug(){
        shapeRenderer.setProjectionMatrix(camera_.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //rendering enemies
        for (GameEntity entity : world_.getEntities_())
        {
            entity.drawDebug(shapeRenderer);
        }

        Player player = world_.getPlayer_();
        float radius = player.surrounding_.radius;
        Vector2 player_pos = new Vector2(player.surrounding_.x - radius, player.surrounding_.y - radius);
        Rectangle circle = new Rectangle(player_pos.x, player_pos.y, radius * 2, radius * 2);
        shapeRenderer.setColor(new Color(Color.GOLD));
        shapeRenderer.rect(circle.x, circle.y, circle.getWidth(), circle.getHeight());
        shapeRenderer.setColor(new Color(Color.PINK));
        shapeRenderer.circle(player.surrounding_.x, player.surrounding_.y, 10);



    }
}
