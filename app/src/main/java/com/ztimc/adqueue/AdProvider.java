package com.ztimc.adqueue;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class AdProvider {

    private LinkedBlockingQueue<Advert> mAdQueue;
    private AdGenerator mAdGenerator;

    /**
     * 队列最大存储个数
     */
    private int mCacheAdCount;

    /**
     * 构造方法，传入必要配置参数
     *
     * @param adGenerator  广告生产者
     * @param cacheAdCount 需要存储多广告个数
     */
    public AdProvider(AdGenerator adGenerator, int cacheAdCount) {
        mAdGenerator = adGenerator;
        mCacheAdCount = cacheAdCount;
        // 广告存储队列，使用Linked，因为这里涉及频繁增删,并且带阻塞
        mAdQueue = new LinkedBlockingQueue<>(mCacheAdCount);
    }

    /**
     * 获取广告,如果没有广告，这里会阻塞,直到有广告，阻塞才会停止
     *
     * @return 广告实体
     */
    public Advert provideAdvert() throws InterruptedException {
        if (mAdQueue.isEmpty()) {
            obtainAdvert();
        }
        Advert advert = mAdQueue.take();
        checkAdCount();
        return advert;
    }

    /**
     * 检测广告数量是否足够
     */
    private void checkAdCount() {
        Log.d("AdQueue", "当前队列广告个数: " + mAdQueue.size());
        if (mAdQueue.size() < mCacheAdCount) {
            obtainAdvert();
        }
    }

    /**
     * 获取广告
     */
    private void obtainAdvert() {
        mAdGenerator.genAdvert(new AdGenerator.AdGeneratorListener() {
            @Override
            public void onAdComplete(Advert advert) {
                mAdQueue.add(advert);
                checkAdCount();
            }

            @Override
            public void onAdError() {
                checkAdCount();
            }
        });
    }

}
