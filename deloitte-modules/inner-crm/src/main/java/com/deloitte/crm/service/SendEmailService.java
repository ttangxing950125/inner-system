package com.deloitte.crm.service;

/**
 * @author PenTang
 * @date 2022/09/23 15:46
 */
public interface SendEmailService {
    /**
     *根据角色id发送给相关用户
     *
     * @param id
     * @param title
     * @param content
     * @return String
     * @author penTang
     * @date 2022/9/23 15:58
    */
    String SendEmail(Integer id,String title,String content);
}
