package com.sewm.defaultteam.leveleditor;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.After;
import org.junit.Before;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Robot;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.JTreeTester;

public class UITest extends ComponentTestFixture {
    protected LevelEditor editor;
    protected JTree tools;
    protected JTreeTester toolsTester;
    protected Canvas canvas;

    public UITest() {
        super("UITest");
    }

    @Before
    public void setUp() throws NoSuchMethodException, ComponentNotFoundException, MultipleComponentsFoundException {
        editor = new LevelEditor();
        editor.getFrame().setVisible(true);

        tools = (JTree) getFinder().find(new ClassMatcher(JTree.class));
        assertNotNull(tools);
        toolsTester = new JTreeTester();
        assertNotNull(toolsTester);
        canvas = (Canvas) getFinder().find(new ClassMatcher(Canvas.class));
        assertNotNull(canvas);
    }

    protected void selectTool(String name) {
        TreePath path = tools.getNextMatch(name, 0, null);
        assertNotNull(path);
        toolsTester.actionSelectPath(tools, path);
    }

    @After
    public void tearDown() {
        editor.getFrame().setVisible(false);
        editor.getFrame().dispose();
    }
}