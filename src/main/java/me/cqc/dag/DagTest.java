package me.cqc.dag;

public class DagTest {

    // 使用示例
    public static void main(String[] args) throws Exception {
        // 创建DAG
        DAG dag = new DAG();

        // 创建任务
        TaskNode task1 = new TaskNode("task1", (input, output) -> {
            System.out.println("Executing Task 1");
            output.put("result1", 1);
        });

        TaskNode task2 = new TaskNode("task2", (input, output) -> {
            System.out.println("Executing Task 2 with input: " + input.get("result1"));
            output.put("result2", (Integer) input.get("result1") * 2);
        });

        TaskNode task3 = new TaskNode("task3", (input, output) -> {
            System.out.println("Executing Task 3 with input: " + input.get("result1"));
            output.put("result3", (Integer) input.get("result1") * 3);

        });

        TaskNode task4 = new TaskNode("task4", (input, output) -> {
            System.out.println("Executing Task 4 with inputs: " + input.get("result2") + " and " + input.get("result3"));
            // 安全地获取并转换参数类型
            Integer result2 = (Integer) input.get("result2");
            Integer result3 = (Integer) input.get("result3");
            // 增加空值检查
            if (result2 != null && result3 != null) {
                output.put("finalResult", result2 + result3);
            } else {
                throw new IllegalArgumentException("Required parameters are missing or invalid");
            }
        });

        // 添加节点到DAG,并添加依赖关系
        dag.addEdge(task1, task2);
        dag.addEdge(task1, task3);
        dag.addEdge(task2, task4);
        dag.addEdge(task3, task4);

        // 创建并启动调度器
        DAGScheduler scheduler = new DAGScheduler(dag, 3);
        scheduler.start();

        // 等待所有任务完成
        scheduler.getCompletionFuture().join();

        System.out.println("All tasks completed successfully");
    }
}
