package com.ztimc.adqueue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //模拟广告展示
        new Thread(new Runnable() {
            @Override
            public void run() {
                AdProvider adProvider = new AdProvider(new AdGeneratorImp(), 2);
                while (true) {
                    try {
                        Advert advert = adProvider.provideAdvert();
                        Log.d("AdQueue", "获取到广告" + advert.description);
                        //每个广告展示3秒
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
