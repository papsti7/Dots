package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Created by Admin on 15.06.2016.
 */
public class ChainAPTest {

    @Before
    public void setUp() throws Exception {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.02f);
        when(Gdx.graphics.getWidth()).thenReturn(1024);
        when(Gdx.graphics.getHeight()).thenReturn(720);
        GameScreen.worldRenderer_ = mock(WorldRenderer.class);

        WorldRenderer.entities_texture_strings = mock(HashMap.class);
        WorldRenderer.entities_textures = mock(HashMap.class);
        when(WorldRenderer.entities_texture_strings.get(any())).thenReturn(mock(ArrayList.class));
        when(WorldRenderer.entities_textures.get(any())).thenReturn(mock(Texture.class));
    }

    @Test
    public void testTrigger() throws Exception {
        World world = new World();
        GameScreen.worldController_ = new WorldController(world);
        GameScreen.worldController_.entities_.add(new NormalEnemy(0,0, new EnemyAttribute(1, 3.f, 4, 1), 3, 1, 1));
        GameScreen.worldController_.entities_.add(new NormalEnemy(10,12, new EnemyAttribute(1, 3.f, 4, 1), 3, 1, 1));

        Vector<Float> healths = new Vector<Float>();
        healths.add(GameScreen.worldController_.entities_.get(0).getHealth());
        healths.add(GameScreen.worldController_.entities_.get(1).getHealth());

        ActionPointFactory actionPointFactory = new ActionPointFactory();
        ArrayList<Vector2> arrayList = new ArrayList<Vector2>();
        arrayList.add(new Vector2(10, 10));
        ActionPoint actionPoint = actionPointFactory.create("ChainAP", arrayList, 1);

        actionPoint.trigger();

        assertTrue(healths.get(0) - 1 == GameScreen.worldController_.entities_.get(0).getHealth());
        assertTrue(healths.get(1) - 1 == GameScreen.worldController_.entities_.get(1).getHealth());

    }

    @Test
    public void testKill() throws Exception {

        ActionPointFactory actionPointFactory = new ActionPointFactory();
        ArrayList<Vector2> arrayList = new ArrayList<Vector2>();
        arrayList.add(new Vector2(10, 10));
        ActionPoint actionPoint = actionPointFactory.create("ChainAP", arrayList, 1);

        actionPoint.kill();

        assertFalse(actionPoint.isAlive());
    }
}