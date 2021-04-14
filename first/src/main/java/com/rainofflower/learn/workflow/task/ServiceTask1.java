package com.rainofflower.learn.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author yanghui
 **/
public class ServiceTask1 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {

        Map<String, Object> variables = execution.getVariables();
        System.out.println(this.getClass() + "当前执行流参数："+variables);
        System.out.println("执行服务任务："+execution.getProcessInstanceId() + ":"+
                execution.getProcessBusinessKey() + "--父流程参数--"+
                execution.getSuperExecutionId()

        );
        execution.setVariable("audit","pass");
    }
}
