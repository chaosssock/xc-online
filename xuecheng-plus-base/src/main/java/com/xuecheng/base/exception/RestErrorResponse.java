package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @Auther: chaosssock
 * @Date: 2024/3/3 22:12
 * @Description: 和前端约定返回的异常信息模型
 */
public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

}
