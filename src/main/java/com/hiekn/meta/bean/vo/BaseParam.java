package com.hiekn.meta.bean.vo;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class BaseParam {

    @ApiParam("用户Id")
    @QueryParam("userId")
    private Integer userId;
    @ApiParam("用户token")
    @QueryParam("token")
    private String token;
    @ApiParam("请求时间戳")
    @QueryParam("tt")
    private Long tt;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTt() {
        return tt;
    }

    public void setTt(Long tt) {
        this.tt = tt;
    }
}
