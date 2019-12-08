package com.scb.common.BaseEntity;


import com.scb.common.constant.Constant;

import java.io.Serializable;

/**
 * @author xiaobin
 * @date 2019/4/16 16:35
 */
@Deprecated
public class BaseRes implements Serializable {
    private static final long serialVersionUID = 2554045249325249284L;

    private String retCode;// retCode 000001 代码成功 000000 失敗

    private Object data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BaseRes() {
        this.retCode = Constant.OP_SUCCESS;
    }

    public BaseRes(String retCode) {
        this.retCode = retCode;
    }

}
