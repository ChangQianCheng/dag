package me.cqc.pattern.singleton;

/**
 * 双重检查锁定
 */
public class DCLSingleton {
    // 防止指令重排序，保证INSTANCE赋值后被访问
    private volatile static DCLSingleton INSTANCE;

    private DCLSingleton() {

    }

    public static DCLSingleton getInstance() {
        if (INSTANCE == null) {//第一次检查避免不必要的锁
            synchronized (DCLSingleton.class) { //局部锁 防止多线程同时进入
                if (INSTANCE == null) {
                    INSTANCE = new DCLSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
