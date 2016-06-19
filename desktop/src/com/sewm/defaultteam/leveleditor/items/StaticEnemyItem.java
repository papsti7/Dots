package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditor;
import com.sewm.defaultteam.leveleditor.LevelEditorCanvasRenderer;
import com.sewm.defaultteam.leveleditor.LevelEditorFile;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

public class StaticEnemyItem extends LevelEditorItem {
    protected int difficulty_;
    protected int points_;
    protected int points_on_death_;
    protected int spawn_time_;
    protected Vector2 end_;

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

    public float getStartX() {
        return getX();
    }

    public float getStartY() {
        return getY();
    }

    public float getEndX() {
        return end_.x;
    }

    public float getEndY() {
        return end_.y;
    }

    public StaticEnemyItem(LevelEditor editor, Vector2 start, Vector2 end, int difficulty, int points, int points_on_death, int spawn_time) {
        super(editor, textureNameFromDifficulty(difficulty), start);
        end_ = end;
        difficulty_ = difficulty;
        points_ = points;
        points_on_death_ = points_on_death;
        spawn_time_ = spawn_time;
        createPanel();
    }

    private static String textureNameFromDifficulty(int difficulty) {
        switch (difficulty) {
            case 1:
                return LevelEditor.STATIC_ENEMY_MEDIUM;
            case 2:
                return LevelEditor.STATIC_ENEMY_HARD;
            default:
                return LevelEditor.STATIC_ENEMY_EASY;
        }
    }

    private void createPanel() {
        try {
            Box vertical = Box.createVerticalBox();

            final JFormattedTextField position_x_start = new JFormattedTextField(LevelEditorFile.FLOAT);
            position_x_start.setText(LevelEditorFile.FLOAT.valueToString(position_.x));
            position_x_start.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        position_.x = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("X-Position Start"));
            vertical.add(position_x_start);

            final JFormattedTextField position_y_start = new JFormattedTextField(LevelEditorFile.FLOAT);
            position_y_start.setText(LevelEditorFile.FLOAT.valueToString(position_.y));
            position_y_start.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        position_.y = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Y-Position Start"));
            vertical.add(position_y_start);

            final JFormattedTextField position_x_end = new JFormattedTextField(LevelEditorFile.FLOAT);
            position_x_end.setText(LevelEditorFile.FLOAT.valueToString(end_.x));
            position_x_end.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        end_.x = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("X-Position End"));
            vertical.add(position_x_end);

            final JFormattedTextField position_y_end = new JFormattedTextField(LevelEditorFile.FLOAT);
            position_y_end.setText(LevelEditorFile.FLOAT.valueToString(end_.y));
            position_y_end.addPropertyChangeListener("value", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if (!e.getNewValue().equals(e.getOldValue())) {
                        end_.y = Float.parseFloat(e.getNewValue().toString());
                        editor_.getFile().setDirty(true);
                    }
                }
            });
            vertical.add(new JLabel("Y-Position End"));
            vertical.add(position_y_end);

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

        node.setAttribute("type", "static");
        node.setAttribute("difficulty", String.valueOf(getDifficulty()));
        node.setAttribute("points", String.valueOf(getPoints()));
        node.setAttribute("pointsOnDeath", String.valueOf(getPointsOnDeath()));
        node.setAttribute("spawnTime", String.valueOf(getSpawnTime()));

        Element start = document.createElement("start");
        start.setAttribute("x", String.valueOf(getStartX()));
        start.setAttribute("y", String.valueOf(getStartY()));
        node.appendChild(start);

        Element end = document.createElement("end");
        end.setAttribute("x", String.valueOf(getEndX()));
        end.setAttribute("y", String.valueOf(getEndY()));
        node.appendChild(end);

        return node;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
        if (this.equals(editor_.getProperties().getSelectedItem())) {
            Texture texture = LevelEditorCanvasRenderer.textures_.get(name);
            spriteBatch.draw(texture,
                    end_.x - texture.getWidth() / 2.f,
                    end_.y - texture.getHeight() / 2.f,
                    texture.getWidth(), texture.getHeight());
        }
    }
}
