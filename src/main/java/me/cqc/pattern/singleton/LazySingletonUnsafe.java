package me.cqc.pattern.singleton;

/**
 * 懒汉式（线程不安全）
 * 线程都不安全，只能在单线程环境使用
 */
public class LazySingletonUnsafe {
    private static LazySingletonUnsafe INSTANCE = null;

    private LazySingletonUnsafe() {
    }

    // 多线程环境下，可能有多个线程同时进入该方法，对 INSTANCE 进行读写
    public static LazySingletonUnsafe getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LazySingletonUnsafe();
        }
        return INSTANCE;
    }
}
