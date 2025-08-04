package me.cqc.pattern.singleton;

/**
 * 懒汉式（线程安全，方法加锁）
 * 线程安全
 * 性能糟糕，每次获取示例都要加锁，在并发且使用频繁的场景下，性能糟糕。
 */
public class LazySingletonSafe {
    private static LazySingletonSafe INSTANCE = null;

    private LazySingletonSafe() {
    }

    // 在方法上添加 synchronized 关键字，限制只有一个线程可以进入方法
    public static synchronized LazySingletonSafe getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazySingletonSafe();
        }
        return INSTANCE;
    }
}
