package com.sewm.defaultteam.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
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
            Vector2 pos = new Vector2(e.getX() * Constants.virtual_screen_width / editor_.getCanvas().getWidth(),
                    Constants.virtual_screen_height - e.getY() * Constants.virtual_screen_height / editor_.getCanvas().getHeight());
            item.clicked(pos);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }
}
