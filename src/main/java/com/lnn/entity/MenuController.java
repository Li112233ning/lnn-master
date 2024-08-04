package com.lnn.entity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnn.service.MenuPermService;
import com.lnn.service.MenuRoleService;
import com.lnn.service.MenuService;
import com.lnn.util.Result;
import com.lnn.vo.AuthRolePermVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Tag(name = "菜单")
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    MenuRoleService menuRoleService;

    @Autowired
    MenuPermService menuPermService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "parendId",description = "父菜单",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "菜单分页查询")
    @GetMapping("/menu/page")
    public Result page(
            @RequestParam(name = "currentPage", defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            Long parendId) {

        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("m.updateTime");
        wrapper.eq("m.deleteStatus",1);
        if (ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)) {
            wrapper.ge("m.updateTime", startTime);
            wrapper.le("m.updateTime", endTime);
        }

        if (ObjUtil.isNotNull(parendId)) {
            wrapper.eq("m.parent_id", parendId);
        }
        return Result.success(menuService.listPage(new Page<>(currentPage, pageSize), wrapper));
    }

    @Operation(summary = "新增菜单",description = "新增或者更新菜单")
    @PostMapping("/menu/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }

    @Operation(summary = "删除菜单",description = "根据ID删除菜单")
    @PostMapping("/menu/delById")
    public Result delById(@RequestBody Menu menu) {
        return Result.success(menuService.removeById(menu.getId()));
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除菜单")
    @PostMapping("/menu/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(menuService.removeBatchByIds(ids));
    }

    @Operation(summary = "查询菜单",description = "根据ID查询菜单")
    @GetMapping("/menu/getById")
    public Result getById(Long id) {
        return Result.success(menuService.getById(id));
    }

    @Parameters({
            @Parameter(name = "type",description = "菜单类型",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "parendId",description = "父菜单",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "查询菜单",description = "查询所有菜单")
    @GetMapping("/menu/getMenuList")
    public Result getMenuList(String types) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if(types!=null){
            queryWrapper.in("type",types.split(","));
        }
        List<Menu> list = menuService.list(queryWrapper);

        Menu root = new Menu();
        root.setId(0L);
        root.setTitle("顶级菜单");
        root.setIcon("ic:outline-home");
        list.add(0,root);

        return Result.success(list);
    }

    @Operation(summary = "菜单授权",description = "菜单授权")
    @PostMapping("/menu/saveAuth")
    public Result saveAuth(@RequestBody AuthRolePermVO menuRolePerm) {
        // 检查传入的 menuRolePerm 对象是否不为空且其 ID 不为 null
        if (ObjUtil.isNotNull(menuRolePerm) && menuRolePerm.getId() != null) {
            // 根据 menuRolePerm 的 ID 删除对应的 MenuRole 记录
            menuRoleService.remove(new QueryWrapper<MenuRole>().eq("menu_id", menuRolePerm.getId()));
            // 根据 menuRolePerm 的 ID 删除对应的 MenuPerm 记录
            menuPermService.remove(new QueryWrapper<MenuPerm>().eq("menu_id", menuRolePerm.getId()));

            // 检查 menuRolePerm 是否不为空且其角色列表不为空
            if (ObjUtil.isNotNull(menuRolePerm) && CollUtil.isNotEmpty(menuRolePerm.getRoles())) {
                // 创建一个存储 MenuRole 对象的列表
                List<MenuRole> menuRoles = new ArrayList<>();
                // 遍历角色 ID 列表
                for (Long roleId : menuRolePerm.getRoles()) {
                    // 创建一个新的 MenuRole 对象并设置菜单 ID 和角色 ID
                    MenuRole menuRole = new MenuRole();
                    menuRole.setMenuId(menuRolePerm.getId());
                    menuRole.setRoleId(roleId);

                    // 将 MenuRole 对象添加到列表中
                    menuRoles.add(menuRole);
                }
                // 批量保存 MenuRole 对象到数据库
                menuRoleService.saveBatch(menuRoles);
            }

            // 检查 menuRolePerm 是否不为空且其权限列表不为空
            if (ObjUtil.isNotNull(menuRolePerm) && CollUtil.isNotEmpty(menuRolePerm.getPerms())) {
                // 创建一个存储 MenuPerm 对象的列表
                List<MenuPerm> menuPerms = new ArrayList<>();

                // 遍历权限 ID 列表
                for (Long permId : menuRolePerm.getPerms()) {
                    // 创建一个新的 MenuPerm 对象并设置菜单 ID 和权限 ID
                    MenuPerm menuPerm = new MenuPerm();
                    menuPerm.setMenuId(menuRolePerm.getId());
                    menuPerm.setPermId(permId);

                    // 将 MenuPerm 对象添加到列表中
                    menuPerms.add(menuPerm);
                }
                // 批量保存 MenuPerm 对象到数据库
                menuPermService.saveBatch(menuPerms);
            }
        }
        // 返回操作成功的结果
        return Result.ok();
    }


}
