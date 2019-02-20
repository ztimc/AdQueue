package com.ztimc.adqueue;

public interface Task<T extends TaskId> {

    T execute();

}
