package com.ty.zbpet.ui.base;

import java.io.Serializable;

/**
 * @author TY on 2018/10/26.
 */
public class BaseResponse<T> implements Serializable {

    private String tag;
    private String message;
    private T data;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
