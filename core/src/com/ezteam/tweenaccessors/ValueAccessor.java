package com.ezteam.tweenaccessors;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by batty on 6/10/2016.
 */
public class ValueAccessor implements TweenAccessor<Value> {
    @Override
    public int getValues(Value target, int tweenType, float[] returnValues) {
        returnValues[0] = target.getValue();
        return 1;
    }

    @Override
    public void setValues(Value target, int tweenType, float[] newValues) {
        target.setValue(newValues[0]);
    }
}
