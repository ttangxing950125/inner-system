package com.deloitte.additional.recording.request;

import com.deloitte.common.core.web.page.PageDomain;

/**
 * @创建人 tangx
 * @创建时间 2022/11/10
 * @描述
 */
public class PageReq extends PageDomain {


    private Integer page;

    private Integer pagesize;


    public Integer getPage() {
        return page == null ? 0 : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPagesize() {
        return pagesize == null ? 10 : pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
}
