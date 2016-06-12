package com.sewm.defaultteam.leveleditor;

import org.junit.Test;

import java.awt.AWTException;
import java.awt.Canvas;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.ComponentLocation;
import abbot.tester.JTreeTester;

public class LevelEditorTest extends UITest {
    @Test
    public void testPlaceSomething() throws ComponentNotFoundException, MultipleComponentsFoundException, InterruptedException, AWTException {
        int itemsBefore = editor.getItems().size();

        // select the normal enemy easy in tools list
        JTree tools = (JTree) getFinder().find(new ClassMatcher(JTree.class));
        assertNotNull(tools);
        TreePath path = tools.getNextMatch("NormalEnemyEasy", 0, null);
        assertNotNull(path);
        JTreeTester menuTester = new JTreeTester();
        menuTester.actionSelectPath(tools, path);

        // place the enemy in the center of the screen
        Canvas canvas = (Canvas) getFinder().find(new ClassMatcher(Canvas.class));
        getRobot().click(canvas);

        getRobot().waitForIdle();
        assertEquals(itemsBefore + 1, editor.getItems().size());
    }

    @Test
    public void testPlaceEnemies() throws ComponentNotFoundException, MultipleComponentsFoundException, InterruptedException, AWTException {
        int itemsBefore = editor.getItems().size();

        // select the normal enemy easy in tools list
        JTree tools = (JTree) getFinder().find(new ClassMatcher(JTree.class));
        assertNotNull(tools);
        TreePath path = tools.getNextMatch("NormalEnemyHard", 0, null);
        assertNotNull(path);
        JTreeTester menuTester = new JTreeTester();
        menuTester.actionSelectPath(tools, path);

        // place 4 enemies in the corners
        Canvas canvas = (Canvas) getFinder().find(new ClassMatcher(Canvas.class));
        getRobot().click(canvas, canvas.getWidth()*1/4, canvas.getHeight()*1/4);
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*1/4);
        getRobot().click(canvas, canvas.getWidth()*1/4, canvas.getHeight()*3/4);
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*3/4);

        getRobot().waitForIdle();
        assertEquals(itemsBefore + 4, editor.getItems().size());

        int hardEnemies = 0;
        for (LevelEditorItem item : editor.getItems()) {
            if (item.getName().equals("NormalEnemyHard")) {
                hardEnemies++;
            }
        }
        assertEquals(4, hardEnemies);
    }
}