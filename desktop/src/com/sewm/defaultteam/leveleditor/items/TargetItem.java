package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
        final JTextField position_x = new JTextField();
        Float pos_x = this.position_.x;
        position_x.setText(pos_x.toString());
        properties_panel_.add(new JLabel("X-Position"));
        properties_panel_.add(position_x);

        final JTextField position_y = new JTextField();
        Float pos_y = this.position_.y;
        position_y.setText(pos_y.toString());
        properties_panel_.add(new JLabel("Y-Position"));
        properties_panel_.add(position_y);

        final JTextField radius = new JTextField();
        Float radius_float = radius_;
        radius.setText(radius_float.toString());
        properties_panel_.add(new JLabel("Radius"));
        properties_panel_.add(radius);

        final JTextField health = new JTextField();
        health.setText(String.valueOf(health_));
        properties_panel_.add(new JLabel("Health"));
        properties_panel_.add(health);

        JButton btn_save = new JButton("Save");
        btn_save.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                position_.x = Float.parseFloat(position_x.getText());
                position_.y = Float.parseFloat(position_y.getText());
                radius_= Integer.parseInt(radius.getText());
                health_= Integer.parseInt(health.getText());
            }
        });
        properties_panel_.add(btn_save);
    }

}
