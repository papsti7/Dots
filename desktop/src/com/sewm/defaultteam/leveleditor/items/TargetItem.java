package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditor;
import com.sewm.defaultteam.leveleditor.LevelEditorFile;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

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

            final JFormattedTextField radius = new JFormattedTextField(LevelEditorFile.FLOAT);
            radius.setText(LevelEditorFile.FLOAT.valueToString(radius_));
            radius.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        radius_ = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Radius"));
            vertical.add(radius);

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
        Element node = document.createElement("target");

        node.setAttribute("x", String.valueOf(getX()));
        node.setAttribute("y", String.valueOf(getY()));
        node.setAttribute("radius", String.valueOf(getRadius()));
        node.setAttribute("health", String.valueOf(getHealth()));

        return node;
    }
}
