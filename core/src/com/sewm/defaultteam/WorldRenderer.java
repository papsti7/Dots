package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by stefan on 22.04.2016.
 */
public class WorldRenderer {
    World world_;
    OrthographicCamera camera_;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    SpriteBatch spriteBatch_;

    Texture enemy_texture_;
    Texture player_texture_;
    boolean debug_;


    public WorldRenderer(World world, boolean debug){
        world_ = world;
        camera_ = new OrthographicCamera(world.getWidth_(),world_.getHeight_());
        camera_.position.set(new Vector3(world_.getWidth_() / 2.f, world_.getHeight_() / 2.f, 0));
        camera_.update();
        debug_ = debug;
        spriteBatch_ = new SpriteBatch();
        loadTextures();
        shapeRenderer.setAutoShapeType(true);
    }

    public void render(){
        spriteBatch_.begin();
            drawEnemies();
            drawPlayer();
        spriteBatch_.end();

        if(debug_ == true)
            drawDebug();


    }
    void loadTextures(){
        enemy_texture_ = new Texture(Gdx.files.internal("images/enemy.png"));
        player_texture_ = new Texture(Gdx.files.internal("images/player.png"));
    }

    void drawEnemies(){
        for(Enemy enemy : world_.getEnemies_()){
            spriteBatch_.draw(enemy_texture_, enemy.body_.getX(), enemy.body_.y);
        }
    }

    void drawPlayer(){
        //spriteBatch_.draw(player_texture_, world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(world_.getPlayer_().color);
        shapeRenderer.circle(world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y, world_.getPlayer_().surrounding_.radius);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(Color.GREEN));
        if(GameScreen.is_touched == true)
        {
            float new_radius = world_.getPlayer_().surrounding_.radius * 1.5f;
            shapeRenderer.circle(world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y,new_radius);
        }
        else
        {
            float new_radius = world_.getPlayer_().surrounding_.radius * 1.1f;
            shapeRenderer.circle(world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y, new_radius);
        }

        shapeRenderer.end();
        float radius = world_.getPlayer_().surrounding_.radius;
        Vector2 player_pos = new Vector2(world_.getPlayer_().surrounding_.x - radius, world_.getPlayer_().surrounding_.y - radius);
        spriteBatch_.draw(player_texture_, player_pos.x, player_pos.y);
    }

    void drawDebug(){
        shapeRenderer.setProjectionMatrix(camera_.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //rendering enemies
        for(Enemy enemy : world_.getEnemies_()){
            Rectangle rect = new Rectangle(enemy.body_.getX(), enemy.body_.getY(), enemy_texture_.getWidth(), enemy_texture_.getHeight());
            shapeRenderer.setColor(new Color(Color.BLUE));
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.width, rect.height);

        }

        Player player = world_.getPlayer_();
        float radius = player.surrounding_.radius;
        Vector2 player_pos = new Vector2(player.surrounding_.x - radius, player.surrounding_.y - radius);
        Rectangle circle = new Rectangle(player_pos.x, player_pos.y, radius * 2, radius * 2);
        shapeRenderer.setColor(new Color(Color.GOLD));
        shapeRenderer.rect(circle.x, circle.y, circle.getWidth(), circle.getHeight());
        shapeRenderer.setColor(new Color(Color.PINK));
        shapeRenderer.circle(player.surrounding_.x, player.surrounding_.y, 10);
        shapeRenderer.end();
    }
}
