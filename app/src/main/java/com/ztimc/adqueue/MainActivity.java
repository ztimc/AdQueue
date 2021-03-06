package com.ztimc.adqueue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private AdRequestQueue mAdRequestQueue;
    private AdPlayerQueue mAdPlayerQueue;
    private AdPlayerTask mAdPlayerTask;

    public static final int QUEUE_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdRequestQueue = new AdRequestQueue(QUEUE_COUNT, new AdRequestTask());
        mAdPlayerQueue = new AdPlayerQueue(QUEUE_COUNT);
        mAdPlayerTask = new AdPlayerTask();

        //模拟广告展示过程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    getAd();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

    private void getAd() {
        mAdRequestQueue.obtainResult(new AdQueue.QueueListener<Advert>() {
            @Override
            public void onTaskResult(final Advert result) {
                //广告请求获取完,执行广告播放队列任务
                mAdPlayerTask.mAdvert = result;
                mAdPlayerQueue.setTask(mAdPlayerTask);
                mAdPlayerQueue.obtainResult(new AdQueue.QueueListener<AdContent>() {
                    @Override
                    public void onTaskResult(AdContent result) {
                        //获取到广告内容，展示,并且在队列中移除
                        mAdRequestQueue.removeResult(result.taskId());
                        mAdPlayerQueue.removeResult(result.taskId());
                        Log.d("adQueue", "获取到广告,整个广告生命周期走完");
                    }

                    @Override
                    public void onTaskError(String error) {
                        //获取广告内容失败，移除请求队列中内容
                        mAdRequestQueue.removeResult(result.id);
                        Log.d("adQueue", "获取广告内容失败");
                    }
                });
            }

            @Override
            public void onTaskError(String error) {
                //获取广告失败，再此获取
                Log.d("adQueue","获取广告失败");
            }
        });
    }
}
