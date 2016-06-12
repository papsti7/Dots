package com.sewm.defaultteam.leveleditor.items;

import com.badlogic.gdx.math.Vector2;
import com.sewm.defaultteam.leveleditor.LevelEditorItem;

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
    }
}
