package com.sewm.defaultteam.leveleditor;

import com.sewm.defaultteam.Constants;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LevelEditorMouseListener implements MouseListener {
    final private LevelEditor editor_;

    public LevelEditorMouseListener(LevelEditor editor) {
        editor_ = editor;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        editor_.getFrame().setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        editor_.getFrame().setCursor(editor_.getCursor());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        LevelEditorTool item = editor_.getSelectedTool();
        if (item != null) {
            Point pos = e.getPoint();
            pos.setLocation(pos.getX() * Constants.virtual_screen_width / editor_.getCanvas().getWidth(),
                    pos.getY() * Constants.virtual_screen_height / editor_.getCanvas().getHeight());
            System.out.println("Place " + item.getUserObject().toString() + " at " + pos);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }
}
