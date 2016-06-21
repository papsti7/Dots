package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;

/**
 * Created by Max on 11/05/2016.
 */
public final class Constants {

    public static final boolean no_parse = false;
    public static final boolean debug = false;
    public static final boolean immortal = false;
    public static final boolean infinite_action_points = false;
    public static final boolean random_enemy_movement = false;

    public static float player_spawn_x = Gdx.graphics.getWidth()/2.f + Gdx.graphics.getWidth() / 40.f;
    public static float player_spawn_y = Gdx.graphics.getHeight()/2.f + Gdx.graphics.getWidth() / 40.f;
    public static float player_radius = Gdx.graphics.getWidth() / 40f;
    public static final float player_damage = 2;

    public static float enemy_width = Gdx.graphics.getWidth() / 20.f;
    public static float enemy_height = Gdx.graphics.getWidth() / 20.f;
    public static final float enemy_damage = 4.5f;

    public static float target_radius = Gdx.graphics.getWidth() / 80.f;
    public static final float target_spawn_interval = 2.f;

    public static float action_point_width = Gdx.graphics.getWidth() / 25.f;
    public static float action_point_height = Gdx.graphics.getWidth() / 25.f;

    public static final float action_point_life_span_ = 5.f;
    public static final float action_point_spawn_interval_ = 4.f;

    public static final float menu_button_x_4_3 = Gdx.graphics.getWidth() * 0.234375f;
    public static final float menu_button_y_1_4_3 = Gdx.graphics.getHeight() * 0.5859375f;
    public static final float menu_button_y_2_4_3 = Gdx.graphics.getHeight() * 0.1953125f;

    public static final float menu_button_x_16_9 = Gdx.graphics.getWidth() * 0.33f;
    public static final float menu_button_y_1_16_9 = Gdx.graphics.getHeight() * 0.5859375f;
    public static final float menu_button_y_2_16_9 = Gdx.graphics.getHeight() * 0.1953125f;

    public static final float aspect_ratio = Gdx.graphics.getHeight() != 0 ? Gdx.graphics.getWidth() / Gdx.graphics.getHeight() : 1;
    public static final float aspect_ratio_16_9 = 16/9;
    public static final float aspect_ratio_4_3 = 4/3;

	public static final float virtual_screen_width = 1000;
    public static final float virtual_screen_height = 1000;

    public static float virtual_to_real_x = Gdx.graphics.getWidth() / virtual_screen_width;
    public static float virtual_to_real_y = Gdx.graphics.getHeight() / virtual_screen_height;

    public static void resize(int width, int height) {
        player_spawn_x = width / 2.f + width / 40.f;
        player_spawn_y = width / 2.f + width / 40.f;
        player_radius = width / 40f;

        enemy_width = width / 20.f;
        enemy_height = width / 20.f;

        target_radius = width / 80.f;

        action_point_width = width / 25.f;
        action_point_height = width / 25.f;

        virtual_to_real_x = width / virtual_screen_width;
        virtual_to_real_y = height / virtual_screen_height;
    }

}
