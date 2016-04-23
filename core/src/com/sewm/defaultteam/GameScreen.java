package com.sewm.defaultteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by stefan on 22.04.2016.
 */
public class GameScreen implements Screen, InputProcessor {
    World world_;
    WorldRenderer worldRenderer_;
    WorldController worldController_;
    boolean flag;
    @Override
    public void show() {
        world_ = new World();
        worldRenderer_ = new WorldRenderer(world_, true);
        worldController_ = new WorldController(world_);
        flag = true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //System.out.println("render\n");
        if(world_.getPlayer_().surrounding_.x < 1200 && flag == true)
            worldController_.update(new Vector2(world_.getPlayer_().surrounding_.x + 1.f ,world_.getPlayer_().surrounding_.y));
        else {
            worldController_.update(new Vector2(world_.getPlayer_().surrounding_.x - 1.f, world_.getPlayer_().surrounding_.y + 1.f));
            flag = false;
        }
        worldRenderer_.render();
    }

    @Override
    public void resize(int width, int height) {

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
        System.out.println("touched with " + screenX + " and " + screenY);
        worldController_.update(new Vector2(screenX, screenY));
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
