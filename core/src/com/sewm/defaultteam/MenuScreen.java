package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.utils.random.ConstantDoubleDistribution;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lisa on 01.06.2016.
 */
public class MenuScreen implements Screen {


    SpriteBatch spriteBatch_ = new SpriteBatch();
    Texture background_texture_ = new Texture(Gdx.files.internal("images/background.png"));
    Texture button_texture_ = new Texture(Gdx.files.internal("images/button.png"));
    BitmapFont button_text_;
    float menu_button_y_1_;
    float menu_button_y_2_;
    float menu_button_x_;
    float text_size_x1;
    float text_size_y1;
    float text_size_x2;
    float text_size_y2;

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getHeight()/10;
        button_text_ = generator.generateFont(parameter);
        generator.dispose();
        if(Constants.aspect_ratio == Constants.aspect_ratio_16_9){
            System.out.print("Aspect ratio is 16:9\n");
            menu_button_x_ = Constants.menu_button_x_16_9;
            menu_button_y_1_ = Constants.menu_button_y_1_16_9;
            menu_button_y_2_ = Constants.menu_button_y_2_16_9;
            text_size_x1 = menu_button_x_ + button_texture_.getWidth() / 2.f - Gdx.graphics.getWidth() * 0.055f;
            text_size_y1 = menu_button_y_1_ + button_texture_.getHeight()/2.f + Gdx.graphics.getHeight()/20.f;
            text_size_x2 =  menu_button_x_ + button_texture_.getWidth() / 2.f - Gdx.graphics.getWidth() * 0.09f;
            text_size_y2 = menu_button_y_2_ + button_texture_.getHeight()/2.f + Gdx.graphics.getHeight()/20.f;
        }
        else if(Constants.aspect_ratio == Constants.aspect_ratio_4_3) {
            System.out.print("Aspect ratio is 4:3\n");
            menu_button_x_ = Constants.menu_button_x_4_3;
            menu_button_y_1_ = Constants.menu_button_y_1_4_3;
            menu_button_y_2_ = Constants.menu_button_y_2_4_3;
            text_size_x1 = menu_button_x_ + button_texture_.getWidth() / 2.f - Gdx.graphics.getWidth() * 0.081f;
            text_size_y1 = menu_button_y_1_ + button_texture_.getHeight()/2.f + Gdx.graphics.getHeight()/20.f;
            text_size_x2 =  menu_button_x_ + button_texture_.getWidth() / 2.f - Gdx.graphics.getWidth() * 0.245f;
            text_size_y2 = menu_button_y_2_ + button_texture_.getHeight()/2.f + Gdx.graphics.getHeight()/20.f;
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
        spriteBatch_.draw(button_texture_, menu_button_x_, menu_button_y_1_);
        spriteBatch_.draw(button_texture_, menu_button_x_, menu_button_y_2_);

        //start_game_text_.draw();

        button_text_.draw(spriteBatch_,"Start",text_size_x1,text_size_y1);
        button_text_.draw(spriteBatch_,"Credits",text_size_x2, text_size_y2);

        spriteBatch_.end();

        Rectangle button_1 = new Rectangle(menu_button_x_,menu_button_y_1_, button_texture_.getWidth(), button_texture_.getHeight());
        Rectangle button_2 = new Rectangle(menu_button_x_,menu_button_y_2_, button_texture_.getWidth(), button_texture_.getHeight());


        Vector2 touch_point = new Vector2(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY());
        if (Gdx.input.isTouched()) {

            if (button_1.contains(touch_point)) {
                StartPoint.getInstance().setScreen(new GameScreen());
            } else if (button_2.contains(touch_point)) {
                StartPoint.getInstance().setScreen(new CreditScreen());
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
