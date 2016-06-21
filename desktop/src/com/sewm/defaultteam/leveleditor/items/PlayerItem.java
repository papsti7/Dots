package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.Constants;
import com.sewm.defaultteam.leveleditor.LevelEditor;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

    @Override
    protected void createPanel() {
        Box vertical = Box.createVerticalBox();

        final JSpinner health = new JSpinner();
        health.setModel(new SpinnerNumberModel(health_, 1, null, 1));
        health.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (health.getPreviousValue() != health.getValue()) {
                    health_ = Integer.parseInt(health.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("Health"));
        vertical.add(health);

        properties_panel_.add(vertical);
    }

    @Override
    public Element toXML(Document document) {
        Element node = document.createElement("player");
        node.setAttribute("health", String.valueOf(getHealth()));
        return node;
    }

    @Override
    public void move(Vector2 to) {
        // not allowed to move player
    }
}
