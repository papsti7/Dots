package com.sewm.defaultteam;

/**
 * Created by Admin on 11.05.2016.
 */
public class EnemyAttribute {

    public int speed_base_;
    public float health_;
    public int inertia_;
    public int difficulty_ ;

    EnemyAttribute(int speed_base, float health, int inertia, int difficulty){
        speed_base_ = speed_base;
        health_ = health;
        inertia_ = inertia;
        difficulty_ = difficulty;
    }

    static EnemyAttribute fromInt(int difficulty) {
        switch (difficulty) {
            case 0:
                return World.enemy_easy_;
            case 1:
                return World.enemy_medium_;
            case 2:
                return World.enemy_hard_;
            default:
                return World.enemy_easy_;
        }
    }
}
