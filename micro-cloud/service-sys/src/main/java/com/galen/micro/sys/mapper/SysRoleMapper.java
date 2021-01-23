package com.galen.micro.sys.mapper;

import com.galen.micro.sys.model.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * @Author: Galen
     * @Description: 根据角色名称获取
     * @Date: 2019/4/9-17:41
     * @Param: [role_supplier]
     * @return: com.galen.micro.mini.model.SysRole
     **/
    SysRole selectByRoleName(String roleName);

    /**
     * @Author: Galen
     * @Description: 获取此管理员的所有的系统的角色
     * @Date: 2019/4/24-9:37
     * @Param: [getGroupType]
     * @return: java.util.List<com.galen.tl.model.SysRole>
     **/
    List<SysRole> getAllSysRoleList(Integer getGroupType);

    /**
     * @Author: Galen
     * @Description: 获取此管理员的系统的角色(不包括独立角色)
     * @Date: 2019/4/24-9:37
     * @Param: [getGroupType]
     * @return: java.util.List<com.galen.tl.model.SysRole>
     **/
    List<SysRole> getSysRoleList(Integer getGroupType);

    /**
     * @Author: Galen
     * @Description: 获取指定用户的角色列表
     * @Date: 2019/4/20-14:37
     * @Param: [userId]
     * @return: java.util.List<com.galen.tl.model.SysRole>
     **/
    List<SysRole> getUserSysRoleList(Long userId);

    /**
     * @Author: Galen
     * @Description: 获取指定用户的角色id列表
     * @Date: 2019/4/24-14:53
     * @Param: [userId]
     * @return: java.util.List<java.lang.Long>
     **/
    List<Long> getUserSysRoleIdList(Long userId);


    List<Map<String, Object>> searchRolesByGroupType(@Param("groupType") Integer groupType);
}