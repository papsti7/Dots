package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Vector2;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by administration on 19/06/2016.
 */
public class WorldControllerTest {

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
        worldController.update(new Vector2(4, 4));
    }

    @Test
    public void testRefreshAP() throws Exception {
        World world = new World();
        WorldController worldController = new WorldController(world);
        ChainAP chainAP = new ChainAP(0,0,true,0);
        chainAP.kill();
        worldController.entities_.add(chainAP);
        worldController.update(new Vector2(300,300));
    }

    @Test
    public void testUpdateScore() throws Exception {
        GameScreen.is_touched = true;
        World world = new World();
        WorldController worldController = new WorldController(world);
        worldController.updateScore(5);
    }

    @Test
    public void testRefreshTarget() throws Exception {
        World world = new World();
        WorldController worldController = new WorldController(world);
        Target target = new Target();
        target.kill();
        worldController.entities_.add(target);
        worldController.update(new Vector2(300,300));
    }
}