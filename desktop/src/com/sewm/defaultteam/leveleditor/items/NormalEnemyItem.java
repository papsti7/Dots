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
            case 2:
                return LevelEditor.NORMAL_ENEMY_MEDIUM;
            case 3:
                return LevelEditor.NORMAL_ENEMY_HARD;
            default:
                return LevelEditor.NORMAL_ENEMY_EASY;
        }
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

        final JSpinner points = new JSpinner();
        points.setModel(new SpinnerNumberModel(points_, 0, null, 1));
        points.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (points.getPreviousValue() != points.getValue()) {
                    points_ = Integer.parseInt(points.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("Points"));
        vertical.add(points);

        final JSpinner points_on_death = new JSpinner();
        points_on_death.setModel(new SpinnerNumberModel(points_on_death_, 0, null, 1));
        points_on_death.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (points_on_death.getPreviousValue() != points_on_death.getValue()) {
                    points_on_death_ = Integer.parseInt(points_on_death.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("Points on Death"));
        vertical.add(points_on_death);

        final JSpinner spawn_time = new JSpinner();
        spawn_time.setModel(new SpinnerNumberModel(spawn_time_, 0, null, 1));
        spawn_time.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (spawn_time.getPreviousValue() != spawn_time.getValue()) {
                    spawn_time_ = Integer.parseInt(spawn_time.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("Spawn Time"));
        vertical.add(spawn_time);

        properties_panel_.add(vertical);
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
