package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.Constants;
import com.sewm.defaultteam.leveleditor.LevelEditor;
import com.sewm.defaultteam.leveleditor.LevelEditorCanvasRenderer;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
            case 2:
                return LevelEditor.STATIC_ENEMY_MEDIUM;
            case 3:
                return LevelEditor.STATIC_ENEMY_HARD;
            default:
                return LevelEditor.STATIC_ENEMY_EASY;
        }
    }

    @Override
    protected void createPanel() {
        Box vertical = Box.createVerticalBox();

        final JSpinner position_x_start = new JSpinner();
        position_x_start.setModel(new SpinnerNumberModel((int)position_.x, 0, Constants.virtual_screen_width, 1));
        position_x_start.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (position_x_start.getPreviousValue() != position_x_start.getValue()) {
                    position_.x = Float.parseFloat(position_x_start.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("X-Position Start"));
        vertical.add(position_x_start);

        final JSpinner position_y_start = new JSpinner();
        position_y_start.setModel(new SpinnerNumberModel((int)position_.y, 0, Constants.virtual_screen_height, 1));
        position_y_start.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (position_y_start.getPreviousValue() != position_y_start.getValue()) {
                    position_.y = Float.parseFloat(position_y_start.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("Y-Position Start"));
        vertical.add(position_y_start);

        final JSpinner position_x_end = new JSpinner();
        position_x_end.setModel(new SpinnerNumberModel((int)end_.x, 0, Constants.virtual_screen_width, 1));
        position_x_end.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (position_x_end.getPreviousValue() != position_x_end.getValue()) {
                    end_.x = Float.parseFloat(position_x_end.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("X-Position End"));
        vertical.add(position_x_end);

        final JSpinner position_y_end = new JSpinner();
        position_y_end.setModel(new SpinnerNumberModel((int)end_.y, 0, Constants.virtual_screen_height, 1));
        position_y_end.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (position_y_end.getPreviousValue() != position_y_end.getValue()) {
                    end_.y = Float.parseFloat(position_y_end.getValue().toString());
                    editor_.getFile().setDirty(true);
                }
            }
        });
        vertical.add(new JLabel("Y-Position End"));
        vertical.add(position_y_end);

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
    public void move(Vector2 to) {
        float moveX = to.x - position_.x;
        float moveY = to.y - position_.y;
        end_.add(moveX, moveY);
        super.move(to);
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
