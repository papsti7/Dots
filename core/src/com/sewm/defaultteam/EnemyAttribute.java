package com.sewm.defaultteam;

/**
 * Created by Admin on 11.05.2016.
 */
public class EnemyAttribute {

    public int speed_base_;
    public float health_;
    public int inertia_;
    public int difficulty_ ;

    EnemyAttribute(int sb, float h, int i, int d){
        speed_base_ = sb;
        health_ = h;
        inertia_ = i;
        difficulty_ = d;
    }
}
