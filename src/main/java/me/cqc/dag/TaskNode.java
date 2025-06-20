package me.cqc.dag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskNode {
    // 任务节点类
    private final String id;
    private final Task task;
    private final List<TaskNode> predecessors = new ArrayList<>();
    private final List<TaskNode> successors = new ArrayList<>();
    private final Map<String, Object> inputParams = new HashMap<>();
    private final Map<String, Object> outputParams = new HashMap<>();
    private final AtomicInteger pendingPredecessors = new AtomicInteger(0);
    private boolean executed = false;

    public TaskNode(String id, Task task) {
        this.id = id;
        this.task = task;
    }

    public void addPredecessor(TaskNode predecessor) {
        predecessors.add(predecessor);
        predecessor.getSuccessors().add(this);
    }

    public String getId() {
        return id;
    }


    public Task getTask() {
        return task;
    }

    public List<TaskNode> getPredecessors() {
        return predecessors;
    }

    public List<TaskNode> getSuccessors() {
        return successors;
    }

    public Map<String, Object> getInputParams() {
        return inputParams;
    }

    public AtomicInteger getPendingPredecessors() {
        return pendingPredecessors;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
    public void addInputParam(String key, Object value) {
        inputParams.put(key, value);
    }

    public Map<String, Object> getOutputParams() {
        return outputParams;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void init() {
        pendingPredecessors.set(predecessors.size());
    }

    public boolean canExecute() {
        return pendingPredecessors.get() == 0 && !executed;
    }

    public void predecessorCompleted(TaskNode predecessor) {
        pendingPredecessors.decrementAndGet();
        // 从前置任务获取输出参数作为本任务的输入
        inputParams.putAll(predecessor.getOutputParams());
    }

    public void execute() {
        if (executed) {
            return;
        }
        try {
            task.execute(inputParams, outputParams);
            executed = true;
        } catch (Exception e) {
            System.err.println("Task " + id + " execution failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
