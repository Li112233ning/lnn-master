package com.lnn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnn.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 文章(Article)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-02 00:44:28
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}

