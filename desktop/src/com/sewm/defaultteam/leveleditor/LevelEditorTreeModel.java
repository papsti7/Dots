package com.sewm.defaultteam.leveleditor;

import java.awt.Cursor;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.sewm.defaultteam.leveleditor.LevelEditorTool.ACTION;
import static com.sewm.defaultteam.leveleditor.LevelEditor.*;

public class LevelEditorTreeModel extends DefaultTreeModel {
    public LevelEditorTreeModel(LevelEditor e, Map<String, Icon> icons, Map<String, Cursor> cursors) throws NoSuchMethodException, SecurityException {
        super(new DefaultMutableTreeNode("Dots"));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getRoot();

        root.add(new LevelEditorTool(e, "Select", null, Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), ACTION.SELECT));
        root.add(new LevelEditorTool(e, "Move", null, Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR), ACTION.MOVE));
        root.add(new LevelEditorTool(e, "Delete", null, Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), ACTION.DELETE));
        //root.add(new LevelEditorTool(e, PLAYER, icons.get(PLAYER), cursors.get(PLAYER), ACTION.PLACE));
        root.add(new LevelEditorTool(e, TARGET, icons.get(TARGET), cursors.get(TARGET), ACTION.PLACE));

        DefaultMutableTreeNode folder = new DefaultMutableTreeNode("Enemies");
        DefaultMutableTreeNode subfolder = new DefaultMutableTreeNode("Normal Enemies");
        subfolder.add(new LevelEditorTool(e, NORMAL_ENEMY_EASY, icons.get(NORMAL_ENEMY_EASY), cursors.get(NORMAL_ENEMY_EASY), ACTION.PLACE));
        subfolder.add(new LevelEditorTool(e, NORMAL_ENEMY_MEDIUM, icons.get(NORMAL_ENEMY_MEDIUM), cursors.get(NORMAL_ENEMY_MEDIUM), ACTION.PLACE));
        subfolder.add(new LevelEditorTool(e, NORMAL_ENEMY_HARD, icons.get(NORMAL_ENEMY_HARD), cursors.get(NORMAL_ENEMY_HARD), ACTION.PLACE));
        folder.add(subfolder);
        subfolder = new DefaultMutableTreeNode("StaticEnemy");
        subfolder.add(new LevelEditorTool(e, STATIC_ENEMY_EASY, icons.get(STATIC_ENEMY_EASY), cursors.get(STATIC_ENEMY_EASY), ACTION.PLACE));
        subfolder.add(new LevelEditorTool(e, STATIC_ENEMY_MEDIUM, icons.get(STATIC_ENEMY_MEDIUM), cursors.get(STATIC_ENEMY_MEDIUM), ACTION.PLACE));
        subfolder.add(new LevelEditorTool(e, STATIC_ENEMY_HARD, icons.get(STATIC_ENEMY_HARD), cursors.get(STATIC_ENEMY_HARD), ACTION.PLACE));
        folder.add(subfolder);
        root.add(folder);

        folder = new DefaultMutableTreeNode("ActionPoints");
        folder.add(new LevelEditorTool(e, CHAIN_ACTION_POINT, icons.get(CHAIN_ACTION_POINT), cursors.get(CHAIN_ACTION_POINT), ACTION.PLACE));
        root.add(folder);
    }
}
