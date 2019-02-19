package com.ztimc.adqueue;

import java.util.Random;

public class AdGeneratorImp implements AdGenerator {


    private int mAdCount = 0;

    @Override
    public void genAdvert(AdGeneratorListener listener) {
        int randomInt = new Random().nextInt(10) + 1;


        // 30%失败
        if (randomInt >= 3) {
            //模拟网络请求耗时0-2000毫秒
            int netTime = new Random().nextInt(2000) + 1;
            try {
                Thread.sleep(netTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Advert advert = new Advert();
            advert.description = "广告" + mAdCount;
            listener.onAdComplete(advert);
            mAdCount++;
        } else {
            listener.onAdError();
        }
    }
}
