package com.ezteam.tweenaccessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ezteam.utils.Constants;

/**
 * Created by batty on 6/9/2016.
 */
public class SpriteAccessor implements TweenAccessor<Sprite> {
    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case Constants.ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case Constants.ALPHA:
                target.setColor(1, 1, 1, newValues[0]);
                break;
        }
    }
}
