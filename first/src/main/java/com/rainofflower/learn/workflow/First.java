package com.rainofflower.learn.workflow;

import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void testReceiveTask(){
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
				.addClasspathResource("processes/receiveTask.bpmn").deploy();*/
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("receiveTask","20210300");
        String processInstanceId = processInstance.getId();
//		String processInstanceId = "35005";
        System.out.println("流程实例id"+processInstanceId);
        Execution exe = runtimeService.createExecutionQuery().activityId("receivetask1").singleResult();
        System.out.println("当前流程节点："+exe.getName());
        runtimeService.trigger(exe.getId());
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        System.out.println("任务："+task.getName());
        taskService.complete(task.getId());
    }

    @Test
    public void testTimerTask() throws InterruptedException {
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
        repositoryService.createDeployment()
                .addClasspathResource("processes/timer1.bpmn").deploy();
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("timer","20210300");
//        System.out.println(processInstance.getName());
        Thread.sleep(120 * 1000);
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        System.out.println(list);
    }

	@Test
	public void testSubProcess() throws InterruptedException {
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
		repositoryService.createDeployment()
				.addClasspathResource("processes/subprocess.bpmn").deploy();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("subprocess","20210300");
		Task subTask = taskService.createTaskQuery().singleResult();
		Map<String, Object> vars = new HashMap<>();
		vars.put("success","false");
		taskService.complete(subTask.getId(), vars);
		List<Task> tasks = taskService.createTaskQuery().list();
		for (Task task : tasks) {
			System.out.println(task.getName());
		}
	}

	@Test
	public void testCallAct() throws InterruptedException {
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
		repositoryService.createDeployment()
				.addClasspathResource("processes/callActivity.bpmn")
				.addClasspathResource("processes/test1.bpmn")
				.deploy();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("callActivityTest","20210300");
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("流程节点："+task.getName());
		taskService.complete(task.getId());
		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
		System.out.println("当前流程实例数："+list.size());
		List<Execution> executions = runtimeService.createExecutionQuery().list();
		System.out.println("当前执行流数："+executions.size());
		task = taskService.createTaskQuery().singleResult();
		System.out.println("流程节点:"+task.getName());
		taskService.complete(task.getId());
		task = taskService.createTaskQuery().singleResult();
		System.out.println("流程节点:"+task.getName());
		taskService.complete(task.getId());
		List<Execution> executions2 = runtimeService.createExecutionQuery().list();
		System.out.println("当前执行流数："+executions2.size());
	}

	@Test
	public void testDynamicCallAct() throws InterruptedException {
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
				.addClasspathResource("processes/dynamicCallActivity.bpmn")
				.addClasspathResource("processes/sub1.bpmn")
				.addClasspathResource("processes/sub2.bpmn")
				.deploy();*/
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("dynamicCallActivityTest","20210401");
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		System.out.println("流程节点："+task.getName());
		Map<String, Object> vars = new HashMap<>();
		vars.put("targetSubProcess","sub2");
		taskService.complete(task.getId(),vars);
	}
}
