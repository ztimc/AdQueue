package com.ztimc.adqueue;

public class AdPlayerQueue extends AdQueue<AdContent> {

    public AdPlayerQueue(int queueCount) {
        super(queueCount, null);
    }

    public AdPlayerQueue(int queueCount, Task<AdContent> task) {
        super(queueCount, task);
    }
}

