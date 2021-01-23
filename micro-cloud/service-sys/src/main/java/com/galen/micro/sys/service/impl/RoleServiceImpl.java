package com.galen.micro.sys.service.impl;

import com.galen.model.AplResponse;
import com.galen.model.ResponseUtils;
import com.galen.micro.sys.component.CurrentSecurityUser;
import com.galen.micro.sys.mapper.SysMenuRoleMapper;
import com.galen.micro.sys.mapper.SysRoleMapper;
import com.galen.micro.sys.mapper.SysUserMapper;
import com.galen.micro.sys.mapper.SysUserRoleMapper;
import com.galen.micro.sys.model.SysRole;
import com.galen.micro.sys.model.SysUserRole;
import com.galen.micro.sys.service.RoleService;
import com.galen.security.pojo.SecuritySysUser;
import com.galen.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private CurrentSecurityUser currentSecurityUser;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuRoleMapper sysMenuRoleMapper;

    @Override
    public AplResponse createRole(String nameEn, String nameCn) {
        /**
         * @Author: Galen
         * @Description: 查看管理员的类型
         **/
        SecuritySysUser securitySysUser = currentSecurityUser.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        if (null == securitySysUser.getRoles() || 1 != securitySysUser.getRoles().size()) {
            return ResponseUtils.build(501, "不是管理员");
        }
        /**
         * @Author: Galen
         * @Description: 查看角色是否已经存在
         **/
        SysRole sysRole = sysRoleMapper.selectByRoleName(nameEn);
        if (sysRole == null) {
            sysRole = new SysRole();
            sysRole.setId(IdUtil.generateNumberId());
            sysRole.setNameEn(nameEn);
            sysRole.setNameCn(nameCn);
            sysRole.setGroupType(securitySysUser.getRoles().get(0).getGroupType());
            sysRoleMapper.insert(sysRole);
        }
        return ResponseUtils.SUCCESS(sysRole.getId());
    }

    @Override
    public AplResponse modifyRole(Long roleId, String nameEn, String nameCn) {
        /**
         * @Author: Galen
         * @Description: 查看管理员的类型
         **/
        SecuritySysUser securitySysUser = currentSecurityUser.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if (null == sysRole) {
            return ResponseUtils.build(501, "角色不存在！");
        }
        /**
         * @Author: Galen
         * @Description: 查看角色名是否已经存在
         **/
        SysRole sysRoleOfName = sysRoleMapper.selectByRoleName(nameEn);
        if (sysRoleOfName == null) {
            sysRole.setNameEn(nameEn);
            sysRole.setNameCn(nameCn);
            sysRoleMapper.updateById(sysRole);
            return ResponseUtils.SUCCESS(roleId);
        }
        /**
         * @Author: Galen
         * @Description: 角色名已经存在，判断是否是当前记录
         **/
        if (roleId.equals(sysRoleOfName.getId())) {
            sysRole.setNameCn(nameCn);
            sysRoleMapper.updateById(sysRole);
            return ResponseUtils.SUCCESS(roleId);
        }
        return ResponseUtils.build(502, "角色名已经存在！");
    }

    @Override
    public AplResponse addToRole(Long userId, Long roleId) {
        if (null == sysUserMapper.selectByPrimaryKey(userId)) {
            return ResponseUtils.build(501, "用户不存在");
        }
        if (null == sysRoleMapper.selectById(roleId)) {
            return ResponseUtils.build(502, "角色不存在");
        }
        SysUserRole sysUserRole = sysUserRoleMapper.selectByUidRid(userId, roleId);
        if (null != sysUserRole) {
            return ResponseUtils.SUCCESS(sysUserRole.getId());
        }
        sysUserRole = new SysUserRole();
        sysUserRole.setId(IdUtil.generateNumberId());
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        sysUserRoleMapper.insertSelective(sysUserRole);
        return ResponseUtils.SUCCESS(sysUserRole.getId());
    }

    @Override
    public AplResponse getSysRoleList() {
        SecuritySysUser securitySysUser = currentSecurityUser.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        if (0 == securitySysUser.getRoles().size()) {
            return ResponseUtils.build(501, "没有查看系统角色的权限");
        }
        if (1 != securitySysUser.getRoles().size() && !securitySysUser.getRoles().get(0).getOnAlone()) {
            return ResponseUtils.build(502, "不是独立权限的角色");
        }
        List<SysRole> sysRoleList = sysRoleMapper.getSysRoleList(securitySysUser.getRoles().get(0).getGroupType());
        return ResponseUtils.SUCCESS(sysRoleList);
    }

    @Override
    public AplResponse getAllSysRoleList() {
        SecuritySysUser securitySysUser = currentSecurityUser.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        if (0 == securitySysUser.getRoles().size()) {
            return ResponseUtils.build(501, "没有查看系统角色的权限");
        }
        if (1 != securitySysUser.getRoles().size() && !securitySysUser.getRoles().get(0).getOnAlone()) {
            return ResponseUtils.build(502, "不是独立权限的角色");
        }
        List<SysRole> sysRoleList = sysRoleMapper.getAllSysRoleList(securitySysUser.getRoles().get(0).getGroupType());
        return ResponseUtils.SUCCESS(sysRoleList);
    }

    @Override
    public AplResponse getSysRoleList(Long userId) {
        SecuritySysUser securitySysUser = currentSecurityUser.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        if (0 == securitySysUser.getRoles().size()) {
            return ResponseUtils.build(501, "没有查看系统角色的权限");
        }
        if (1 != securitySysUser.getRoles().size() && !securitySysUser.getRoles().get(0).getOnAlone()) {
            return ResponseUtils.build(502, "不是独立权限的角色");
        }
        List<Long> userRoleIdList = sysRoleMapper.getUserSysRoleIdList(userId);
        List<SysRole> sysRoleList = sysRoleMapper.getSysRoleList(securitySysUser.getRoles().get(0).getGroupType());
        for (SysRole sysRole : sysRoleList) {
            if (userRoleIdList.contains(sysRole.getId())) {
                sysRole.setOnChoose(true);
            } else {
                sysRole.setOnChoose(false);
            }
        }
        return ResponseUtils.SUCCESS(sysRoleList);
    }

    @Override
    public AplResponse getUserSysRoleList(Long userId) {
        List<SysRole> sysRoleList = sysRoleMapper.getUserSysRoleList(userId);
        return ResponseUtils.SUCCESS(sysRoleList);
    }

    @Override
    public AplResponse removeUserSysRole(Long userId, Long roleId) {
        SysUserRole sysUserRole = sysUserRoleMapper.selectByUidRid(userId, roleId);
        if (null != sysUserRole) {
            sysUserRoleMapper.deleteByPrimaryKey(sysUserRole.getId());
        }
        return ResponseUtils.SUCCESS("移除成功！");
    }

    @Override
    @Transactional
    public AplResponse removeSysRole(Long roleId) {
        /**
         * @Author: Galen
         * @Description: 查看这个角色还有么有人在使用
         **/
        List<Long> userList = sysUserRoleMapper.selectUserByRid(roleId);
        if (null == userList || 0 != userList.size()) {
            return ResponseUtils.build(501, "存在用户正在关联此角色！");
        }
        /**
         * @Author: Galen
         * @Description: 删除此角色资源
         **/
        sysRoleMapper.deleteById(roleId);
        /**
         * @Author: Galen
         * @Description: 删除此角色与用户的关联（防止存在脏数据）
         **/
        sysUserRoleMapper.deleteByRoleId(roleId);
        /**
         * @Author: Galen
         * @Description: 删除此角色与菜单的关联（防止存在脏数据）
         **/
        sysMenuRoleMapper.deleteByRoleId(roleId);
        return ResponseUtils.SUCCESS("移除成功！");
    }

    @Override
    public AplResponse removeAloneSysRole(Long userId) {
        /**
         * @Author: Galen
         * @Description: 查看这个角色还有么有人在使用
         **/
        Long roleId = sysUserRoleMapper.selectOnAloneRoleByUid(userId);
        if (null == roleId) {
            return ResponseUtils.SUCCESS("移除成功！");
        }
        /**
         * @Author: Galen
         * @Description: 删除此角色资源
         **/
        sysRoleMapper.deleteById(roleId);
        /**
         * @Author: Galen
         * @Description: 删除此角色与用户的关联（防止存在脏数据）
         **/
        sysUserRoleMapper.deleteByRoleId(roleId);
        /**
         * @Author: Galen
         * @Description: 删除此角色与菜单的关联（防止存在脏数据）
         **/
        sysMenuRoleMapper.deleteByRoleId(roleId);
        return ResponseUtils.SUCCESS("移除成功！");
    }
}
