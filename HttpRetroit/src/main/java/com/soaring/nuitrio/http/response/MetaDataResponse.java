package com.soaring.nuitrio.http.response;

/**
 * Created by renyuxiang on 2015/12/4.
 */
public class MetaDataResponse<T> {
    private String meta;
    private T data;

    public MetaDataResponse() {
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}