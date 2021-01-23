package com.galen.micro.sys.service.impl;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sys.component.CurrentSecurityUser;
import com.galen.micro.sys.mapper.SysMenuMapper;
import com.galen.micro.sys.mapper.SysMenuRoleMapper;
import com.galen.micro.sys.mapper.SysRoleMapper;
import com.galen.micro.sys.mapper.UserSecurityMapper;
import com.galen.micro.sys.model.MenuFilter;
import com.galen.micro.sys.model.SysMenu;
import com.galen.micro.sys.model.SysMenuRole;
import com.galen.micro.sys.model.SysRole;
import com.galen.micro.sys.service.MenuService;
import com.galen.security.pojo.SecuritySysMenu;
import com.galen.utils.IdUtil;
import com.galen.utils.RedisKeyConstant;
import com.galen.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private CurrentSecurityUser currentSecurityUser;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuRoleMapper sysMenuRoleMapper;
    @Autowired
    private UserSecurityMapper userSecurityMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private List<SysMenu> sourceList;//使得遍历同一份数据源

    @Override
    @Transactional
    public AplResponse createSysMenu(Integer menuType, Long parentId, String title, String titleEn, String iconPic, String path, String component,
                                     String elementId, String requestUrl, Integer sortOrder) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(IdUtil.generateNumberId());
        sysMenu.setMenuType(menuType);
        sysMenu.setParentId(parentId);
        sysMenu.setTitle(title);
        sysMenu.setTitleEn(titleEn);
        sysMenu.setIconPic(iconPic);
        sysMenu.setPath(path);
        sysMenu.setComponent(component);
        sysMenu.setElementId(elementId);
        sysMenu.setRequestUrl(requestUrl);
        sysMenu.setSortOrder(sortOrder);
        sysMenu.setEnabled(true);
        sysMenuMapper.insert(sysMenu);
        /**
         * @Author: Galen
         * @Description: 自动给超级管理员加入权限
         **/
        SysRole sysRole = sysRoleMapper.selectByRoleName("ROLE_admin");
        if (null != sysRole) {
            SysMenuRole sysMenuRole = sysMenuRoleMapper.selectByRidMid(sysRole.getId(), sysMenu.getId());
            if (null != sysMenuRole) {
                return ResponseUtils.SUCCESS(sysMenuRole.getId());
            }
            sysMenuRole = new SysMenuRole();
            sysMenuRole.setId(IdUtil.generateNumberId());
            sysMenuRole.setRoleId(sysRole.getId());
            sysMenuRole.setMenuId(sysMenu.getId());
            sysMenuRoleMapper.insertSelective(sysMenuRole);
        }
        /**
         * @Author: Galen
         * @Description: 触发重新装载
         **/
        redisTemplate.delete(RedisKeyConstant.TLMS_MENU_CHECK_LIST);
        return ResponseUtils.SUCCESS(sysMenu.getId());
    }

    @Override
    public AplResponse modifySysMenu(Long id, String title, String titleEn, String iconPic, String path, String component,
                                     String elementId, String requestUrl, Integer sortOrder) {
        SysMenu sysMenuDb = sysMenuMapper.selectById(id);
        if (null == sysMenuDb) {
            return ResponseUtils.build(501, "权限资源不存在");
        }
        if (StringUtil.isEmpty(title) && 1 == sysMenuDb.getMenuType()) {
            return ResponseUtils.build(502, "请传菜单标题");
        }
        if (StringUtil.isEmpty(elementId) && 2 == sysMenuDb.getMenuType()) {
            return ResponseUtils.build(503, "请传入元素id");
        }
        SysMenu sysMenuModify = new SysMenu();
        sysMenuModify.setId(id);
        sysMenuModify.setTitle(title);
        sysMenuModify.setTitleEn(titleEn);
        sysMenuModify.setIconPic(iconPic);
        sysMenuModify.setPath(path);
        sysMenuModify.setComponent(component);
        sysMenuModify.setElementId(elementId);
        sysMenuModify.setRequestUrl(requestUrl);
        sysMenuModify.setSortOrder(sortOrder);
        sysMenuMapper.updateById(sysMenuModify);
        /**
         * @Author: Galen
         * @Description: 触发重新装载
         **/
        redisTemplate.delete(RedisKeyConstant.TLMS_MENU_CHECK_LIST);
        return ResponseUtils.SUCCESS(id);
    }

    @Override
    public AplResponse addToSysMenu(Long roleId, Long menuId) {
        if (null == sysRoleMapper.selectById(roleId)) {
            return ResponseUtils.build(501, "角色不存在");
        }
        SysMenu sysMenu = sysMenuMapper.selectById(menuId);
        if (null == sysMenu) {
            return ResponseUtils.build(502, "权限不存在");
        }
        /**
         * @Author: Galen
         * @Description: 查看父级是否添加关联
         **/
        SysMenuRole sysMenuRole;
        if (0 != sysMenu.getParentId()) {
            sysMenuRole = sysMenuRoleMapper.selectByRidMid(roleId, sysMenu.getParentId());
            if (null == sysMenuRole) {
                sysMenuRole = new SysMenuRole();
                sysMenuRole.setId(IdUtil.generateNumberId());
                sysMenuRole.setRoleId(roleId);
                sysMenuRole.setMenuId(sysMenu.getParentId());
                sysMenuRoleMapper.insertSelective(sysMenuRole);
            }
        }
        /**
         * @Author: Galen
         * @Description: 添加当前菜单的关联
         **/
        sysMenuRole = sysMenuRoleMapper.selectByRidMid(roleId, menuId);
        if (null != sysMenuRole) {
            return ResponseUtils.SUCCESS(sysMenuRole.getId());
        }
        sysMenuRole = new SysMenuRole();
        sysMenuRole.setId(IdUtil.generateNumberId());
        sysMenuRole.setRoleId(roleId);
        sysMenuRole.setMenuId(menuId);
        sysMenuRoleMapper.insertSelective(sysMenuRole);
        List<Long> sysMenuIdList = sysMenuMapper.selectByPid(menuId);
        if (null == sysMenuIdList || 0 == sysMenuIdList.size()) {
            return ResponseUtils.SUCCESS(sysMenuRole.getId());
        }
        boolean checkFlag = true;
        List<Long> sysRoleMenuIdList = sysMenuRoleMapper.selectByRidPid(roleId, menuId);
        if (null == sysRoleMenuIdList || 0 == sysRoleMenuIdList.size()) {
            checkFlag = false;
        }
        /**
         * @Author: Galen
         * @Description: 同时自动添加一层此菜单的子菜单和角色的关联
         **/
        Iterator<Long> iterator = sysMenuIdList.iterator();
        Long childMenuId;
        List<SysMenuRole> sysMenuRoleList = new LinkedList<>();
        while (iterator.hasNext()) {
            childMenuId = iterator.next();
            //核查是否添加过了
            if (checkFlag && sysRoleMenuIdList.contains(childMenuId)) {
                continue;
            }
            sysMenuRole = new SysMenuRole();
            sysMenuRole.setId(IdUtil.generateNumberId());
            sysMenuRole.setRoleId(roleId);
            sysMenuRole.setMenuId(childMenuId);
            sysMenuRoleList.add(sysMenuRole);
        }
        if (0 == sysMenuRoleList.size()) {
            return ResponseUtils.SUCCESS(sysMenuRole.getId());
        }
        sysMenuRoleMapper.insertList(sysMenuRoleList);
        return ResponseUtils.SUCCESS(sysMenuRole.getId());
    }

    @Override
    public AplResponse getMenusByUser() {
        Long userId = currentSecurityUser.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        List<SecuritySysMenu> securitySysMenuList = userSecurityMapper.getMenuByUid(userId);
        return ResponseUtils.SUCCESS(securitySysMenuList);
    }

    @Override
    public AplResponse getButtonByUser(Long menuId) {
        Long userId = currentSecurityUser.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        List<String> buttonIdList = userSecurityMapper.getButtonElementIdByUid(userId, menuId);
        return ResponseUtils.SUCCESS(buttonIdList);
    }

    @Override
    public AplResponse getAllSysMenuList(MenuFilter menuFilter) {
        Long userId = currentSecurityUser.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        menuFilter.setUserId(userId);
        if (menuFilter.getPageBegin() < 0) {
            menuFilter.setPageBegin(0);
        }
        if (menuFilter.getPageSize() <= 0) {
            menuFilter.setPageSize(1);
        }
        menuFilter.setPageBegin(menuFilter.getPageBegin() * menuFilter.getPageSize());
        List<SysMenu> sysMenusList = sysMenuMapper.getAllSysMenuList(menuFilter);
        long totals = sysMenuMapper.getRowsTotal(menuFilter);
        return ResponseUtils.SUCCESS(sysMenusList, totals);
    }

    @Override
    public AplResponse getSysMenuList() {
        Long userId = currentSecurityUser.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        sourceList = sysMenuMapper.getSysMenuList(userId);
        return ResponseUtils.SUCCESS(arrangeToTree());
    }

    @Override
    public AplResponse getSysMenuList(Long roleId) {
        Long userId = currentSecurityUser.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        List<Long> roleMenuId = sysMenuMapper.getSysMenuIdListByRoleId(roleId);
        sourceList = sysMenuMapper.getSysMenuList(userId);
        for (SysMenu sysMenu : sourceList) {
            if (roleMenuId.contains(sysMenu.getId())) {
                sysMenu.setOnChoose(true); //属于此角色
            }
        }
        return ResponseUtils.SUCCESS(arrangeToTree());
    }

    /**
     * @Author: Galen
     * @Description: 规整为树形结构
     * @Date: 2019/4/25-9:58
     * @Param: [list]
     * @return: java.util.List<com.galen.tl.pojo.SysMenu>
     **/
    private List<SysMenu> arrangeToTree() {
        Iterator<SysMenu> menuIterator = sourceList.iterator();
        List<SysMenu> resultTreeList = new ArrayList<>();
        SysMenu sysMenuStock;
        while (menuIterator.hasNext()) {
            SysMenu sysMenu = menuIterator.next();
            //判断parent=0是根节点
            if (sysMenu.getParentId().equals(0) || sysMenu.getParentId() == 0) {
                sysMenuStock = (SysMenu) sysMenu.clone();
                if (null == sysMenuStock.getChildren()) {
                    sysMenuStock.setChildren(new ArrayList<>());
                }
                resultTreeList.add(sysMenuStock);
                //子集
                getChildren(sysMenuStock);
            }
        }
        return resultTreeList;
    }

    /**
     * @Author: Galen
     * @Description: 查子菜单（暂时不用递归）
     * @Date: 2019/4/25-17:52
     * @Param: [sysMenuStock]
     * @return: void
     **/
    private void getChildren(SysMenu sysMenuStock) {
        Iterator<SysMenu> menuIterator = sourceList.iterator();
        while (menuIterator.hasNext()) {
            SysMenu sysMenuSub = menuIterator.next();
            //节点的id = 子集的父级id
            if (sysMenuStock.getId().equals(sysMenuSub.getParentId())) {
                SysMenu sysMenuStockChild = (SysMenu) sysMenuSub.clone();
                if (null == sysMenuStockChild.getChildren()) {
                    sysMenuStockChild.setChildren(new ArrayList<>());
                }
                sysMenuStock.getChildren().add(sysMenuStockChild);
                //子集
                getChildren2(sysMenuStockChild);
            }
        }
    }

    private void getChildren2(SysMenu sysMenuStock) {
        Iterator<SysMenu> menuIterator = sourceList.iterator();
        while (menuIterator.hasNext()) {
            SysMenu sysMenuSub = menuIterator.next();
            //节点的id = 子集的父级id
            if (sysMenuStock.getId().equals(sysMenuSub.getParentId())) {
                sysMenuStock.getChildren().add((SysMenu) sysMenuSub.clone());
            }
        }
    }

    @Override
    public AplResponse getRoleSysMenuList(Long roleId) {
        List<SysMenu> sysMenuList = sysMenuMapper.getSysMenuListByRoleId(roleId);
        return ResponseUtils.SUCCESS(sysMenuList);
    }

    @Override
    @Transactional
    public AplResponse removeRoleSysMenu(Long roleId, Long menuId) {
        /**
         * @Author: Galen
         * @Description: 删除此菜单和角色的关联
         **/
        SysMenuRole sysMenuRole = sysMenuRoleMapper.selectByRidMid(roleId, menuId);
        if (null != sysMenuRole) {
            sysMenuRoleMapper.deleteByPrimaryKey(sysMenuRole.getId());
            /**
             * @Author: Galen
             * @Description: 同时删除此菜单的子菜单和角色的关联
             **/
            sysMenuRoleMapper.deleteByParentId(roleId, menuId);
        }
        return ResponseUtils.SUCCESS("移除成功！");
    }

    @Override
    @Transactional
    public AplResponse removeSysMenu(Long menuId) {
        SysMenu sysMenu = sysMenuMapper.selectById(menuId);
        if (null == sysMenu) {
            //防止多次点击
            return ResponseUtils.SUCCESS("移除成功！");
        }
        /**
         * @Author: Galen
         * @Description: 把子菜单的parentId改为 此菜单的parentId，实现所有菜单往上移动一层级
         **/
        sysMenuMapper.updateParentId(sysMenu);
        /**
         * @Author: Galen
         * @Description: 删除这个菜单
         **/
        sysMenuMapper.deleteById(menuId);
        /**
         * @Author: Galen
         * @Description: 移除关联数据
         **/
        sysMenuRoleMapper.deleteByMenuId(menuId);
        return ResponseUtils.SUCCESS("移除成功！");
    }
}
