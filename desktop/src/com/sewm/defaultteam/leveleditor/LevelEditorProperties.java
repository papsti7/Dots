package com.sewm.defaultteam.leveleditor;

import com.sewm.defaultteam.leveleditor.items.ChainActionPointItem;
import com.sewm.defaultteam.leveleditor.items.NormalEnemyItem;
import com.sewm.defaultteam.leveleditor.items.StaticEnemyItem;
import com.sewm.defaultteam.leveleditor.items.TargetItem;

import javax.swing.JScrollPane;

public class LevelEditorProperties {
    final private LevelEditor editor_;
    private LevelEditorItem currently_selected_item;

    public LevelEditorProperties(LevelEditor editor) {
        editor_ = editor;
    }

    public void setSelectedItem(LevelEditorItem item) {
        currently_selected_item = item;
        JScrollPane panel = editor_.getPropertiesPanel();
    }
}
