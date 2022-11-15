package com.deloitte.additional.recording.domain;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deloitte.additional.recording.util.TreeEntityInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deloitte.common.core.annotation.Excel;
import lombok.Builder;

/**
 * (SysMenu)表实体类
 *
 * @author 吴鹏鹏ppp
 * @since 2022-11-09 23:49:34
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SysMenu  implements Serializable, TreeEntityInterface<SysMenu> {
    private static final long serialVersionUID = -49429765979140414L;
    @Excel(name = "${column.comment}")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 菜单名称
     */
    @Excel(name = "菜单名称")
    private String name;
    /**
     * 菜单父id
     */
    @Excel(name = "菜单父id")
    private Long parentid;
    /**
     * 状态,1:启用，0：禁用
     */
    @Excel(name = "状态,1:启用，0：禁用")
    private String status;
    /**
     * 访问路径
     */
    @Excel(name = "访问路径")
    private String url;
    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sortkey;
    /**
     * 菜单图标路径
     */
    @Excel(name = "菜单图标路径")
    private String iconpath;
    /**
     * 是否显示菜单
     */
    @Excel(name = "是否显示菜单")
    private String type;

//    @TableField(exist = false)
//    private List<SysMenu> menus = new ArrayList<SysMenu>();

    @TableField(exist = false)
    private List<SysMenu> menus;


}
