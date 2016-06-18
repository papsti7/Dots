package com.sewm.defaultteam.leveleditor;


import javax.swing.JLabel;
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

    public boolean isSelected() {
        return currently_selected_item != null;
    }

    public void setSelectedItem(LevelEditorItem item) {
        currently_selected_item = item;
        JScrollPane panel = editor_.getPropertiesPanel();
        JLabel label = (JLabel) panel.getColumnHeader().getView();

        if (item != null) {
            label.setText(item.getName());
            JPanel properties = item.getPropertiesPanel();
            panel.setViewportView(properties);
        } else {
            panel.getViewport().removeAll();
        }
        editor_.getFrame().revalidate();
    }
}
