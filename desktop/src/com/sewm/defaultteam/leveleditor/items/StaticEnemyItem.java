package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

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

    public StaticEnemyItem(String texture_name, Vector2 start, Vector2 end, int difficulty, int points, int points_on_death, int spawn_time) {
        super(texture_name, start);
        end_ = end;
        difficulty_ = difficulty;
        points_ = points;
        points_on_death_ = points_on_death;
        spawn_time_ = spawn_time;
        createPanel();
    }

    private void createPanel() {
        JTextField position_x_start = new JTextField();
        Float pos_x_start = this.getStartX();
        position_x_start.setText(pos_x_start.toString());
        properties_panel_.add(new JLabel("X-Position Start"));
        properties_panel_.add(position_x_start);

        JTextField position_y_start = new JTextField();
        Float pos_y_start = this.getStartY();
        position_y_start.setText(pos_y_start.toString());
        properties_panel_.add(new JLabel("Y-Position Start"));
        properties_panel_.add(position_y_start);

        JTextField position_x_end = new JTextField();
        Float pos_x_end = this.getEndX();
        position_x_end.setText(pos_x_end.toString());
        properties_panel_.add(new JLabel("X-Position End"));
        properties_panel_.add(position_x_end);

        JTextField position_y_end = new JTextField();
        Float pos_y_end = this.getEndY();
        position_y_end.setText(pos_y_end.toString());
        properties_panel_.add(new JLabel("Y-Position End"));
        properties_panel_.add(position_y_end);
        
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
}
