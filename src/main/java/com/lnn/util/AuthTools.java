package com.lnn.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lnn.entity.Menu;
import com.lnn.entity.Perm;
import com.lnn.service.MenuService;
import com.lnn.service.PermService;
import com.lnn.vo.MenuPermTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthTools {

    @Autowired
    MenuService menuService;

    @Autowired
    PermService permService;

    // 获取菜单和权限的树形结构
    public List<MenuPermTreeVO> getMenuPerms() {

        // 查询所有类型不等于 2 的菜单
        List<Menu> menus = menuService.list(new QueryWrapper<Menu>().ne("type", 2));

        // 查询所有权限
        List<Perm> perms = permService.list();

        // 创建一个存储菜单权限树的列表
        List<MenuPermTreeVO> menuPerms = new ArrayList<>();

        // 遍历每个菜单
        for (Menu menu : menus) {
            // 遍历每个权限
            for (Perm perm : perms) {
                // 如果权限的菜单 ID 与当前菜单的 ID 相同
                if (perm.getMenuId() == menu.getId()) {
                    // 创建一个新的 MenuPermTreeVO 对象
                    MenuPermTreeVO menuPerm = new MenuPermTreeVO();
                    menuPerm.setId(perm.getId()); // 设置权限 ID
                    menuPerm.setName(perm.getName() + ":" + perm.getTag()); // 设置权限名称和标签
                    menuPerm.setParentId(perm.getMenuId() + 10000); // 设置父 ID，避免冲突
                    menuPerms.add(menuPerm); // 将权限对象添加到列表
                } else {
                    // 创建一个新的 MenuPermTreeVO 对象，表示菜单
                    MenuPermTreeVO menuPerm = new MenuPermTreeVO();
                    menuPerm.setId(menu.getId() + 10000); // 设置菜单 ID，避免与权限 ID 冲突
                    menuPerm.setName(menu.getTitle()); // 设置菜单标题
                    menuPerm.setParentId(menu.getParentId() + 10000); // 设置父 ID，避免冲突
                    menuPerm.setDisabled(true); // 设置为禁用状态
                    menuPerms.add(menuPerm); // 将菜单对象添加到列表
                }
            }
        }

        // 创建一个 TreeSet 来去重并排序 MenuPermTreeVO 对象
        Set<MenuPermTreeVO> menuPermVOSet = new TreeSet<>(new Comparator<MenuPermTreeVO>() {
            @Override
            public int compare(MenuPermTreeVO o1, MenuPermTreeVO o2) {
                return o1.getId().compareTo(o2.getId()); // 按 ID 升序排序
            }
        });

        // 将所有菜单权限对象添加到 Set 中，自动去重和排序
        menuPermVOSet.addAll(menuPerms);

        // 将 Set 转换为 List 并返回
        return menuPermVOSet.stream().collect(Collectors.toList());
    }
}
