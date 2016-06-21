package com.sewm.defaultteam.leveleditor;


import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LevelEditorProperties {
    final private LevelEditor editor_;
    private LevelEditorItem currently_selected_item;

    public LevelEditorProperties(LevelEditor editor) {
        editor_ = editor;
    }

    public LevelEditorItem getSelectedItem() {
        return currently_selected_item;
    }

    public void setSelectedItem(LevelEditorItem item) {
        currently_selected_item = item;
        JScrollPane panel = editor_.getPropertiesPanel();
        JList<LevelEditorItem> list = editor_.getItemList();

        if (item != null) {
            JPanel properties = item.getPropertiesPanel();
            panel.setViewportView(properties);
            list.setSelectedValue(item, true);
        } else {
            panel.getViewport().removeAll();
            list.clearSelection();
        }
        editor_.getFrame().revalidate();
    }
}
