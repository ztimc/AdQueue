# 广告队列实现

### 广告队列实现

[AdQueue.java](https://github.com/ztimc/AdQueue/blob/master/app/src/main/java/com/ztimc/adqueue/AdQueue.java)

队列只能支持指定数量的结果，并且执行任务在线程池中，保证了任务的执行是并行。

### 测试类

[MainActivity.java](https://github.com/ztimc/AdQueue/blob/master/app/src/main/java/com/ztimc/adqueue/MainActivity.java)

测试是否一直能获取广告，在log中能看到两个队列执行任务的过程。
