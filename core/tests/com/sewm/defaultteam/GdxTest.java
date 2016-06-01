package com.sewm.defaultteam;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import org.junit.After;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by marki on 31.05.16.
 */
public class GdxTest implements ApplicationListener {

    @Before
    public void setUp() {
        WorldRenderer.entities_textures = new HashMap<String, Texture>();
        WorldRenderer.entities_texture_strings = new HashMap<String, List<String>>();
        Gdx.gl = mock(GL20.class);

        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
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
