package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditor;
import com.sewm.defaultteam.leveleditor.LevelEditorFile;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class ChainActionPointItem extends LevelEditorItem {
    private Vector2[] positions_;

    public ChainActionPointItem(LevelEditor editor, String texture_name, Vector2 position, Vector2... positions) {
        super(editor, texture_name, position);
        positions_ = positions;
        createPanel();
    }

    private void createPanel() {
        try {
            Box vertical = Box.createVerticalBox();

            final JFormattedTextField position_x = new JFormattedTextField(LevelEditorFile.FLOAT);
            position_x.setText(LevelEditorFile.FLOAT.valueToString(position_.x));
            position_x.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        position_.x = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("X-Position"));
            vertical.add(position_x);

            final JFormattedTextField position_y = new JFormattedTextField(LevelEditorFile.FLOAT);
            position_y.setText(LevelEditorFile.FLOAT.valueToString(position_.y));
            position_y.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        position_.y = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Y-Position"));
            vertical.add(position_y);

            //TODO further positions?

            properties_panel_.add(vertical);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Element toXML(Document document) {
        Element node = document.createElement("actionPoint");
        node.setAttribute("type", "chain");

        Element position = document.createElement("position");
        position.setAttribute("x", String.valueOf(getX()));
        position.setAttribute("y", String.valueOf(getY()));
        node.appendChild(position);

        for (Vector2 pos : positions_) {
            position = document.createElement("position");
            position.setAttribute("x", String.valueOf(pos.x));
            position.setAttribute("y", String.valueOf(pos.y));
            node.appendChild(position);
        }

        return node;
    }
}
