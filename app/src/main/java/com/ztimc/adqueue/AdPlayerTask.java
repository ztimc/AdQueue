package com.ztimc.adqueue;

import android.util.Log;

import java.util.Random;

public class AdPlayerTask implements Task<AdContent> {

    public Advert mAdvert;

    public void setAdvert(Advert advert) {
        mAdvert = advert;
    }

    @Override
    public AdContent execute() {
        Log.d("AdPlayerTask","播放队列任务执行");
        AdContent adContent = new AdContent();
        adContent.advert = mAdvert;
        //模拟网络请求,1-2000毫秒
        int netTime = new Random().nextInt(2000) + 1;
        try {
            Thread.sleep(netTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adContent;
    }
}
