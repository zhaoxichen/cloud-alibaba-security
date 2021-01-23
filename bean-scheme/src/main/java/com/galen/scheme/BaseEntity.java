package com.galen.scheme;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表
 *
 * @author wcyong
 * @date 2019-04-19
 */
@Data
public class BaseEntity implements Serializable {

    @Column(name = "id", type = MySqlTypeConstant.BIGINT, length = 20, isKey = true)
    private Long id;

    @Column(name = "created_by", type = MySqlTypeConstant.VARCHAR,length = 128)
    private String createdBy;

    @Column(name = "created_on", type = MySqlTypeConstant.DATETIME)
    private Date createdOn;

    @Column(name = "modify_by", type = MySqlTypeConstant.VARCHAR,length = 128)
    private String modifiedBy;

    @Column(name = "modified_on", type = MySqlTypeConstant.DATETIME)
    private Date modifiedOn;

    @Column(name = "is_deleted", type = MySqlTypeConstant.INT, length = 1)
    private Integer isDeleted;

}
