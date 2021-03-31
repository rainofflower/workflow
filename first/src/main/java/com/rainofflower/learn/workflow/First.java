package com.rainofflower.learn.workflow;

import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 第一个流程运行类
 *
 */
public class First {

	@Test
	public void testBase()  {
		// 创建流程引擎
		//ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.qa.xml");
		ProcessEngine engine = config.buildProcessEngine();

		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		// 获取流程任务组件
		TaskService taskService = engine.getTaskService();

		DynamicBpmnService dynamicBpmnService = engine.getDynamicBpmnService();

		// 部署流程文件
		/*repositoryService.createDeployment()
				.addClasspathResource("processes/test1.bpmn").deploy();*/

		/*repositoryService.createDeployment()
				.addClasspathResource("processes/test2.bpmn").deploy();*/


		// 启动流程--key要和已部署的某个流程的id相同
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess","20210300");
//		runtimeService.startProcessInstanceByKeyAndTenantId();
		Task task1 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("当前任务名称：" + task1.getName());
		// 查询第一个任务
		Task task = taskService.createTaskQuery().singleResult();
		System.out.println("第一个任务完成前，当前任务名称：" + task.getName());
		// 完成第一个任务
		taskService.complete(task.getId());
		// 查询第二个任务
		task = taskService.createTaskQuery().singleResult();
		System.out.println("第二个任务完成前，当前任务名称：" + task.getName());
		// 完成第二个任务（流程结束）
		taskService.complete(task.getId());
		task = taskService.createTaskQuery().singleResult();
		System.out.println("流程结束后，查找任务：" + task);
	}

	@Test
	public void testBranch(){
		ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.qa.xml");
		ProcessEngine engine = config.buildProcessEngine();

		// 得到流程存储服务组件
		RepositoryService repositoryService = engine.getRepositoryService();
		// 得到运行时服务组件
		RuntimeService runtimeService = engine.getRuntimeService();
		// 获取流程任务组件
		TaskService taskService = engine.getTaskService();

		DynamicBpmnService dynamicBpmnService = engine.getDynamicBpmnService();

		// 部署流程文件
		/*repositoryService.createDeployment()
				.addClasspathResource("processes/branch.bpmn").deploy();*/
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("branch","20210300");
		String processInstanceId = processInstance.getId();
//		String processInstanceId = "22505";
		System.out.println("流程实例id"+processInstanceId);
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		for(Task task : tasks){
			System.out.println("任务："+task.getName());
			taskService.complete(task.getId());
		}
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		System.out.println("任务："+task.getName());
		taskService.complete(task.getId());
	}
}
