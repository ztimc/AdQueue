package com.ztimc.adqueue;

import android.graphics.Bitmap;

public class AdContent implements TaskId {
    public Advert advert;
    public Bitmap mBitmap;

    @Override
    public String taskId() {
        return advert.taskId();
    }

    @Override
    public ResultState state() {
        return advert.state;
    }

    @Override
    public void setState(ResultState state) {
        advert.state = state;
    }
}
