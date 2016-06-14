package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        final JTextField position_x_start = new JTextField();
        final Float pos_x_start = this.getStartX();
        position_x_start.setText(pos_x_start.toString());
        properties_panel_.add(new JLabel("X-Position Start"));
        properties_panel_.add(position_x_start);

        final JTextField position_y_start = new JTextField();
        final Float pos_y_start = this.getStartY();
        position_y_start.setText(pos_y_start.toString());
        properties_panel_.add(new JLabel("Y-Position Start"));
        properties_panel_.add(position_y_start);

        final JTextField position_x_end = new JTextField();
        final Float pos_x_end = this.getEndX();
        position_x_end.setText(pos_x_end.toString());
        properties_panel_.add(new JLabel("X-Position End"));
        properties_panel_.add(position_x_end);

        final JTextField position_y_end = new JTextField();
        final Float pos_y_end = this.getEndY();
        position_y_end.setText(pos_y_end.toString());
        properties_panel_.add(new JLabel("Y-Position End"));
        properties_panel_.add(position_y_end);

        final JTextField points = new JTextField();
        points.setText(String.valueOf(points_));
        properties_panel_.add(new JLabel("Points"));
        properties_panel_.add(points);

        final JTextField points_on_death = new JTextField();
        points_on_death.setText(String.valueOf(points_on_death_));
        properties_panel_.add(new JLabel("Points on Death"));
        properties_panel_.add(points_on_death);

        final JTextField spawn_time = new JTextField();
        spawn_time.setText(String.valueOf(spawn_time_));
        properties_panel_.add(new JLabel("Spawn Time"));
        properties_panel_.add(spawn_time);

        JButton btn_save = new JButton("Save");
        btn_save.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                position_.x = Float.parseFloat(position_x_start.getText());
                position_.y = Float.parseFloat(position_y_start.getText());
                end_.x = Float.parseFloat(position_x_end.getText());
                end_.y = Float.parseFloat(position_y_end.getText());
                points_on_death_ = Integer.parseInt(points_on_death.getText());
                points_= Integer.parseInt(points.getText());
                spawn_time_= Integer.parseInt(spawn_time.getText());
            }
        });
        properties_panel_.add(btn_save);
    }


}
