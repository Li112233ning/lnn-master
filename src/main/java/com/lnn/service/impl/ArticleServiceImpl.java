package com.lnn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnn.entity.Article;
import com.lnn.mapper.ArticleMapper;
import com.lnn.service.ArticleService;

import org.springframework.stereotype.Service;

/**
 * 文章(Article)表服务实现类
 *
 * @author makejava
 * @since 2024-08-02 00:44:27
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}


