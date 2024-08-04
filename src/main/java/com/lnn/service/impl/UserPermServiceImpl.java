package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.mapper.UserPermMapper;
import com.lnn.entity.UserPerm;
import com.lnn.service.UserPermService;
import org.springframework.stereotype.Service;

/**
 * 用户-权限(UserPerm)表服务实现类
 *
 * @author makejava
 * @since 2024-08-04 19:40:55
 */
@Service("userPermService")
public class UserPermServiceImpl extends ServiceImpl<UserPermMapper, UserPerm> implements UserPermService {

}


