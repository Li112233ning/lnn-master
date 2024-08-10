package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.mapper.ProcessDefinitionMapper;
import com.lnn.service.ProcessDefinitionService;
import com.lnn.vo.ProcessDefinitionVO;
import org.springframework.stereotype.Service;

@Service
public class ProcessDefinitionServiceImpl extends ServiceImpl<ProcessDefinitionMapper, ProcessDefinitionVO> implements ProcessDefinitionService {

}
