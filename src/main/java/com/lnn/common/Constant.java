package com.lnn.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 常量类
 */
@Data
@Configuration
public class Constant {

    //生成的代码存放目录:resources下
    @Value("${filePlace}")
    public String filePlace;

    //vm模板存放目录
    @Value("${vmPlace}")
    public String vmPlace;

    public static String[] annos = {"/register","/login","/unAuth","/user/refreshToken"};

    public static List<String> annosList = Arrays.asList(annos);



}
