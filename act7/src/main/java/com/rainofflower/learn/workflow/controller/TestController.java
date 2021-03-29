package com.rainofflower.learn.workflow.controller;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YangHui
 * @date 2021-03-29
 */
@RestController
public class TestController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/welcome")
    public String welcome(){
        return "调用流程存储服务，查询部署数量："
        + repositoryService.createDeploymentQuery().count();
    }
}
