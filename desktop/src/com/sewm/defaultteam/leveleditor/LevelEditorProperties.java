package com.sewm.defaultteam.leveleditor;


import com.sewm.defaultteam.leveleditor.items.TargetItem;

import javax.swing.BoxLayout;
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
        panel.setColumnHeaderView(new JLabel(item.getName()));

        JPanel properties_panel = item.getPropertiesPanel();
        properties_panel.setLayout(new BoxLayout(properties_panel, BoxLayout.Y_AXIS));
        panel.setViewportView(properties_panel);
    }
}
