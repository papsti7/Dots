package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Lisa on 15.06.2016.
 */
public class WorldControllerTest {

    @Before
    public void setUp() throws Exception {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.002f);
        when(Gdx.graphics.getHeight()).thenReturn(768);
        when(Gdx.graphics.getWidth()).thenReturn(1024);
    }
    @Test
    public void testUpdate() throws Exception {
        GameScreen.is_touched = true;
        World world = new World();
        WorldController worldController = new WorldController(world);
        worldController.update(new Vector2(4,4));
    }

    @Test
    public void testCheckActionPoints() throws Exception {

    }

    @Test
    public void testUpdateEntities() throws Exception {

    }

    @Test
    public void testRefreshAP() throws Exception {

    }

    @Test
    public void testUpdateScore() throws Exception {

    }

    @Test
    public void testRefreshTarget() throws Exception {

    }

    @Test
    public void testCheckTarget() throws Exception {

    }
}