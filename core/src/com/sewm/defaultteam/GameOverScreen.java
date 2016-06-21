package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Admin on 04.05.2016.
 */
public class GameOverScreen implements Screen {

    SpriteBatch spriteBatch_ = new SpriteBatch();
    public Texture background_;
    TextObject game_over_text_ = new TextObject(WorldRenderer.font_large_,
                                                spriteBatch_,
                                                Gdx.graphics.getWidth()/4.f,
                                                2*Gdx.graphics.getHeight()/3.f ,
                                                "Game Over!");


    Texture button_texture_ = new Texture(Gdx.files.internal("images/button.png"));
    BitmapFont button_text_;

    float menu_button_y_2_;
    float menu_button_x_;
    float text_size_x2;
    float text_size_y2;
    boolean touched;
    @Override
    public void show() {
        touched = Gdx.input.isTouched();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getHeight()/10;
        button_text_ = generator.generateFont(parameter);
        generator.dispose();
        if(Constants.aspect_ratio == Constants.aspect_ratio_16_9){
            System.out.print("Aspect ratio is 16:9\n");
            menu_button_x_ = Constants.menu_button_x_16_9;
            menu_button_y_2_ = Constants.menu_button_y_2_16_9;
            text_size_x2 =  menu_button_x_ + button_texture_.getWidth() / 2.f - Gdx.graphics.getWidth() * 0.09f;
            text_size_y2 = menu_button_y_2_ + button_texture_.getHeight()/2.f + Gdx.graphics.getHeight()/20.f;
        }
        else if(Constants.aspect_ratio == Constants.aspect_ratio_4_3) {
            System.out.print("Aspect ratio is 4:3\n");
            menu_button_x_ = Constants.menu_button_x_4_3;
            menu_button_y_2_ = Constants.menu_button_y_2_4_3;
            text_size_x2 =  menu_button_x_ + button_texture_.getWidth() / 2.f - Gdx.graphics.getWidth() * 0.245f;
            text_size_y2 = menu_button_y_2_ + button_texture_.getHeight()/2.f + Gdx.graphics.getHeight()/20.f;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch_.setProjectionMatrix(WorldRenderer.camera_.combined);
        spriteBatch_.begin();
        Color color = spriteBatch_.getColor();
        spriteBatch_.setColor(color.r, color.g, color.b, 0.3f);
        spriteBatch_.draw(background_, 0.f, 0.f);
        game_over_text_.draw();
        spriteBatch_.setColor(color.r, color.g, color.b, 1.f);
        spriteBatch_.draw(button_texture_, menu_button_x_, menu_button_y_2_);
        button_text_.draw(spriteBatch_,"Back",text_size_x2, text_size_y2);
        spriteBatch_.end();

        Rectangle button_2 = new Rectangle(menu_button_x_,menu_button_y_2_, button_texture_.getWidth(), button_texture_.getHeight());
        if (touched && !Gdx.input.isTouched())
        {
            touched = false;
        }
        Vector2 touch_point = new Vector2(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY());
        if (Gdx.input.isTouched()&& !touched) {
            if (button_2.contains(touch_point)) {
                StartPoint.getInstance().setScreen(new MenuScreen());
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
