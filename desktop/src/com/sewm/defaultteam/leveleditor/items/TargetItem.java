package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class TargetItem extends LevelEditorItem {
    protected float radius_;
    protected int health_;

    public float getRadius() {
        return radius_;
    }

    public int getHealth() {
        return health_;
    }

    public TargetItem(String texture_name, Vector2 position, float radius, int health) {
        super(texture_name, position);
        radius_ = radius;
        health_ = health;
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

        JTextField radius = new JTextField();
        Float radius_float = radius_;
        radius.setText(radius_float.toString());
        properties_panel_.add(new JLabel("Radius"));
        properties_panel_.add(radius);

        //TODO convert Int to string
        JTextField health = new JTextField();
        health.setText(health.toString());
        properties_panel_.add(new JLabel("Health"));
        properties_panel_.add(health);
    }

}
