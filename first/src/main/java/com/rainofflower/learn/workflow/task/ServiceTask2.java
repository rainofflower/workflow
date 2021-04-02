package com.rainofflower.learn.workflow.task;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * ${DESCRIPTION}
 *
 * @author yanghui
 **/
public class ServiceTask2 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("--task2--执行服务任务："+execution.getProcessInstanceId() + ":"+
                execution.getProcessInstanceBusinessKey() + "--父流程参数--"+
                        execution.getParent().getProcessInstanceId() + ":" +
                        execution.getParent().getProcessInstanceBusinessKey()
                );
    }
}
