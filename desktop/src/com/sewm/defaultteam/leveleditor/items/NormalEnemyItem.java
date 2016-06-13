package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

    public NormalEnemyItem(String texture_name, Vector2 position, int difficulty, int points, int points_on_death, int spawn_time) {
        super(texture_name, position);
        difficulty_ = difficulty;
        points_ = points;
        points_on_death_ = points_on_death;
        spawn_time_ = spawn_time;
        createPanel();
    }

    private void createPanel() {
        JTextField position_x = new JTextField();
        Float pos_x = this.position_.x;
        position_x.setText(pos_x.toString());
        properties_panel_.add(new JLabel("X-Position"));
        properties_panel_.add(position_x);

        JTextField position_y = new JTextField();
        Float pos_y = this.position_.y;
        position_y.setText(pos_y.toString());
        properties_panel_.add(new JLabel("Y-Position"));
        properties_panel_.add(position_y);

        //TODO convert Int to string
        JTextField points = new JTextField();
        points.setText(points_.toString());
        properties_panel_.add(new JLabel("Points"));
        properties_panel_.add(points);

        JTextField points_on_death = new JTextField();
        points_on_death.setText(points_on_death_.toString());
        properties_panel_.add(new JLabel("Points on Death"));
        properties_panel_.add(points_on_death);

        JTextField spawn_time = new JTextField();
        spawn_time.setText(spawn_time_.toString());
        properties_panel_.add(new JLabel("Spawn Time"));
        properties_panel_.add(spawn_time);
    }

    public JPanel getPropertiesPanel() {
        return properties_panel_;
    }
}