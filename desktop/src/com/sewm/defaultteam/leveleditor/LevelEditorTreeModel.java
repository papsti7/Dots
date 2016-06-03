package com.sewm.defaultteam.leveleditor;

import java.awt.Cursor;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class LevelEditorTreeModel extends DefaultTreeModel {
    public LevelEditorTreeModel(Map<String, Icon> icons, Map<String, Cursor> cursors) {
        super(new DefaultMutableTreeNode("Dots"));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getRoot();

        root.add(new DefaultMutableTreeNode("Select"));
        root.add(new DefaultMutableTreeNode("Move"));
        root.add(new LevelEditorTool("Player", icons.get("Player"), cursors.get("Player")));
        root.add(new LevelEditorTool("Target", icons.get("Target"), cursors.get("Target")));

        DefaultMutableTreeNode folder = new DefaultMutableTreeNode("Enemies");
        DefaultMutableTreeNode subfolder = new DefaultMutableTreeNode("Normal Enemies");
        subfolder.add(new LevelEditorTool("Easy", icons.get("NormalEnemyEasy"), cursors.get("NormalEnemyEasy")));
        subfolder.add(new LevelEditorTool("Medium", icons.get("NormalEnemyMedium"), cursors.get("NormalEnemyMedium")));
        subfolder.add(new LevelEditorTool("Hard", icons.get("NormalEnemyHard"), cursors.get("NormalEnemyHard")));
        folder.add(subfolder);
        subfolder = new DefaultMutableTreeNode("StaticEnemy");
        subfolder.add(new LevelEditorTool("Easy", icons.get("StaticEnemyEasy"), cursors.get("StaticEnemyEasy")));
        subfolder.add(new LevelEditorTool("Medium", icons.get("StaticEnemyMedium"), cursors.get("StaticEnemyMedium")));
        subfolder.add(new LevelEditorTool("Hard", icons.get("StaticEnemyHard"), cursors.get("StaticEnemyHard")));
        folder.add(subfolder);
        root.add(folder);

        folder = new DefaultMutableTreeNode("ActionPoints");
        folder.add(new LevelEditorTool("ChainActionPoint", icons.get("ChainActionPoint"), cursors.get("ChainActionPoint")));
        root.add(folder);
    }
}
