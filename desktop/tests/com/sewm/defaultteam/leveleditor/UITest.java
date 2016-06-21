package com.sewm.defaultteam.leveleditor;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.After;
import org.junit.Before;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import abbot.finder.AWTHierarchy;
import abbot.finder.BasicFinder;
import abbot.finder.ComponentFinder;
import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.ClassMatcher;
import abbot.finder.matchers.JMenuItemMatcher;
import abbot.finder.matchers.WindowMatcher;
import abbot.tester.JComponentTester;
import abbot.tester.JFileChooserTester;
import abbot.tester.JTreeTester;

import static com.sewm.defaultteam.leveleditor.LevelEditor.*;

public abstract class UITest extends ComponentTestFixture {
    protected LevelEditor editor;
    protected JTree tools;
    protected JTreeTester toolsTester;
    protected JFileChooserTester dialogTester;
    protected Canvas canvas;

    public UITest() {
        super("UITest");
        if (!new File("images").isDirectory()) {
            fail("Please run this test with the android/assets folder as working directory!");
        }
    }

    @Before
    public void setUp() throws ComponentNotFoundException, MultipleComponentsFoundException, NoSuchMethodException {
        editor = new LevelEditor();
        editor.getFrame().setVisible(true);

        tools = (JTree) getFinder().find(new ClassMatcher(JTree.class));
        assertNotNull(tools);
        toolsTester = new JTreeTester();
        assertNotNull(toolsTester);
        dialogTester = new JFileChooserTester();
        assertNotNull(dialogTester);
        canvas = (Canvas) getFinder().find(new ClassMatcher(Canvas.class));
        assertNotNull(canvas);
    }

    protected void selectTool(String name) {
        TreePath path = tools.getNextMatch(name, 0, null);
        assertNotNull(path);
        toolsTester.actionSelectPath(tools, path);
    }

    protected void fileAction(final String action) throws ComponentNotFoundException, MultipleComponentsFoundException {
        JComponentTester tester = new JComponentTester();

        JMenu menu = (JMenu) getFinder().find(new ClassMatcher(JMenu.class));
        assertNotNull(menu);

        JMenuItem item = (JMenuItem) getFinder().find(new JMenuItemMatcher(action));
        assertNotNull(item);

        tester.actionClick(menu);
        tester.actionClick(item);
    }

    protected void selectFile(String name) throws ComponentNotFoundException, MultipleComponentsFoundException {
        JFileChooser dialog = (JFileChooser) getFinder().find(null, new ClassMatcher(JFileChooser.class, true));
        assertNotNull(dialog);

        dialogTester.actionSetDirectory(dialog, "tests/");
        dialogTester.actionSetFilename(dialog, name);
        dialogTester.actionApprove(dialog);
    }

    @After
    public void tearDown() {
        editor.getFrame().setVisible(false);
        editor.getFrame().dispose();
    }
}