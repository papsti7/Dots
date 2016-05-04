package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by stefan on 22.04.2016.
 */
public class WorldRenderer {
    World world_;
    static OrthographicCamera camera_;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    static public SpriteBatch spriteBatch_;

    static public Texture enemy_texture_;
    static public Texture player_texture_;
    static public Texture target_texture_;
    java.util.Map<Integer, Texture> player_health_map;
    boolean debug_;
    static public ArrayList<TextObject> texts_;
    static public BitmapFont font_small_;
    static public BitmapFont font_large_;


    public WorldRenderer(World world, boolean debug){
        world_ = world;
        camera_ = new OrthographicCamera(world.getWidth_(),world_.getHeight_());
        camera_.position.set(new Vector3(world_.getWidth_() / 2.f, world_.getHeight_() / 2.f, 0));
        camera_.update();
        debug_ = debug;
        texts_ = new ArrayList<TextObject>();
        font_small_ = loadFonts(40);
        font_large_ = loadFonts(180);

        spriteBatch_ = new SpriteBatch();

        loadTextures();
        shapeRenderer.setAutoShapeType(true);

    }

    public BitmapFont loadFonts(int size)
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        return font;

    }

    public void render(){
        spriteBatch_.begin();
            drawEntities();
            drawPlayer();
            drawText();



        spriteBatch_.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(world_.getPlayer_().getHealth_() > 2.f)
            shapeRenderer.setColor(new Color(Color.GREEN));
        else if(world_.getPlayer_().getHealth_() > 1.f && world_.getPlayer_().getHealth_() <= 2.f)
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

        if(debug_ == true)
            drawDebug();


    }

    private void drawText() {
        for (TextObject text : texts_)
        {
            text.draw();
        }
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
        if(world_.getPlayer_().getHealth_() > 2.f)
            spriteBatch_.draw(player_health_map.get(3), player_pos.x, player_pos.y);
        else if(world_.getPlayer_().getHealth_() > 1.f && world_.getPlayer_().getHealth_() <= 2.f)
            spriteBatch_.draw(player_health_map.get(2), player_pos.x, player_pos.y);
        else if(world_.getPlayer_().getHealth_() <= 1.f && world_.getPlayer_().getHealth_() > 0.f)
            spriteBatch_.draw(player_health_map.get(1), player_pos.x, player_pos.y);
        else{
            byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

            Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
            BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
            PixmapIO.writePNG(Gdx.files.external("DoTs/assets/images/GameOverScreen.png"), pixmap);
            StartPoint.startPoint_.setScreen(new GameOverScreen());
        }

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
        shapeRenderer.end();
    }
}
