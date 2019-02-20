package com.ztimc.adqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdQueue<T extends TaskId> {
    private List<T> mTaskResults;
    private Executor mExecutor;
    private Task<T> mTask;

    private int mQueueCount;

    private QueueListener<T> mQueueListener;

    public AdQueue(int queueCount, Task<T> task) {
        mQueueCount = queueCount;
        mTask = task;
        mTaskResults = new ArrayList<>(queueCount);
        mExecutor = Executors.newFixedThreadPool(mQueueCount);
    }

    /**
     * 获取广告
     *
     * @param listener 广告结果的监听
     */
    public void obtainResult(QueueListener<T> listener) {
        mQueueListener = listener;

        if (!available()) {
            executeTask();
        } else {
            //当请求广告结果中还有广告，直接返回广告
            T availableResult = getAvailableResult();
            assert availableResult != null;
            availableResult.setState(TaskId.ResultState.USING);
            mQueueListener.onTaskResult(availableResult);
            mQueueListener = null;
        }
    }

    /**
     * 当广告走完生命周期，从队列中移除
     */
    public void removeResult(String advertId) {
        for (int i = 0; i < mTaskResults.size(); i++) {
            if (advertId.equals(mTaskResults.get(i).taskId())) {
                mTaskResults.remove(i);
                return;
            }
        }
        checkAdCount();
    }

    /**
     * 设置任务
     *
     * @param task 要执行当任务
     */
    public void setTask(Task<T> task) {
        mTask = task;
    }

    /**
     * 获取可用的结果
     *
     * @return 可用的结果
     */
    private T getAvailableResult() {
        for (int i = 0; i < mTaskResults.size(); i++) {
            if (mTaskResults.get(i).state() == TaskId.ResultState.NONE) {
                return mTaskResults.get(i);
            }
        }
        return null;
    }

    /**
     * 检测Results中是否有可用的结果
     *
     * @return
     */
    private boolean available() {
        for (int i = 0; i < mTaskResults.size(); i++) {
            if (mTaskResults.get(i).state() == TaskId.ResultState.NONE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测广告数量是否达到队列要求，如没有，再去获取广告
     */
    private void checkAdCount() {
        if (mTaskResults.size() < mQueueCount) {
            executeTask();
        }
    }

    /**
     * 执行任务，获取广告
     */
    private void executeTask() {
        //在线程池中执行获取广告,这里是并发，也就是可以同时进行多个广告获取
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {

                T result = mTask.execute();
                if (result == null && mQueueListener != null) {
                    mQueueListener.onTaskError("get ad error");
                    mQueueListener = null;
                    checkAdCount();
                    return;
                }

                mTaskResults.add(result);

                if (mQueueListener != null) {
                    assert result != null;
                    result.setState(TaskId.ResultState.USING);
                    mQueueListener.onTaskResult(result);
                    mQueueListener = null;
                    checkAdCount();
                }
            }
        });
    }

    /**
     * 队列监听，主要用于获取队列任务结果
     */
    interface QueueListener<T> {
        /* 当有结果时，此函数回调*/
        void onTaskResult(T result);

        /* 当获取结果失败时候，此函数回调，把决策权交给调用者*/
        void onTaskError(String taskId);
    }
}
