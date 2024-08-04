package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.mapper.DeptMapper;
import com.lnn.entity.Dept;
import com.lnn.service.DeptService;
import org.springframework.stereotype.Service;

/**
 * 部门(dasd)表服务实现类
 *
 * @author makejava
 * @since 2024-08-04 23:42:07
 */
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

}


