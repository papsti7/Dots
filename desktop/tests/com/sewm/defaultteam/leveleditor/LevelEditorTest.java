package com.sewm.defaultteam.leveleditor;

import org.junit.Test;

import java.awt.AWTException;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;

public class LevelEditorTest extends UITest {
    @Test
    public void testPlaceSomething() throws ComponentNotFoundException, MultipleComponentsFoundException, InterruptedException, AWTException {
        int itemsBefore = editor.getFile().getItems().size();

        // select the normal enemy easy in tools list
        selectTool("NormalEnemyEasy");

        // place the enemy in the center of the screen
        getRobot().click(canvas);

        getRobot().waitForIdle();
        assertEquals(itemsBefore + 1, editor.getFile().getItems().size());
    }

    @Test
    public void testPlaceEnemies() throws ComponentNotFoundException, MultipleComponentsFoundException, InterruptedException, AWTException {
        int itemsBefore = editor.getFile().getItems().size();

        // select the normal enemy hard in tools list
        selectTool("NormalEnemyHard");

        // place 4 enemies in the corners
        getRobot().click(canvas, canvas.getWidth()*1/4, canvas.getHeight()*1/4);
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*1/4);
        getRobot().click(canvas, canvas.getWidth()*1/4, canvas.getHeight()*3/4);
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*3/4);

        getRobot().waitForIdle();
        assertEquals(itemsBefore + 4, editor.getFile().getItems().size());

        int hardEnemies = 0;
        for (LevelEditorItem item : editor.getFile().getItems()) {
            if (item.getName().equals("NormalEnemyHard")) {
                hardEnemies++;
            }
        }
        assertEquals(4, hardEnemies);
    }

    @Test
    public void testPlaceAndDelete() throws ComponentNotFoundException, MultipleComponentsFoundException {
        int itemsBefore = editor.getFile().getItems().size();

        // select the static enemy easy in tools list
        selectTool("StaticEnemyEasy");

        // place enemy in corner
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*3/4);

        getRobot().waitForIdle();
        assertEquals(itemsBefore + 1, editor.getFile().getItems().size());

        // select delete tool
        selectTool("Delete");

        // delete previously placed enemy
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*3/4);

        getRobot().waitForIdle();
        assertEquals(itemsBefore, editor.getFile().getItems().size());
    }

    @Test
    public void testDeletePlayer() throws ComponentNotFoundException, MultipleComponentsFoundException {
        int itemsBefore = editor.getFile().getItems().size();

        // select delete tool
        selectTool("Delete");

        // try to delete the player
        getRobot().click(canvas);

        getRobot().waitForIdle();
        assertEquals(itemsBefore, editor.getFile().getItems().size());
    }
}