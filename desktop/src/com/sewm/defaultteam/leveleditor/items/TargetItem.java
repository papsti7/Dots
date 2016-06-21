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

public class TargetItem extends LevelEditorItem {
    protected float radius_;
    protected int health_;

    public float getRadius() {
        return radius_;
    }

    public int getHealth() {
        return health_;
    }

    public TargetItem(LevelEditor editor, Vector2 position, float radius, int health) {
        super(editor, LevelEditor.TARGET, position);
        radius_ = radius;
        health_ = health;
        createPanel();
    }

    @Override
    protected void createPanel() {
        Box vertical = Box.createVerticalBox();

        final JSpinner position_x = new JSpinner();
        position_x.setModel(new SpinnerNumberModel((int)position_.x, 0, Constants.virtual_screen_width, 1));
        position_x.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (position_x.getPreviousValue() != position_x.getValue()) {
                    position_.x = Float.parseFloat(position_x.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("X-Position"));
        vertical.add(position_x);

        final JSpinner position_y = new JSpinner();
        position_y.setModel(new SpinnerNumberModel((int)position_.y, 0, Constants.virtual_screen_height, 1));
        position_y.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (position_y.getPreviousValue() != position_y.getValue()) {
                    position_.y = Float.parseFloat(position_y.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("Y-Position"));
        vertical.add(position_y);

        final JSpinner radius = new JSpinner();
        radius.setModel(new SpinnerNumberModel((int)radius_, null, null, 1));
        radius.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (radius.getPreviousValue() != radius.getValue()) {
                    radius_ = Float.parseFloat(radius.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("Radius"));
        vertical.add(radius);

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
        Element node = document.createElement("target");

        node.setAttribute("x", String.valueOf(getX()));
        node.setAttribute("y", String.valueOf(getY()));
        node.setAttribute("radius", String.valueOf(getRadius()));
        node.setAttribute("health", String.valueOf(getHealth()));

        return node;
    }
}
