package com.ztimc.adqueue;

public interface AdGenerator {

    void genAdvert(AdGeneratorListener listener);


    /**
     * 广告生成回调
     */
    interface AdGeneratorListener {
        /**
         * 广告加载完毕
         * @param advert 广告
         */
        void onAdComplete(Advert advert);


        /**
         * 广告加载错误
         */
        void onAdError();
    }
}

