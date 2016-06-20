package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Lisa on 15.06.2016.
 */
public class TargetTest {
    @Before
    public void setUp()
    {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getHeight()).thenReturn(768);
        when(Gdx.graphics.getWidth()).thenReturn(1024);

        GameScreen.worldController_ = mock(WorldController.class);
        GameScreen.worldController_.world_ = mock(World.class);
    }

    @Test
    public void updateTargetTest() throws Exception {
        Target target = new Target(300, 300, 50, 3);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.03f);

        target.updateTarget(new Vector2(0,0));
        assertTrue(target.getHealth() == 3.f);

        target.updateTarget(new Vector2(300,300));
        assertTrue(target.getHealth() < 3.f);
    }

    @Test
    public void updatePositionTest() throws Exception {
        Target target = new Target(300, 300, 50, 3);
        for (int i = 0; i < 10; i++)
            target.updatePosition();
        Circle body = (Circle) target.getBody();
        assertTrue( body.x == 300 && body.y == 300);
    }

    @Test
    public void onContactTest() throws Exception {
        Target target = new Target(300, 300, 50, 3);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.5f);
        target.onContact();
        //0.5 * 2 = 1 -> decreaseHealth(1) => 3 - 1 = 2
        assertTrue(target.getHealth() == 2.f);

    }

    @Test
    public void killTest() throws Exception {
        Target target = new Target(300, 300, 50, 3);
        target.kill();
        assertTrue(target.getHealth() == 0);
        assertTrue(!target.isAlive());

    }


    @Test
    public void decreaseHealthTest() throws Exception {
        //also tests jump
        Target target = new Target(300, 300, 50, 3);
        assertTrue(target.getHealth() == 3);
        assertTrue(target.isAlive());

        target.decreaseHealth(0.5f);
        assertTrue(target.getHealth() == 2.5f);
        Circle body = (Circle)target.getBody();
        assertTrue(body.x == 300 && body.y == 300);
        assertTrue(target.isAlive());

        target.decreaseHealth(0.6f);
        assertTrue(target.getHealth() == 1.9f);
        body = (Circle)target.getBody();
        assertTrue(body.x != 300 || body.y != 300);
        assertTrue(target.isAlive());
        assertTrue(body.x < 1024 && body.x > 0 && body.y < 768 && body.x > 0);

        target.decreaseHealth(3.f);
        assertTrue(target.getHealth() == 0);
        assertTrue(!target.isAlive());

    }
}