package com.sewm.defaultteam;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Lisa on 04.05.2016.
 */
public class TextObject {
    private BitmapFont font_;
    private SpriteBatch spriteBatch_;
    private float x_;
    private float y_;

    public BitmapFont getFont_() {
        return font_;
    }

    public void setFont_(BitmapFont font_) {
        this.font_ = font_;
    }

    public SpriteBatch getSpriteBatch_() {
        return spriteBatch_;
    }

    public void setSpriteBatch_(SpriteBatch spriteBatch_) {
        this.spriteBatch_ = spriteBatch_;
    }

    public float getX_() {
        return x_;
    }

    public void setX_(float x_) {
        this.x_ = x_;
    }

    public float getY_() {
        return y_;
    }

    public void setY_(float y_) {
        this.y_ = y_;
    }

    public String getText_() {
        return text_;
    }

    public void setText_(String text_) {
        this.text_ = text_;
    }

    private String text_;
    public TextObject(BitmapFont font, SpriteBatch spriteBatch, float x, float y, String text)
    {
        font_ = font;
        spriteBatch_ = spriteBatch;
        x_ = x;
        y_ = y;
        text_ = text;
    }

    public void draw()
    {
        font_.draw(spriteBatch_,text_,x_,y_);
    }


}
