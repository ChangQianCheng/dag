package me.cqc.pattern.singleton;


/**
 * Singleton is a design pattern that restricts the instantiation of a class to one single instance.
 * This is useful when exactly one object is needed to coordinate actions across the system.
 * The Singleton class ensures that only one instance of the class is created, providing a global point
 * of access to this instance.
 * The constructor of the Singleton class is marked as private to prevent direct instantiation from outside
 * the class. To obtain the singleton instance, use the {@link #getInstance()} method.
 * Thread safety: The provided implementation is  thread-safe.
 * 单例模式是一种将类的实例化限制为单个实例的设计模式。当系统中恰好需要一个对象来协调各项操作时，这种模式非常有用。单例类可确保仅创建该类的一个实例，并提供对该实例的全局访问点。
 * 单例类的构造函数会被标记为私有，以防止类外部直接实例化。若要获取单例实例，需使用`getInstance()`方法。
 */
public class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
