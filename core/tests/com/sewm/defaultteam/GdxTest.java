package com.sewm.defaultteam;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class GdxTest implements ApplicationListener {
    @Before
    public void setUp() {
        Gdx.gl = mock(GL20.class);
        Gdx.graphics = mock(Graphics.class);
        GameScreen.worldController_ = mock(WorldController.class);
        GameScreen.worldRenderer_ = mock(WorldRenderer.class);

        WorldRenderer.entities_texture_strings = mock(HashMap.class);
        WorldRenderer.entities_textures = mock(HashMap.class);
        when(WorldRenderer.entities_texture_strings.get(any())).thenReturn(mock(ArrayList.class));
        when(WorldRenderer.entities_textures.get(any())).thenReturn(mock(Texture.class));

        when(Gdx.graphics.getDeltaTime()).thenReturn(0.02f);
        when(Gdx.graphics.getWidth()).thenReturn((int) Constants.virtual_screen_width);
        when(Gdx.graphics.getHeight()).thenReturn((int) Constants.virtual_screen_height);

        Constants.resize((int) Constants.virtual_screen_width, (int) Constants.virtual_screen_height);

        new HeadlessApplication(this);
    }

    @After
    public void tearDown() {
        Gdx.app.exit();
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
