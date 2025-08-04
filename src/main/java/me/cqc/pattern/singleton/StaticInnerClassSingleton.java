package me.cqc.pattern.singleton;

/**
 * 静态内部类
 * 线程安全（类加载机制保证）
 * 无法传递参数初始化实例（构造参数必须无参）
 * 无参初始化的多线程场景
 */
public class StaticInnerClassSingleton {

    private StaticInnerClassSingleton() {
    }

    //静态内部类，仅在调用 getInstance 时加载
    private static class SingletonHolder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

    public static StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
