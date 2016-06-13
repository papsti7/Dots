package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChainActionPointItem extends LevelEditorItem {
    public ChainActionPointItem(String texture_name, Vector2 position, Vector2... positions) {
        super(texture_name, position);
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

        //TODO further positions?
    }

}
