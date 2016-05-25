package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;

import java.util.Random;

/**
 * Created by Max on 11/05/2016.
 */
public class Constants {
    public static final boolean no_parse = false;
    public static final boolean debug = false;
    public static final boolean immortal = false;
    public static final float player_spawn_x = Gdx.graphics.getWidth()/2.f + Gdx.graphics.getWidth() / 40.f;
    public static final float player_spawn_y = Gdx.graphics.getHeight()/2.f + Gdx.graphics.getWidth() / 40.f;
    public static final float player_radius = Gdx.graphics.getWidth() / 40f;

    public static final float enemy_width = Gdx.graphics.getWidth() / 20.f;
    public static final float enemy_height = Gdx.graphics.getWidth() / 20.f;

    public static final float target_radius = Gdx.graphics.getWidth() / 80.f;

    public static final float action_point_width = Gdx.graphics.getWidth() / 25.f;
    public static final float action_point_height = Gdx.graphics.getWidth() / 25.f;

}
