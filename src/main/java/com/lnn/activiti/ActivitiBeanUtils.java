package com.lnn.activiti;

import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActivitiBeanUtils {
    // 从配置文件中读取数据库驱动类名
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // 从配置文件中读取数据库连接 URL
    @Value("${spring.datasource.url}")
    String url;

    // 从配置文件中读取数据库用户名
    @Value("${spring.datasource.username}")
    String username;

    // 从配置文件中读取数据库密码
    @Value("${spring.datasource.password}")
    String password;

    // 初始化方法，用于配置和创建流程引擎
    public void init() {
        // 创建一个内存中的独立流程引擎配置
        ProcessEngineConfiguration config = ProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration();

        // 设置 JDBC 驱动
        config.setJdbcDriver(driver);
        // 设置 JDBC URL
        config.setJdbcUrl(url);
        // 设置 JDBC 用户名
        config.setJdbcUsername(username);
        // 设置 JDBC 密码
        config.setJdbcPassword(password);

        // 设置数据库模式更新策略为自动更新
        config.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 构建流程引擎的核心对象 ProcessEngine
        ProcessEngine processEngine = config.buildProcessEngine();

        // 获取任务服务
        TaskService taskService = processEngine.getTaskService();
        // 获取历史服务
        HistoryService historyService = processEngine.getHistoryService();
        // 获取仓库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 获取运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 将流程引擎及其服务注册为单例 Bean
        ApplicationContextUtil.registerSingletonBean("processEngine", processEngine);
        ApplicationContextUtil.registerSingletonBean("taskService", taskService);
        ApplicationContextUtil.registerSingletonBean("historyService", historyService);
        ApplicationContextUtil.registerSingletonBean("repositoryService", repositoryService);
        ApplicationContextUtil.registerSingletonBean("runtimeService", runtimeService);
    }
}
