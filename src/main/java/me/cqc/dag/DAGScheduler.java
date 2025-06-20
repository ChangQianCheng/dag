package me.cqc.dag;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DAGScheduler {

    private final ExecutorService executor;
    private final DAG dag;
    private final CompletableFuture<Void> completionFuture = new CompletableFuture<>();
    private final Map<String, CompletableFuture<Void>> taskFutures = new HashMap<>();

    public DAGScheduler(DAG dag, int threadPoolSize) {
        this.dag = dag;
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
        dag.validate();

        // 为每个任务创建CompletableFuture
        for (TaskNode node : dag.getNodes()) {
            taskFutures.put(node.getId(), new CompletableFuture<>());
        }

        // 提交所有可以立即执行的任务
        for (TaskNode node : dag.getNodes()) {
            submitIfReady(node);
        }

        // 当所有任务完成时，完成completionFuture
        CompletableFuture.allOf(taskFutures.values().toArray(new CompletableFuture[0]))
                .whenComplete((v, ex) -> {
                    executor.shutdown();
                    if (ex != null) {
                        completionFuture.completeExceptionally(ex);
                    } else {
                        completionFuture.complete(null);
                    }
                });
    }

    public CompletableFuture<Void> getCompletionFuture() {
        return completionFuture;
    }

    private void submitIfReady(TaskNode node) {
        if (!node.canExecute()) {
            return;
        }

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            node.execute();
            // 通知后继任务
            for (TaskNode successor : node.getSuccessors()) {
                successor.predecessorCompleted(node);
                submitIfReady(successor);
            }
        }, executor);

        taskFutures.get(node.getId()).completeAsync(() -> null, executor);
    }
}    