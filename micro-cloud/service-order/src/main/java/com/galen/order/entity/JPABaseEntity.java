package com.galen.order.entity;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: chenzx05
 * @Date: 2021/1/23-21:54
 * @Description: 基础表
 **/
@Data
public class JPABaseEntity implements Serializable {

    @Column(name = "created_by", length = 128)
    private String createdBy;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "modify_by", length = 128)
    private String modifiedBy;

    @Column(name = "modified_on")
    private Date modifiedOn;

    @Column(name = "is_deleted", length = 1)
    private Integer isDeleted;
}
