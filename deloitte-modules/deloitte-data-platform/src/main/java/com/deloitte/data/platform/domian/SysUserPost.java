package com.deloitte.data.platform.domian;

import com.alibaba.fastjson2.JSON;

/**
 * 用户和岗位关联 sys_user_post
 *
 * @author lipeng
 */
public class SysUserPost {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + JSON.toJSONString(this);
    }
}
