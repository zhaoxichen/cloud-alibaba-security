package com.galen.micro.sys.mapper;


import com.galen.micro.sys.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByUid(Long userId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * @Author: Galen
     * @Description: 查看是否已经存在
     * @Date: 2019/4/12-16:11
     * @Param: [userId, roleId]
     * @return: com.galen.micro.mini.model.SysUserRole
     **/
    SysUserRole selectByUidRid(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * @Author: Galen
     * @Description: 删除关联数据
     * @Date: 2019/4/20-14:24
     * @Param: [roleId]
     * @return: int
     **/
    int deleteByRoleId(Long roleId);

    /**
     * @Author: Galen
     * @Description: 查询与此角色关联的用户
     * @Date: 2019/4/25-11:23
     * @Param: [roleId]
     * @return: java.util.List<java.lang.Long>
     **/
    List<Long> selectUserByRid(Long roleId);

    /**
     * @Author: Galen
     * @Description: 查询与此用户关联的独立角色
     * @Date: 2019/4/25-11:23
     * @Param: [userId]
     * @return: java.lang.Long
     **/
    Long selectOnAloneRoleByUid(Long userId);
}