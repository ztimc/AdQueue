# 广告队列实现

### 广告请求类

[AdGeneratorImp.java](https://github.com/ztimc/AdQueue/blob/master/app/src/main/java/com/ztimc/adqueue/AdGeneratorImp.java)

模拟网络延迟，请求失败的情况，生成广告

### 队列实现类

[AdProvider.java](https://github.com/ztimc/AdQueue/blob/master/app/src/main/java/com/ztimc/adqueue/AdProvider.java) 

广告提供者，负责不断提广告。

### 测试类

[MainActivity.java](https://github.com/ztimc/AdQueue/blob/master/app/src/main/java/com/ztimc/adqueue/MainActivity.java)

测试是否一直能获取广告，并输出广告队列中还剩下的广告个数

