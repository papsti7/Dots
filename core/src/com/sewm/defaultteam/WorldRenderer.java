package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
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

    static public java.util.Map<String, Texture> entities_textures;
    java.util.Map<Integer, Texture> player_health_map;
    boolean debug_;
    static public ArrayList<TextObject> texts_;
    static public BitmapFont font_small_;
    static public BitmapFont font_large_;
    static public String score_text = "Score: ";
    static public int enemy_contact_;
    static public int target_contact_;


    public WorldRenderer(World world, boolean debug){
        world_ = world;
        camera_ = new OrthographicCamera(world.getWidth_(),world_.getHeight_());
        camera_.position.set(new Vector3(world_.getWidth_() / 2.f, world_.getHeight_() / 2.f, 0));
        camera_.update();
        debug_ = debug;
        texts_ = new ArrayList<TextObject>();
        font_small_ = loadFonts(Gdx.graphics.getHeight()/20);
        font_large_ = loadFonts(Gdx.graphics.getHeight()/6);

        spriteBatch_ = new SpriteBatch();

        entities_textures = new HashMap<String , Texture>();
        loadTextures();
        shapeRenderer.setAutoShapeType(true);
        texts_.add(new TextObject(font_small_,spriteBatch_, Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.95f, score_text + "0"));

    }

    public BitmapFont loadFonts(int size)
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/videophreak.ttf"));
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

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(world_.getPlayer_().getHealth_() > 2.f)
            shapeRenderer.setColor(new Color(Color.GREEN));
        else if(world_.getPlayer_().getHealth_() > 1.f && world_.getPlayer_().getHealth_() <= 2.f)
            shapeRenderer.setColor(new Color(Color.ORANGE));
        else
            shapeRenderer.setColor(new Color(Color.RED));

        if(GameScreen.is_touched)
        {
            float new_radius = world_.getPlayer_().surrounding_.radius * 1.5f;
            shapeRenderer.circle(world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y,new_radius);
        }
        else
        {
            float new_radius = world_.getPlayer_().surrounding_.radius;
            shapeRenderer.circle(world_.getPlayer_().surrounding_.x, world_.getPlayer_().surrounding_.y, new_radius);
        }


        if (enemy_contact_ > 0) {
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 0, 0.3f);
            shapeRenderer.rectLine(0,0,Gdx.graphics.getWidth(),0,60);
            shapeRenderer.rectLine(0,0,0,Gdx.graphics.getHeight(),60);
            shapeRenderer.rectLine(0,Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),60);
            shapeRenderer.rectLine(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),0,60);
            //shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            enemy_contact_--;
        }

        if (target_contact_ > 0)
        {
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 1, 0, 0.3f);
            shapeRenderer.rectLine(0,0,Gdx.graphics.getWidth(),0,60);
            shapeRenderer.rectLine(0,0,0,Gdx.graphics.getHeight(),60);
            shapeRenderer.rectLine(0,Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),60);
            shapeRenderer.rectLine(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),Gdx.graphics.getWidth(),0,60);
            target_contact_--;
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);


        if(debug_)
            drawDebug();


    }

    private void drawText() {
        for (TextObject text : texts_)
        {
            text.draw();
        }
    }

    /*
    public void loadEntityTexture(String filename)
    {
        entities_textures.put(filename, new Texture(Gdx.files.internal(filename)));
    }
    */

    void loadTextures(){
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
        Texture texture = WorldRenderer.entities_textures.get(world_.getPlayer_().getTexture());
        Vector2 player_pos = new Vector2(world_.getPlayer_().surrounding_.x - texture.getWidth() / 2.f, world_.getPlayer_().surrounding_.y - texture.getHeight() / 2.f);
        spriteBatch_.draw(texture, player_pos.x, player_pos.y);

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
            GameOverScreen gameOverScreen = new GameOverScreen();
            gameOverScreen.background_ = new Texture(pixmap);
            StartPoint.startPoint_.setScreen(gameOverScreen);
        }
        spriteBatch_.draw(texture, player_pos.x, player_pos.y);

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

    public void updateScore(int score) {
        int index = -1;
        for (int element = 0; element < texts_.size(); element++)
        {
            String text = texts_.get(element).getText_();
            text = text.substring(0,text.indexOf(' '));
            text += " ";
            System.out.println(text);
            if (text.equals(score_text))
            {
                index = element;
                break;
            }
        }

        texts_.get(index).setText_(score_text + score);

    }
}
