package com.ztimc.adqueue;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class AdRequestTask implements Task<Advert> {

    private AtomicInteger mTaskNumber = new AtomicInteger(1);

    @Override
    public Advert execute() {

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
            advert.description = "广告";
            advert.id = mTaskNumber.getAndIncrement() + "";
            return advert;
        } else {
            return null;
        }
    }
}
