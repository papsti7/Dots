package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Admin on 08.06.2016.
 */
public class EnemyTest {


    @Before
    public void setUp() throws Exception {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        GameScreen.worldController_ = mock(WorldController.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.02f);
        when(Gdx.graphics.getWidth()).thenReturn((int) Constants.virtual_screen_width);
        when(Gdx.graphics.getHeight()).thenReturn((int) Constants.virtual_screen_height);

        WorldRenderer.entities_texture_strings = mock(HashMap.class);
        WorldRenderer.entities_textures = mock(HashMap.class);
        when(WorldRenderer.entities_texture_strings.get(any())).thenReturn(mock(ArrayList.class));
        when(WorldRenderer.entities_textures.get(any())).thenReturn(mock(Texture.class));
    }

    @Test
    public void testUpdatePosition() throws Exception {
        NormalEnemy normalEnemy = new NormalEnemy(0,0, new EnemyAttribute(1, 3.f, 4, 1), 3, 1, 1);
        normalEnemy.updateTarget(new Vector2(100, 100));
        int counter = 0;
        while(!normalEnemy.body_.contains(100, 100))
        {
            normalEnemy.updatePosition();
            counter++;
            assertTrue(counter < 32);
        }

        assertTrue(normalEnemy.body_.contains(100, 100));
    }

    @Test
    public void testOnContact() throws Exception {
        NormalEnemy normalEnemy = new NormalEnemy(0,0, new EnemyAttribute(1, 3.f, 4, 1), 3, 1, 1);
        World world = new World();
        float old_health = world.getPlayer_().getHealth();
        normalEnemy.onContact();
        assertTrue(old_health > world.getPlayer_().getHealth());
    }

    @Test
    public void testDecreaseHealth() throws Exception {
        NormalEnemy normalEnemy = new NormalEnemy(0,0, new EnemyAttribute(1, 3.f, 4, 1), 3, 1, 1);
        float old_h = normalEnemy.getHealth();
        normalEnemy.decreaseHealth(1.f);
        assertTrue(normalEnemy.getHealth() == old_h - 1.f);
    }

    @Test
    public void testDraw() throws Exception {

    }

    @Test
    public void testDrawDebug() throws Exception {

    }

    @Test
    public void testKill() throws Exception {
        NormalEnemy normalEnemy = new NormalEnemy(0,0, new EnemyAttribute(1, 3.f, 4, 1), 3, 1, 1);
        normalEnemy.kill();
        assertTrue(!normalEnemy.isAlive());
    }

    @Test
    public void testOnDeath() throws Exception {

    }
}