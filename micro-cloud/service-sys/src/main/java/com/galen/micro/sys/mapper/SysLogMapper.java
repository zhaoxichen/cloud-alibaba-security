/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.galen.micro.sys.mapper;


import com.galen.micro.sys.model.SysLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Repository
public interface SysLogMapper extends BaseMapper<SysLogEntity> {
	
}
