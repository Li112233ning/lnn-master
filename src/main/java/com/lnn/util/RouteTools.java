package com.lnn.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lnn.entity.Menu;
import com.lnn.entity.Perm;
import com.lnn.entity.Role;
import com.lnn.entity.User;
import com.lnn.service.MenuService;
import com.lnn.service.UserService;
import com.lnn.vo.RouteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RouteTools {

    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    public List<RouteVO> buildRouteTree() {
        // 查询父菜单（parent_id 为 0 且未被删除）
        List<Menu> menuList = menuService.listMenu(new QueryWrapper<Menu>().eq("m.parent_id", 0L).eq("m.deleteStatus", 1));
        // 创建一个存储路由的列表
        ArrayList<RouteVO> routeVoList = new ArrayList<>();
        // 构建菜单树
        menu(menuList,routeVoList);
        // 打印路由信息，格式化为 JSON
        log.info(JSONUtil.toJsonPrettyStr(routeVoList));
        // 返回构建好的路由列表
        return routeVoList;
    }

    private void menu(List<Menu> menuList, List<RouteVO> routeVOList) {
        // 检查根菜单列表是否为空
        if(CollUtil.isNotEmpty(menuList)){
            // 遍历每个父菜单
            for (Menu menu : menuList) {
                // 将菜单转换为路由对象
                RouteVO routeVO = route(menu);
                // 如果菜单类型为 0 且父菜单 ID 为 0，添加到路由列表
                if(menu.getType() == 0 && menu.getParentId() == 0){
                    routeVOList.add(routeVO);
                }
                // 查询当前菜单的子菜单（排除类型为 2 的菜单，且未被删除）
                List<Menu> childrenList = menuService.listMenu(new QueryWrapper<Menu>().eq("m.parent_id", menu.getId()).ne("m.type", 2).eq("m.deleteStatus", 1));
                // 如果子菜单列表不为空
                if(CollUtil.isNotEmpty(childrenList)){
                    // 获取子菜单的路由对象
                    List<RouteVO> childrenRoute = childrenRoute(childrenList);
                    // 将子路由设置到父路由中
                    routeVO.setChildren(childrenRoute);
                    // 递归调用，构建子菜单的路由
                    menu(childrenList,routeVOList);
                }
            }
        }
    }

    private List<RouteVO> childrenRoute(List<Menu> menuList) {
        // 创建一个存储子路由的列表
        List<RouteVO> children = new ArrayList<>();

        // 检查菜单列表是否为空
        if (CollUtil.isNotEmpty(menuList)){
            //遍历菜单，将菜单转换为路由对象，添加到子路由列表
            for (Menu menu : menuList) {
                RouteVO child = route(menu);
                children.add(child);
            }

        }
        return children;

    }

    private RouteVO route(Menu menu) {
        // 创建一个新的路由对象
        RouteVO root = new RouteVO();
        root.setPath(menu.getPath()); // 设置路由路径
        root.setName(menu.getName()); // 设置路由名称
        root.setComponent(menu.getComponent()); // 设置路由组件

        // 创建路由元数据对象
        RouteVO.Meta meta = new RouteVO.Meta();
        meta.setTitle(menu.getTitle()); // 设置菜单标题
        meta.setIcon(menu.getIcon()); // 设置菜单图标
        meta.setShowLink(menu.getShowLink()); // 设置是否显示链接
        meta.setRank(menu.getRank()); // 设置菜单排序
        // 设置访问该菜单需要的角色
        meta.setRoles(menu.getRoles().stream().map(Role::getTag).collect(Collectors.toList()));

        // 获取当前用户的权限列表
        User user  = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Perm> permList = user.getPerms();
        // 设置路由的权限标识
        meta.setAuths(permList.stream().map(Perm::getTag).collect(Collectors.toList()));

        // 将元数据设置到路由对象中
        root.setMeta(meta);

        //返回构建好的路由对象
        return root;

    }
}
