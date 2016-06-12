package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

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
    }
}
