package com.rainofflower.learn.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * ${DESCRIPTION}
 *
 * @author yanghui
 **/
public class ServiceTask1 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("执行服务任务："+execution.getProcessInstanceId() + ":"+execution.getProcessInstanceBusinessKey() +
                ":"+execution.getEventName());
    }
}
