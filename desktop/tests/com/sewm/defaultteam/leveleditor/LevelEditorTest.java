package com.sewm.defaultteam.leveleditor;

import org.junit.Test;

import java.awt.AWTException;
import java.io.File;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;

import static com.sewm.defaultteam.leveleditor.LevelEditor.*;

public class LevelEditorTest extends UITest {
    @Test
    public void testPlaceSomething() throws ComponentNotFoundException, MultipleComponentsFoundException, InterruptedException, AWTException {
        int itemsBefore = editor.getFile().getItems().size();

        // select the normal enemy easy in tools list
        selectTool(NORMAL_ENEMY_EASY);

        // place the enemy in the center of the screen
        getRobot().click(canvas);

        getRobot().waitForIdle();
        assertEquals(itemsBefore + 1, editor.getFile().getItems().size());
    }

    @Test
    public void testPlaceEnemies() throws ComponentNotFoundException, MultipleComponentsFoundException, InterruptedException, AWTException {
        int itemsBefore = editor.getFile().getItems().size();

        // select the normal enemy hard in tools list
        selectTool(NORMAL_ENEMY_HARD);

        // place 4 enemies in the corners
        getRobot().click(canvas, canvas.getWidth()*1/4, canvas.getHeight()*1/4);
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*1/4);
        getRobot().click(canvas, canvas.getWidth()*1/4, canvas.getHeight()*3/4);
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*3/4);

        getRobot().waitForIdle();
        assertEquals(itemsBefore + 4, editor.getFile().getItems().size());

        int hardEnemies = 0;
        for (LevelEditorItem item : editor.getFile().getItems()) {
            if (item.getName().equals(NORMAL_ENEMY_HARD)) {
                hardEnemies++;
            }
        }
        assertEquals(4, hardEnemies);
    }

    @Test
    public void testPlaceAndDelete() throws ComponentNotFoundException, MultipleComponentsFoundException {
        int itemsBefore = editor.getFile().getItems().size();

        // select the static enemy easy in tools list
        selectTool(STATIC_ENEMY_EASY);

        // place enemy in corner
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*3/4);

        getRobot().waitForIdle();
        assertEquals(itemsBefore + 1, editor.getFile().getItems().size());

        // select delete tool
        selectTool(DELETE);

        // delete previously placed enemy
        getRobot().click(canvas, canvas.getWidth()*3/4, canvas.getHeight()*3/4);

        getRobot().waitForIdle();
        assertEquals(itemsBefore, editor.getFile().getItems().size());
    }

    @Test
    public void testDeletePlayer() throws ComponentNotFoundException, MultipleComponentsFoundException {
        int itemsBefore = editor.getFile().getItems().size();

        // select delete tool
        selectTool(DELETE);

        // try to delete the player
        getRobot().click(canvas);

        getRobot().waitForIdle();
        assertEquals(itemsBefore, editor.getFile().getItems().size());
    }

    @Test
    public void testSaveAndSaveAsFile() throws ComponentNotFoundException, MultipleComponentsFoundException {
        final String testFileSave = "testFileSave.lvl";
        final String testFileSaveAs = "testFileSaveAs.lvl";
        int expectedItems = editor.getFile().getItems().size();

        // place some things
        selectTool(CHAIN_ACTION_POINT);
        for (int i = 1; i < 8; i++) {
            getRobot().click(canvas, canvas.getWidth() * i / 8, canvas.getHeight() / 8);
            expectedItems++;
        }

        selectTool(TARGET);
        for (int i = 1; i < 8; i++) {
            getRobot().click(canvas, canvas.getWidth() * i / 8, canvas.getHeight() / 4);
            expectedItems++;
        }

        getRobot().waitForIdle();
        assertEquals(expectedItems, editor.getFile().getItems().size());

        // save file
        fileAction(FILE_SAVE);
        selectFile(testFileSave);
        File savedFile = new File("tests/" + testFileSave);
        assertTrue(savedFile.isFile());
        savedFile.delete();

        selectTool(NORMAL_ENEMY_HARD);
        for (int i = 1; i < 8; i++) {
            getRobot().click(canvas, canvas.getWidth() * i / 8, canvas.getHeight() * 3 / 4);
            expectedItems++;
        }
        getRobot().waitForIdle();
        assertEquals(expectedItems, editor.getFile().getItems().size());

        fileAction(FILE_SAVE);
        assertTrue(savedFile.isFile());
        savedFile.delete();

        // save as file
        fileAction(FILE_SAVE_AS);
        selectFile(testFileSaveAs);
        savedFile = new File("tests/" + testFileSaveAs);
        assertTrue(savedFile.isFile());
        savedFile.delete();
    }

    @Test
    public void testSaveAndOpenFile() throws ComponentNotFoundException, MultipleComponentsFoundException {
        final String testFileSave = "testFileSave.lvl";
        int initialItems = editor.getFile().getItems().size();
        int expectedItems = initialItems;

        // place some things
        selectTool(NORMAL_ENEMY_EASY);
        for (int i = 1; i < 8; i++) {
            getRobot().click(canvas, canvas.getWidth() * i / 8, canvas.getHeight() / 3);
            expectedItems++;
        }
        selectTool(STATIC_ENEMY_EASY);
        for (int i = 1; i < 8; i++) {
            getRobot().click(canvas, canvas.getWidth() * i / 8, canvas.getHeight() / 4);
            expectedItems++;
        }

        getRobot().waitForIdle();
        assertEquals(expectedItems, editor.getFile().getItems().size());

        // save file
        fileAction(FILE_SAVE);
        selectFile(testFileSave);
        File savedFile = new File("tests/" + testFileSave);
        assertTrue(savedFile.isFile());

        // new file
        fileAction(FILE_NEW);
        assertEquals(initialItems, editor.getFile().getItems().size());

        // open file
        fileAction(FILE_OPEN);
        selectFile(testFileSave);
        assertEquals(expectedItems, editor.getFile().getItems().size());

        savedFile.delete();
    }
}