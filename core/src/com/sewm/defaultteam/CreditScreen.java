package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CreditScreen implements Screen {
    BitmapFont text;
    BitmapFont text2;
    SpriteBatch spriteBatch_ = new SpriteBatch();
    float menu_button_y_1_;
    float menu_button_y_2_;
    float menu_button_x_;
    float text_size_x1;
    float text_size_y1;
    Texture background_texture_ = new Texture(Gdx.files.internal("images/background.png"));
    Texture button_texture_ = new Texture(Gdx.files.internal("images/button.png"));

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getHeight()/10;
        text = generator.generateFont(parameter);
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = Gdx.graphics.getHeight()/20;
        text2 = generator.generateFont(parameter2);
        generator.dispose();
        if(Constants.aspect_ratio == Constants.aspect_ratio_16_9){
            System.out.print("Aspect ratio is 16:9\n");
            menu_button_x_ = Constants.menu_button_x_16_9;
            menu_button_y_1_ = Constants.menu_button_y_1_16_9;
            menu_button_y_2_ = Constants.menu_button_y_2_16_9;
            text_size_x1 = menu_button_x_ + button_texture_.getWidth() / 2.f - Gdx.graphics.getWidth() * 0.055f;
            text_size_y1 = menu_button_y_2_ + button_texture_.getHeight()/2.f + Gdx.graphics.getHeight()/20.f;

        }
        else if(Constants.aspect_ratio == Constants.aspect_ratio_4_3) {
            System.out.print("Aspect ratio is 4:3\n");
            menu_button_x_ = Constants.menu_button_x_4_3;
            menu_button_y_1_ = Constants.menu_button_y_1_4_3;
            menu_button_y_2_ = Constants.menu_button_y_2_4_3;
            text_size_x1 = menu_button_x_ + button_texture_.getWidth() / 2.f - Gdx.graphics.getWidth() * 0.081f;
            text_size_y1 = menu_button_y_2_ + button_texture_.getHeight()/2.f + Gdx.graphics.getHeight()/20.f;

        }
        else
        {
            System.out.print("\n\naspect ratio was not exspected!!!\n\n");
        }
    }

    @Override
    public void render(float delta) {
        spriteBatch_.begin();
        spriteBatch_.draw(background_texture_, 0.f, 0.f);
        spriteBatch_.draw(button_texture_, menu_button_x_, menu_button_y_2_);
        text.draw(spriteBatch_,"Back",text_size_x1,text_size_y1);
        text2.draw(spriteBatch_, "special thanks to Jakob Fischer for VideoPhreak(Font)", text_size_x1 - Gdx.graphics.getWidth() * 0.3f, menu_button_y_1_);

        spriteBatch_.end();

        Rectangle back_button = new Rectangle(menu_button_x_,menu_button_y_2_, button_texture_.getWidth(), button_texture_.getHeight());


        Vector2 touch_point = new Vector2(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY());
        if (Gdx.input.isTouched()) {

            if (back_button.contains(touch_point)) {
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
