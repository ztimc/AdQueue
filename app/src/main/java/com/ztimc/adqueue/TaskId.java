package com.ztimc.adqueue;

public interface TaskId {
    enum ResultState {
        NONE,
        USING,
    }

    String taskId();
    ResultState state();
    void setState(ResultState state);
}
