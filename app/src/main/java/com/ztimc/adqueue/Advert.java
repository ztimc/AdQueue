package com.ztimc.adqueue;

public class Advert implements TaskId {

    public String id;
    public String description;
    public String imageUrl;
    public ResultState state;

    @Override
    public String taskId() {
        return id;
    }

    @Override
    public ResultState state() {
        return state;
    }
}
