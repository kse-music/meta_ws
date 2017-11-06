package com.hiekn.meta.rest;

import com.hiekn.meta.bean.UserBean;
import com.hiekn.meta.bean.result.BaseParam;
import com.hiekn.meta.bean.result.RestResp;
import com.hiekn.meta.service.UserService;
import com.hiekn.meta.util.JsonUtils;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.ws.rs.*;


@Controller
@Path("/user")
@Api("用户模块")
public class UserRestApi {

    @Resource
    private UserService userService;

    @POST
    @Path("/listByPage")
    @ApiOperation("分页用户列表")
    public RestResp<UserBean> listByPage(@BeanParam BaseParam baseParam,
                                         @ApiParam(value = "当前页") @DefaultValue("1") @FormParam("pageNo") Integer pageNo,
                                         @ApiParam(value = "每页数") @DefaultValue("10") @FormParam("pageSize") Integer pageSize) {
        return new RestResp<>(userService.listByPage(pageNo, pageSize), baseParam.getTt());
    }

    @POST
    @Path("/login")
    @ApiOperation("用户登录")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = RestResp.class),
            @ApiResponse(code = 500, message = "失败")
    })
    public RestResp<UserBean> login(@BeanParam BaseParam baseParam,
                                    @ApiParam(value = "用户名/邮箱", required = true) @FormParam("username") String username,
                                    @ApiParam(value = "密码", required = true) @FormParam("password") String password) {
        UserBean userBean = userService.login(username, password);
        return new RestResp<>(userBean, baseParam.getTt());
    }

    @GET
    @Path("/get")
    @ApiOperation("用户详情")
    public RestResp<UserBean> get(@BeanParam BaseParam baseParam,
                                  @ApiParam(required = true) @QueryParam("id") Integer id) {
        return new RestResp<>(userService.get(id), baseParam.getTt());
    }

    @POST
    @Path("/add")
    @ApiOperation("添加用户")
    public RestResp<UserBean> add(@BeanParam BaseParam baseParam,
                                  @ApiParam(value = "bean对象", required = true) @FormParam("bean") String bean) {
        UserBean userBean = JsonUtils.fromJson(bean, UserBean.class);
        return new RestResp<>(userBean, baseParam.getTt());
    }

    @POST
    @Path("/modify")
    @ApiOperation("修改用户信息")
    public RestResp<UserBean> modify(@BeanParam BaseParam baseParam,
                                     @ApiParam(value = "bean对象", required = true) @FormParam("bean") String bean) {
        UserBean userBean = JsonUtils.fromJson(bean, UserBean.class);
        userBean.setId(baseParam.getUserId());
        userService.modify(userBean);
        return new RestResp<>(baseParam.getTt());
    }

    @POST
    @Path("/delete")
    @ApiOperation("删除用户")
    public RestResp<UserBean> delete(@BeanParam BaseParam baseParam,
                                     @ApiParam(required = true) @QueryParam("id") Integer id) {
        userService.remove(id);
        return new RestResp<>(baseParam.getTt());
    }


}
