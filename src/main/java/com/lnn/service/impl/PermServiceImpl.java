package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.mapper.PermMapper;
import com.lnn.entity.Perm;
import com.lnn.service.PermService;
import org.springframework.stereotype.Service;

/**
 * 权限(Perm)表服务实现类
 *
 * @author makejava
 * @since 2024-08-04 19:45:32
 */
@Service("permService")
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements PermService {

}


