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

    static public Texture enemy_texture_;
    static public Texture player_texture_;
    static public Texture target_texture_;
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

            drawPlayer();

            drawEntities();
        spriteBatch_.end();

        if(debug_)
            drawDebug();


    }


    void loadTextures(){
        enemy_texture_ = new Texture(Gdx.files.internal("images/enemy.png"));
        player_texture_ = new Texture(Gdx.files.internal("images/player.png"));
        target_texture_ = new Texture(Gdx.files.internal("images/target.png"));

    }

    void drawEntities()
    {
        for (GameEntity entity : world_.getEntities_())
        {
            entity.draw(spriteBatch_);
        }
    }



    void drawPlayer(){
        spriteBatch_.draw(player_texture_, world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y);
    }



    void drawDebug(){
        debugRenderer.setProjectionMatrix(camera_.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        //rendering enemies
        for (GameEntity entity : world_.getEntities_())
        {
            entity.drawDebug(debugRenderer);
        }

        Player player = world_.getPlayer_();
        Rectangle rect = new Rectangle(player.surrounding_.x, player.surrounding_.y, player_texture_.getWidth(), player_texture_.getHeight());
        debugRenderer.setColor(new Color(Color.GOLD));
        debugRenderer.rect(rect.x, rect.y, rect.getWidth(), rect.getHeight());
        debugRenderer.setColor(new Color(Color.PINK));
        debugRenderer.circle(player.surrounding_.x, player.surrounding_.y, 10);
        debugRenderer.end();
    }
}
