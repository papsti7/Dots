package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Admin on 15.06.2016.
 */
public class ActionPointFactoryTest {

    @Before
    public void setUp() throws Exception {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
        GameScreen.worldController_ = mock(WorldController.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.02f);
        when(Gdx.graphics.getWidth()).thenReturn((int) Constants.virtual_screen_width);
        when(Gdx.graphics.getHeight()).thenReturn((int) Constants.virtual_screen_height);
    }

    @Test
    public void testCreate() throws Exception {
        ActionPointFactory actionPointFactory = new ActionPointFactory();
        assertNull(actionPointFactory.create("",  new ArrayList<Vector2>(), 0));

        ArrayList<Vector2> arrayList = new ArrayList<Vector2>();
        arrayList.add(new Vector2(10, 10));
        ActionPoint chainAP = actionPointFactory.create("ChainAP", arrayList, 0);
        assertNull(chainAP);
        ActionPoint chainAP4 = actionPointFactory.create("ChainAP", arrayList, 10);
        assertNull(chainAP4);

        ActionPoint chainAP2 = actionPointFactory.create("ChainAP",arrayList, 1);
        assertNotNull(chainAP2);

        assertTrue(((Rectangle)(chainAP2.getBody())).equals(new Rectangle(10, 10, Constants.action_point_width, Constants.action_point_height)));

        assertTrue(chainAP2.getNext().equals(chainAP2));

        arrayList.add(new Vector2(20, 20));
        arrayList.add(new Vector2(30, 40));

        ActionPoint chainAP3 = actionPointFactory.create("ChainAP", arrayList, 3);
        assertNotNull(chainAP3);
        ActionPoint current = chainAP3.first_;
        while(!current.getNext().equals(current))
        {
            assertNotNull(current.getNext());
            assertNotNull(current.first_);
            current = current.getNext();
        }
        arrayList.clear();
        ActionPoint chainAP5 = actionPointFactory.create("ChainAP", arrayList, 3);
        assertNotNull(chainAP5);
        ActionPoint current2 = chainAP3.first_;
        while(!current2.getNext().equals(current2))
        {
            assertNotNull(current2.getNext());
            assertNotNull(current2.first_);
            current2 = current2.getNext();
        }


    }
}