package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by Lisa on 08.06.2016.
 */
public class PlayerTest {

    @Before
    public void setUp() throws Exception {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
    }

    @After
    public void tearDown() throws Exception {
        Gdx.app.exit();
    }

    @Test
    public void createTest() throws Exception
    {
        Player player = new Player();
    }


    @Test
    public void updateAndRectTest() throws Exception {
        Player player = new Player();
        player.update(new Vector2(100,100));
        Rectangle result = player.getRect();
        Rectangle correct = new Rectangle(100 - Constants.player_radius,
                100 - Constants.player_radius,
                Constants.player_radius * 2,
                Constants.player_radius * 2);
        assertTrue(result.equals(correct));

    }

    @Test
    public void decreaseHealthTest() throws Exception {
        Player player = new Player();
        float old_health = player.getHealth();
        player.decreaseHealth(1.f);
        assertTrue(player.getHealth() == (old_health - 1.f));

    }


}