package com.sewm.defaultteam.leveleditor;

import java.awt.Cursor;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.sewm.defaultteam.leveleditor.LevelEditorTool.ACTION;

public class LevelEditorTreeModel extends DefaultTreeModel {
    public LevelEditorTreeModel(LevelEditor e, Map<String, Icon> icons, Map<String, Cursor> cursors) throws NoSuchMethodException, SecurityException {
        super(new DefaultMutableTreeNode("Dots"));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getRoot();

        root.add(new LevelEditorTool(e, "Select", null, Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), ACTION.SELECT));
        root.add(new LevelEditorTool(e, "Move", null, Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR), ACTION.MOVE));
        root.add(new LevelEditorTool(e, "Delete", null, Cursor.getPredefinedCursor(Cursor.HAND_CURSOR), ACTION.DELETE));
        root.add(new LevelEditorTool(e, "Player", icons.get("Player"), cursors.get("Player"), ACTION.PLACE));
        root.add(new LevelEditorTool(e, "Target", icons.get("Target"), cursors.get("Target"), ACTION.PLACE));

        DefaultMutableTreeNode folder = new DefaultMutableTreeNode("Enemies");
        DefaultMutableTreeNode subfolder = new DefaultMutableTreeNode("Normal Enemies");
        subfolder.add(new LevelEditorTool(e, "NormalEnemyEasy", icons.get("NormalEnemyEasy"), cursors.get("NormalEnemyEasy"), ACTION.PLACE));
        subfolder.add(new LevelEditorTool(e, "NormalEnemyMedium", icons.get("NormalEnemyMedium"), cursors.get("NormalEnemyMedium"), ACTION.PLACE));
        subfolder.add(new LevelEditorTool(e, "NormalEnemyHard", icons.get("NormalEnemyHard"), cursors.get("NormalEnemyHard"), ACTION.PLACE));
        folder.add(subfolder);
        subfolder = new DefaultMutableTreeNode("StaticEnemy");
        subfolder.add(new LevelEditorTool(e, "StaticEnemyEasy", icons.get("StaticEnemyEasy"), cursors.get("StaticEnemyEasy"), ACTION.PLACE));
        subfolder.add(new LevelEditorTool(e, "StaticEnemyMedium", icons.get("StaticEnemyMedium"), cursors.get("StaticEnemyMedium"), ACTION.PLACE));
        subfolder.add(new LevelEditorTool(e, "StaticEnemyHard", icons.get("StaticEnemyHard"), cursors.get("StaticEnemyHard"), ACTION.PLACE));
        folder.add(subfolder);
        root.add(folder);

        folder = new DefaultMutableTreeNode("ActionPoints");
        folder.add(new LevelEditorTool(e, "ChainActionPoint", icons.get("ChainActionPoint"), cursors.get("ChainActionPoint"), ACTION.PLACE));
        root.add(folder);
    }
}
