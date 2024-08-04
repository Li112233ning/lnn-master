package com.lnn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 菜单-权限(MenuPerm)表实体类
 *
 * @author makejava
 * @since 2024-08-05 00:48:14
 */
@TableName("redpig_sys_menu_perm")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "菜单-权限")
public class MenuPerm implements Serializable {
    //菜单主键ID
    @Schema(description = "菜单主键ID")
    @TableField(value = "menu_id")
    private Long menuId;

    //权限主键ID
    @Schema(description = "权限主键ID")
    @TableField(value = "perm_id")
    private Long permId;

}

