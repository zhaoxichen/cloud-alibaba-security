package com.galen.micro.sys.model;

import io.swagger.annotations.ApiParam;

public class MenuFilter {

    @ApiParam(hidden = true)
    private Long userId;

    @ApiParam(value = "取第pageBegin页", required = false)
    private Integer pageBegin;

    @ApiParam(value = "每页的数量", required = false)
    private Integer pageSize;

    @ApiParam(value = "菜单类型", required = false)
    private Integer menuType;

    @ApiParam(value = "中文标题", required = false)
    private String title;

    @ApiParam(value = "英文标题", required = false)
    private String titleEn;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPageBegin() {
        return pageBegin == null ? 0 : pageBegin;
    }

    public void setPageBegin(Integer pageBegin) {
        this.pageBegin = pageBegin;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }
}
