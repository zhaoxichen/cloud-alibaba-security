package com.galen.micro.sys.service;

import com.galen.model.AplResponse;

public interface RoleService {
    /**
     * @Author: Galen
     * @Description: 增加一个角色
     * @Date: 2019/4/19-14:40
     * @Param: [nameEn, nameCn]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse createRole(String nameEn, String nameCn);

    /**
     * @Author: Galen
     * @Description: 修改角色属性
     * @Date: 2019/4/23-11:01
     * @Param: [roleId, nameEn, nameCn]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse modifyRole(Long roleId, String nameEn, String nameCn);

    /**
     * @Author: Galen
     * @Description: 添加用户为xxx角色
     * @Date: 2019/3/22-9:23
     * @Param: [userId, roleId]
     * @return: java.lang.String
     **/
    AplResponse addToRole(Long userId, Long roleId);

    /**
     * @Author: Galen
     * @Description: 查看所有系统角色
     * @Date: 2019/4/26-16:42
     * @Param: []
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getAllSysRoleList();

    /**
     * @Author: Galen
     * @Description: 查询系统角色, 不包含独立角色
     * @Date: 2019/4/19-15:03
     * @Param: []
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getSysRoleList();

    /**
     * @Author: Galen
     * @Description: 查询系统角色, 检测是否包含
     * @Date: 2019/4/24-14:49
     * @Param: [userId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getSysRoleList(Long userId);

    /**
     * @Author: Galen
     * @Description: 查看xx用户的角色列表
     * @Date: 2019/4/20-14:32
     * @Param: [userId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse getUserSysRoleList(Long userId);

    /**
     * @Author: Galen
     * @Description: 移除xx用户的一个角色
     * @Date: 2019/4/20-14:03
     * @Param: [userId, roleId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse removeUserSysRole(Long userId, Long roleId);

    /**
     * @Author: Galen
     * @Description: 移除一个角色
     * @Date: 2019/4/20-14:17
     * @Param: [roleId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse removeSysRole(Long roleId);

    /**
     * @Author: Galen
     * @Description: 移除一个独立角色
     * @Date: 2019/4/20-14:17
     * @Param: [userId]
     * @return: com.galen.model.AplResponse
     **/
    AplResponse removeAloneSysRole(Long userId);
}
