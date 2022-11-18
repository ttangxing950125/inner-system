package com.deloitte.common.core.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;
import org.apache.ibatis.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author PenTang
 * @date 2022/09/19 11:25
 */
public class EmailUtil {


    private EmailUtil() {

    }


    /**
     * 发送邮件
     *
     * @param title   邮件标题
     * @param content 邮件内容
     * @param address 收件人，多个收件人用,分割
     * @return
     */
    public static void sendEmail(String title, String content, String address) {
        List<String> addressList = new ArrayList<String>();
        String[] split = address.split(",");
        for (String s : split) {
            addressList.add(s);
        }
        MailAccount mailAccount = getMailAccount();
        String send = MailUtil.send(mailAccount, addressList, title, content, false);

    }

    public static MailAccount getMailAccount() {

        Props props = PropsUtil.get("properties/smtp");

        MailAccount account = new MailAccount();
        account.setHost(props.getStr("host"));
        account.setPort(Integer.valueOf(props.getStr("port")));
        account.setAuth(Boolean.valueOf(props.getStr("auth")));
        account.setFrom(props.getStr("sender"));
        account.setConnectionTimeout(Integer.valueOf(props.getStr("connectiontimeout")));
        account.setTimeout(Integer.valueOf(props.getStr("timeout")));
        return account;

    }

    /**
     * 发送带模板的邮件内容
     *
     * @param companyText 公司名称
     * @param contentText 邮件内容
     * @param address     邮件地址
     */
    public static void sendTemplateEmail(String companyText, String contentText, String address) {

        String send = "";
        MailAccount mailAccount = getMailAccount();
        try {
            send = MailUtil.send(mailAccount, address, "Crm主体管理", scopeTemplate(companyText, contentText), true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.err.println(">>>>>>>send>>>>>" + send);
        }

    }


    //读取到html文件模板，替换文件模板中的信息
    public static String scopeTemplate(String companyName, String context) {
        String filename = "emailTemplate/market.ftl";

        InputStream inputStream = null;
        BufferedReader fileReader = null;
        StringBuffer buffer = null;
        try {
            inputStream = Resources.getResourceAsStream(filename);
//        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);
            fileReader = new BufferedReader(new InputStreamReader(inputStream));
            buffer = new StringBuffer();
            String line = "";
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("read template");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //落款处需要时间则加上
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        String inscription = "主体管理" + "&nbsp;&nbsp;" + "日期:" + date;
        String htmlText = MessageFormat.format(buffer.toString(), companyName, context, inscription);
        return htmlText;

    }
}

