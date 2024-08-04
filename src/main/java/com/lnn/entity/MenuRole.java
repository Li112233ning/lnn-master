package com.lnn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 菜单-角色(MenuRole)表实体类
 *
 * @author makejava
 * @since 2024-08-05 00:48:28
 */
@TableName("redpig_sys_menu_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "菜单-角色")
public class MenuRole implements Serializable {
    //菜单主键ID
    @Schema(description = "菜单主键ID")
    @TableField(value = "menu_id")
    private Long menuId;

    //角色主键ID
    @Schema(description = "角色主键ID")
    @TableField(value = "role_id")
    private Long roleId;

}

