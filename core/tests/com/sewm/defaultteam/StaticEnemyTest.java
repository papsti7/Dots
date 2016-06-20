package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Lisa on 08.06.2016.
 */
public class StaticEnemyTest {

    @Before
    public void setUp() throws Exception {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.002f);
        when(Gdx.graphics.getHeight()).thenReturn(768);
        when(Gdx.graphics.getWidth()).thenReturn(1024);
    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void updateTargetTest() throws Exception {


        EnemyAttribute enemy_hard = new EnemyAttribute(13, 3.f, 5, 3);
        StaticEnemy enemy = new StaticEnemy(enemy_hard,3,3,
                new Vector2(100, 100), new Vector2(200, 100), 8);


        Rectangle turn_point_1 = new Rectangle(200, 100, Constants.player_radius * 2.f, Constants.player_radius * 2.f);
        //Rectangle turn_point_2 = new Rectangle(100, 100, 25 * 2.f, 25 * 2.f);
        while (!OverlapTester.overlapRectangles(enemy.getBody(), turn_point_1))// !enemy.getBody().equals(turn_point_1))
        {
            enemy.update(new Vector2(10,10));
            assertTrue(enemy.getTargetPos().equals(enemy.getEndPos()));

        }

        enemy.update(new Vector2(10,10));
        enemy.update(new Vector2(10,10));
        assertTrue(enemy.getTargetPos().equals(enemy.getStartPos()));

    }
}