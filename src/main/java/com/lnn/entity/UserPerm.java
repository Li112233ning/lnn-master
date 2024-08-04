package com.lnn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 用户-权限(UserPerm)表实体类
 *
 * @author makejava
 * @since 2024-08-04 19:40:53
 */
@TableName("redpig_sys_user_perm")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户-权限")
public class UserPerm implements Serializable{

    /** 用户主键ID **/
    @Schema(description = "用户主键ID")
    @TableField(value = "user_id")
    private Long userId;

    /** 权限主键ID **/
    @Schema(description = "权限主键ID")
    @TableField(value = "perm_id")
    private Long permId;

}

