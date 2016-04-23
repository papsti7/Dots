package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        spriteBatch_.draw(player_texture_, world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y);
    }

    void drawDebug(){
        debugRenderer.setProjectionMatrix(camera_.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        //rendering enemies
        for(Enemy enemy : world_.getEnemies_()){
            Rectangle rect = new Rectangle(enemy.body_.getX(), enemy.body_.getY(), enemy_texture_.getWidth(), enemy_texture_.getHeight());
            debugRenderer.setColor(new Color(Color.BLUE));
            debugRenderer.rect(rect.getX(), rect.getY(), rect.width, rect.height);

        }

        Player player = world_.getPlayer_();
        Rectangle circle = new Rectangle(player.surrounding_.x, player.surrounding_.y, player_texture_.getWidth(), player_texture_.getHeight());
        debugRenderer.setColor(new Color(Color.GOLD));
        debugRenderer.rect(circle.x, circle.y, circle.getWidth(), circle.getHeight());
        debugRenderer.end();
    }
}
