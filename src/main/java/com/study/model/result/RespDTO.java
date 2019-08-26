package com.study.model.result;

import java.io.Serializable;

/**
 * @Description: 通用返回格式
 * @author: gaodm
 * @time: 2018/8/1 14:55
 */
public class RespDTO<T> implements Serializable {


    public String code = "0";
    public String msg = "";
    public T data;

    public static RespDTO onSuc(Object data) {
        RespDTO resp = new RespDTO();
        resp.data = data;
        return resp;
    }

    public static RespDTO onError(String errMsg) {
        RespDTO resp = new RespDTO();
        resp.code = "-1";
        resp.msg = errMsg;
        return resp;
    }

    @Override
    public String toString() {
        return "RespDTO{" +
                "code=" + code +
                ", error='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
