package com.ztimc.adqueue;

public class AdRequestQueue extends AdQueue<Advert> {


    public AdRequestQueue(int queueCount, Task<Advert> task) {
        super(queueCount, task);
    }
}
