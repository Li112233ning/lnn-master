package com.lnn.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnn.service.ProcessDefinitionService;
import com.lnn.util.Result;
import com.lnn.vo.ProcessDefinitionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipInputStream;

@Tag(name = "流程定义")
@RestController
public class ProcessDefinitionController {

    @Autowired
    private ProcessDefinitionService processDefinitionService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "流程定义分页查询")
    @GetMapping("/processDefinition/page")
    public Result page(
            @RequestParam(name = "currentPage", defaultValue = "1") Long currentPage, // 当前页码，默认为1
            @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize, // 每页大小，默认为10
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, // 开始时间
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) { // 结束时间

        // 创建查询条件的包装器
        QueryWrapper<ProcessDefinitionVO> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime"); // 按更新时间降序排序

        // 如果开始时间和结束时间不为空，则添加时间范围条件
        if (ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)) {
            wrapper.ge("updateTime", startTime); // 添加大于等于开始时间的条件
            wrapper.le("updateTime", endTime);   // 添加小于等于结束时间的条件
        }

        //查询流程定义并且分页
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .listPage((currentPage.intValue() - 1) * pageSize.intValue(), pageSize.intValue());

        // 创建一个列表来存储转换后的视图对象
        List<ProcessDefinitionVO> processDefinitionVOS = new ArrayList<>();
        // 遍历查询结果并转换为视图对象
        for (ProcessDefinition processDefinition : processDefinitions) {
            ProcessDefinitionVO processDefinitionVO = new ProcessDefinitionVO();
            processDefinitionVO.setId(processDefinition.getId());
            processDefinitionVO.setName(processDefinition.getName());
            processDefinitionVO.setDeploymentId(processDefinition.getDeploymentId());
            processDefinitionVOS.add(processDefinitionVO);
        }

        //创建分页对象并设置记录
        IPage<ProcessDefinitionVO> iPage = new Page<>();
        iPage.setRecords(processDefinitionVOS);
        //查询部署总数
        long total = repositoryService.createDeploymentQuery().count();
        iPage.setPages((total + pageSize - 1) / pageSize);
        iPage.setCurrent(currentPage);
        iPage.setSize(pageSize);
        iPage.setTotal(total);

        return Result.ok(iPage);
    }

    // 获取流程定义的图片
    @GetMapping("/processDefinition/processImage/{deploymentId}")
    public void processImage(HttpServletResponse response, @PathVariable("deploymentId") String deploymentId) {
        // 获取图片资源名称
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);

        //定义图片名字
        String resourceName = "";
        if (list != null && list.size() > 0){
            for (String name : list) {
                if (name.indexOf(".png") >= 0){
                    resourceName = name;
                }
            }
        }

        InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);

        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = resourceAsStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
            }
            resourceAsStream.close();

            byte[] bytes1 = outputStream.toByteArray();
            response.setContentType("image/jpg");
            ServletOutputStream os = response.getOutputStream();
            os.write(bytes1);
            os.flush();
            os.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // 新增或更新流程定义
    @Operation(summary = "新增流程定义", description = "新增或者更新流程定义")
    @PostMapping("/processDefinition/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ProcessDefinitionVO processDefinition) {
        return Result.success(processDefinitionService.saveOrUpdate(processDefinition)); // 返回成功结果
    }

    // 根据ID删除流程定义
    @Operation(summary = "删除流程定义", description = "根据ID删除流程定义")
    @PostMapping("/processDefinition/delById")
    public Result delById(@RequestBody ProcessDefinitionVO processDefinition) {
        try {
            repositoryService.deleteDeployment(processDefinition.getDeploymentId()); // 删除指定的流程定义
        } catch (Exception e) {
            throw new RuntimeException("删除失败：还有未完成的流程"); // 异常处理
        }
        return Result.ok(); // 返回成功结果
    }

    // 根据ID查询流程定义
    @Operation(summary = "查询流程定义", description = "根据ID查询流程定义")
    @PostMapping("/processDefinition/getById")
    public void getById(@RequestBody ProcessDefinitionVO processDefinition, HttpServletResponse response) throws IOException {
        // 获取图片资源名称
        List<String> list = repositoryService.getDeploymentResourceNames(processDefinition.getDeploymentId());
        // 定义图片资源的名称
        String resourceName = "";
        for (String name : list) {
            if ((name.indexOf("png")) >= 0){
                resourceName = name;
            }
        }

        // 获取图片的输入流
        InputStream in = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        // 获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();

        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = in.read(bytes)) != -1){
            outputStream.write(bytes,0,len);
        }
        in.close();
        outputStream.close();

    }

    // 根据ID批量删除流程定义
    @Operation(summary = "批量删除", description = "根据ID批量删除流程定义")
    @PostMapping("/processDefinition/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(processDefinitionService.removeBatchByIds(ids)); // 返回成功结果
    }

    /**
     * 上传流程文件并部署
     */
    @RequestMapping("/processDefinition/deploy")
    @ResponseBody
    public Result deploy(MultipartFile processDefinitionFile) throws IOException {
        InputStream in = processDefinitionFile.getInputStream(); // 获取上传文件的输入流
        ZipInputStream zipInputStream = new ZipInputStream(in); // 创建 ZIP 输入流
        repositoryService.createDeployment()
                .addZipInputStream(zipInputStream) // 添加 ZIP 输入流
                .deploy(); // 部署流程定义

        return Result.ok(); // 返回成功结果
    }
}