package com.deloitte.common.core.domain;

import com.deloitte.common.core.constant.Constants;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author lipeng
 */
public class MetaR<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = Constants.SUCCESS;

    /** 失败 */
    public static final int FAIL = Constants.FAIL;

    private int retcode;

    private String msg;

    private T data;

    public static <T> MetaR<T> ok()
    {
        return restResult(null, SUCCESS, null);
    }

    public static <T> MetaR<T> ok(T data)
    {
        return restResult(data, SUCCESS, null);
    }

    public static <T> MetaR<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> MetaR<T> fail()
    {
        return restResult(null, FAIL, null);
    }

    public static <T> MetaR<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> MetaR<T> fail(T data)
    {
        return restResult(data, FAIL, null);
    }

    public static <T> MetaR<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> MetaR<T> fail(int retcode, String msg)
    {
        return restResult(null, retcode, msg);
    }

    private static <T> MetaR<T> restResult(T data, int retcode, String msg)
    {
        MetaR<T> apiResult = new MetaR<>();
        apiResult.setRetcode(retcode);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getRetcode()
    {
        return retcode;
    }

    public void setRetcode(int retcode)
    {
        this.retcode = retcode;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
}
