package me.cqc.pattern.observer9;
import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;

public class FlowExample {

    public static void main(String[] args) throws InterruptedException {
        // 1. 创建发布者（使用JDK提供的SubmissionPublisher）
        try (SubmissionPublisher<String> publisher = new SubmissionPublisher<>()) {

            // 2. 定义订阅者的行为（通过Lambda简化）
            // 2.1 处理正常数据
            Consumer<String> onNext = data -> System.out.println("收到数据: " + data);
            // 2.2 处理错误
            Consumer<Throwable> onError = error -> System.err.println("发生错误: " + error.getMessage());
            // 2.3 处理完成事件
            Runnable onComplete = () -> System.out.println("数据发送完成");

            // 3. 创建订阅者适配器（将Lambda转换为Subscriber）
            Subscriber<String> subscriber = new SimpleSubscriber<>(onNext, onError, onComplete);

            // 4. 订阅（注册观察者）
            publisher.subscribe(subscriber);

            // 5. 发布数据
            publisher.submit("第一个数据");
            publisher.submit("第二个数据");
            publisher.submit("第三个数据");

            // 等待数据处理完成
            Thread.sleep(100);
        }
    }

    // 简化的Subscriber适配器，将核心行为通过函数式接口暴露
    static class SimpleSubscriber<T> implements Subscriber<T> {
        private final Consumer<T> onNext;
        private final Consumer<Throwable> onError;
        private final Runnable onComplete;
        private Subscription subscription;

        // 通过构造器接收Lambda表达式
        public SimpleSubscriber(Consumer<T> onNext,
                                Consumer<Throwable> onError,
                                Runnable onComplete) {
            this.onNext = onNext;
            this.onError = onError;
            this.onComplete = onComplete;
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            // 请求1条数据（背压控制：告诉发布者最多发送多少数据）
            subscription.request(1);
        }

        @Override
        public void onNext(T item) {
            // 处理数据（调用Lambda）
            onNext.accept(item);
            // 处理完一条后，再请求一条（背压控制）
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            // 处理错误（调用Lambda）
            onError.accept(throwable);
        }

        @Override
        public void onComplete() {
            // 处理完成事件（调用Lambda）
            onComplete.run();
        }
    }
}
