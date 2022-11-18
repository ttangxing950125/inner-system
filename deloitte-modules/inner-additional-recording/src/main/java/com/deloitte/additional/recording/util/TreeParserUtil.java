package com.deloitte.additional.recording.util;

import java.util.ArrayList;
import java.util.List;

/**
  * @创建人 tangx
  * @创建时间  2022/11/15
  * @描述
 */public class TreeParserUtil {

    /**
     * 解析树形数据
     *
     */
    public static <E extends TreeEntityInterface<E>> List<E> getTreeList(Long topId, List<E> entityList) {
        List<E> resultList = new ArrayList<>();

        //获取顶层元素集合
        Long parentId;
        for (E entity : entityList) {
            parentId = entity.getParentid();
            if (parentId == null || parentId.equals(topId)) {
                resultList.add(entity);
            }
        }

        //获取每个顶层元素的子数据集合
        for (E entity : resultList) {
            entity.setMenus(getSubList(entity.getId(), entityList));
        }

        return resultList;
    }

    /**
     * 获取子数据集合
     *
     */
    private static <E extends TreeEntityInterface<E>> List<E> getSubList(Long id, List<E> entityList) {
        List<E> childList = new ArrayList<>();
        Long parentId;

        //子集的直接子对象
        for (E entity : entityList) {
            parentId = entity.getParentid();
            if (id.equals(parentId)) {
                //同一级放入同一个list
                childList.add(entity);
            }
        }

        //子集的间接子对象
        for (E entity : childList) {
            entity.setMenus(getSubList(entity.getId(), entityList));
        }

        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }

        return childList;
    }
}
