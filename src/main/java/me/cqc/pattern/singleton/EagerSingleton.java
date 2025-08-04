package me.cqc.pattern.singleton;

/**
 * 饿汉式
 * 在类加载时就完成单例的初始化，利用JVM类加载机制保证线程安全。
 * 优势：线程安全，第一次获取示例快
 * 适用场景：单例对象创建成本低（不影响类加载）））
 */
public class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
    }


    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
