package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Admin on 04.05.2016.
 */
public class WinScreen implements Screen {

    SpriteBatch spriteBatch_ = new SpriteBatch();
    public Texture background_;
    TextObject win_text = new TextObject(WorldRenderer.font_large_,
            spriteBatch_,
            Gdx.graphics.getWidth()/4.f,
            2*Gdx.graphics.getHeight()/3.f ,
            "You Win!");
    @Override
    public void show() {

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
        win_text.draw();
        spriteBatch_.end();
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
