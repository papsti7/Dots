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
import javax.swing.JPanel;

public class NormalEnemyItem extends LevelEditorItem {
    protected int difficulty_;
    protected int points_;
    protected int points_on_death_;
    protected int spawn_time_;

    public int getDifficulty() {
        return difficulty_;
    }

    public int getPoints() {
        return points_;
    }

    public int getPointsOnDeath() {
        return points_on_death_;
    }

    public int getSpawnTime() {
        return spawn_time_;
    }

    public NormalEnemyItem(LevelEditor editor, Vector2 position, int difficulty, int points, int points_on_death, int spawn_time) {
        super(editor, textureNameFromDifficulty(difficulty), position);
        difficulty_ = difficulty;
        points_ = points;
        points_on_death_ = points_on_death;
        spawn_time_ = spawn_time;
        createPanel();
    }

    private static String textureNameFromDifficulty(int difficulty) {
        switch (difficulty) {
            case 1:
                return LevelEditor.NORMAL_ENEMY_MEDIUM;
            case 2:
                return LevelEditor.NORMAL_ENEMY_HARD;
            default:
                return LevelEditor.NORMAL_ENEMY_EASY;
        }
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

            final JFormattedTextField points = new JFormattedTextField(LevelEditorFile.INT);
            points.setText(LevelEditorFile.INT.valueToString(points_));
            points.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        points_ = Integer.parseInt(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Points"));
            vertical.add(points);

            final JFormattedTextField points_on_death = new JFormattedTextField(LevelEditorFile.INT);
            points_on_death.setText(LevelEditorFile.INT.valueToString(points_on_death_));
            points_on_death.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        points_on_death_ = Integer.parseInt(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Points on Death"));
            vertical.add(points_on_death);

            final JFormattedTextField spawn_time = new JFormattedTextField(LevelEditorFile.INT);
            spawn_time.setText(LevelEditorFile.INT.valueToString(spawn_time_));
            spawn_time.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        spawn_time_ = Integer.parseInt(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Spawn Time"));
            vertical.add(spawn_time);

            properties_panel_.add(vertical);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Element toXML(Document document) {
        Element node = document.createElement("enemy");

        node.setAttribute("type", "normal");
        node.setAttribute("x", String.valueOf(getX()));
        node.setAttribute("y", String.valueOf(getY()));
        node.setAttribute("difficulty", String.valueOf(getDifficulty()));
        node.setAttribute("points", String.valueOf(getPoints()));
        node.setAttribute("pointsOnDeath", String.valueOf(getPointsOnDeath()));
        node.setAttribute("spawnTime", String.valueOf(getSpawnTime()));

        return node;
    }
}
