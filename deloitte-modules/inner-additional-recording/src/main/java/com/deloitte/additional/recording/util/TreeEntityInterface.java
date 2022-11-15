package com.deloitte.additional.recording.util;

import java.util.List;

/**
 * @创建人 tangx
 * @创建时间 2022/11/15
 * @描述 树形菜单实体接口
 */
public interface TreeEntityInterface<E> {

    Long getId();

    Long getParentid();

    void setMenus(List<E> menus);

}