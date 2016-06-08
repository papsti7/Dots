package com.sewm.defaultteam;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.mock;


/**
 * Created by Lisa on 08.06.2016.
 */
public class StartPointTest  {
    @BeforeClass
    public static void initGdx()
    {
        Gdx.app = mock(Application.class);
        Gdx.graphics = mock(Graphics.class);
    }


    @Test
    public void instanceTest() throws Exception {
        StartPoint.getInstance();
    }

}