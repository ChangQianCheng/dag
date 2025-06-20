package me.cqc.dag;

import java.util.*;

public class DAG {
    private final Map<String, TaskNode> nodes = new HashMap<>();

    public void addNode(TaskNode node) {
        nodes.put(node.getId(), node);
    }


    public void addEdge(String fromId, String toId) {
        TaskNode from = nodes.get(fromId);
        TaskNode to = nodes.get(toId);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Node not found");
        }
        to.addPredecessor(from);
    }

    public void addEdge(TaskNode from, TaskNode to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("from 或 to 为null");
        }
        nodes.put(from.getId(), from);
        nodes.put(to.getId(), to);
        to.addPredecessor(from);

    }

    public Collection<TaskNode> getNodes() {
        return nodes.values();
    }

    public void validate() {
        // 检查是否有环
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();
        for (TaskNode node : nodes.values()) {
            if (!visited.contains(node.getId())) {
                if (hasCycle(node, visited, recStack)) {
                    throw new IllegalStateException("DAG contains cycle");
                }
            }
        }
        // 初始化所有节点的前置任务计数
        for (TaskNode node : nodes.values()) {
            node.init();
        }
    }

    private boolean hasCycle(TaskNode node, Set<String> visited, Set<String> recStack) {
        String id = node.getId();
        if (recStack.contains(id)) {
            return true;
        }
        if (visited.contains(id)) {
            return false;
        }
        visited.add(id);
        recStack.add(id);
        for (TaskNode successor : node.getSuccessors()) {
            if (hasCycle(successor, visited, recStack)) {
                return true;
            }
        }
        recStack.remove(id);
        return false;
    }
}
