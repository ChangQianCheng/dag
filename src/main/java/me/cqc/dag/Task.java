package me.cqc.dag;

import java.util.Map;

@FunctionalInterface
public interface Task {

    void execute(Map<String, Object> inputParams, Map<String, Object> outputParams) throws Exception;

}
