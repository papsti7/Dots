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
