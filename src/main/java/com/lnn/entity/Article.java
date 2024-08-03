package com.lnn.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 文章(Article)表实体类
 *
 * @author makejava
 * @since 2024-08-02 00:44:26
 */
@TableName("redpig_article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    //主键ID
    private Long id;
    //删除状态：0、已删除 1、未删除
    private Integer deleteStatus;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //作者
    private String author;
    //内容
    private String content;
    //创建者
    private String createBy;
    //更新者
    private String updateBy;
    //备注
    private String remark;

}

