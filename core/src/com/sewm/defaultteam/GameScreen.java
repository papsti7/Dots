package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.FileNotFoundException;

/**
 * Created by stefan on 22.04.2016.
 */
public class GameScreen implements Screen, InputProcessor {

    World world_;

    public World getWorld_() {
        return world_;
    }

    public void setWorld_(World world_) {
        this.world_ = world_;
    }

    public WorldRenderer getWorldRenderer_() {
        return worldRenderer_;
    }

    public void setWorldRenderer_(WorldRenderer worldRenderer_) {
        this.worldRenderer_ = worldRenderer_;
    }

    public boolean is_touched() {
        return is_touched;
    }

    public void setIs_touched(boolean is_touched) {
        this.is_touched = is_touched;
    }

    public WorldController getWorldController_() {
        return worldController_;
    }

    public void setWorldController_(WorldController worldController_) {
        this.worldController_ = worldController_;
    }




    WorldRenderer worldRenderer_;
    WorldController worldController_;
    static boolean is_touched;
    @Override
    public void show() {
        world_ = new World();
        worldRenderer_ = new WorldRenderer(world_, false);
        try {
            world_.parseLevelFile("levels/sample.lvl");
        } catch (FileNotFoundException e) {
            System.out.println("File nott found: " + e);
            System.out.println("Working Directory = " +
                    System.getProperty("user.dir"));
        }


        // world_.loadTestLevel();

        worldController_ = new WorldController(world_);
        is_touched = false;
    }

    void update(){
        if(Gdx.input.isTouched())
        {
            worldRenderer_.camera_.unproject(worldController_.touchpoint_.set(Gdx.input.getX(),Gdx.input.getY(), 0));
            Rectangle player = world_.getPlayer_().getRect();
            if(OverlapTester.pointInRectangle(player , worldController_.touchpoint_.x, worldController_.touchpoint_.y) || is_touched == true)
            {
                /* touched the player surrounding */
                float radius = world_.getPlayer_().surrounding_.radius;
                worldController_.update(new Vector2(worldController_.touchpoint_.x + radius, worldController_.touchpoint_.y + radius), worldRenderer_);
                is_touched = true;
            }

        }
        else{
            is_touched = false;
        }

        worldController_.updateHealths();
        worldController_.updateEntities();
    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        worldRenderer_.render();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /*INPUT PROCESSOR*/
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //worldController_.update(new Vector2(screenX, screenY));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
