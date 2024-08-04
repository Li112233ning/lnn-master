package com.lnn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 用户-角色(UserRole)表实体类
 *
 * @author makejava
 * @since 2024-08-04 19:36:12
 */
@TableName("redpig_sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户-角色")
public class UserRole implements Serializable {

    //用户主键ID
    @Schema(description = "用户主键ID")
    @TableField(value = "user_id")
    private Long userId;
    //角色主键ID
    @Schema(description = "角色主键ID")
    @TableField(value = "role_id")
    private Long roleId;

}

