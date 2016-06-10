package com.sewm.defaultteam.leveleditor;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.After;
import org.junit.Before;

import java.awt.EventQueue;
import java.awt.Robot;

public class UITest extends ComponentTestFixture {
    protected LevelEditor editor;

    public UITest() {
        super("UITest");
    }

    @Before
    public void setUp() throws NoSuchMethodException {
        editor = new LevelEditor();
        editor.getCanvas().setName("canvas1");
        editor.getFrame().setVisible(true);
    }

    @After
    public void tearDown() {
        editor.getFrame().setVisible(false);
        editor.getFrame().dispose();
    }
}