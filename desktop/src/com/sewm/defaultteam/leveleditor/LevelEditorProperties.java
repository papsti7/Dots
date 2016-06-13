package com.sewm.defaultteam.leveleditor;


import com.sewm.defaultteam.leveleditor.items.TargetItem;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class LevelEditorProperties {
    final private LevelEditor editor_;
    private LevelEditorItem currently_selected_item;

    public LevelEditorProperties(LevelEditor editor) {
        editor_ = editor;
    }

    public void setSelectedItem(LevelEditorItem item) {
        currently_selected_item = item;
        JScrollPane panel = editor_.getPropertiesPanel();
        panel.setColumnHeaderView(item.getPropertiesPanel());
    }
}
