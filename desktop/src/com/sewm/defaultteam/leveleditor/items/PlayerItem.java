package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.Constants;
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

public class PlayerItem extends LevelEditorItem {
    protected int health_;

    public int getHealth() {
        return health_;
    }

    public PlayerItem(LevelEditor editor, int health) {
        super(editor, LevelEditor.PLAYER, new Vector2(Constants.virtual_screen_width/2f, Constants.virtual_screen_height/2f));
        health_ = health;
        createPanel();
    }

    private void createPanel() {
        try {
            Box vertical = Box.createVerticalBox();

            final JFormattedTextField health = new JFormattedTextField(LevelEditorFile.INT);
            health.setText(LevelEditorFile.INT.valueToString(health_));
            health.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        health_ = Integer.parseInt(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Health"));
            vertical.add(health);

            properties_panel_.add(vertical);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Element toXML(Document document) {
        Element node = document.createElement("player");
        node.setAttribute("health", String.valueOf(getHealth()));
        return node;
    }
}
