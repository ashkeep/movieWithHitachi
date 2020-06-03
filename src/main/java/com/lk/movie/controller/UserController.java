package com.lk.movie.controller;


import com.alibaba.fastjson.JSONObject;
import com.lk.movie.entity.TUser;

import com.lk.movie.util.ResponseTemplate;
import com.lk.movie.service.UserBiz;
import com.lk.movie.util.kit.ConstantKit;
import com.lk.movie.util.kit.Md5TokenGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.rmi.Remote;


/**
 * @author lk56054
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    Md5TokenGenerator tokenGenerator;

    @Autowired
    UserBiz userBiz;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation("用户登录接口")
    public ResponseTemplate login(@RequestBody(required = false) JSONObject userInfo) {
        String username = userInfo.getString("username");
        String password = userInfo.getString("password");
        JSONObject result = new JSONObject();
        try {
            TUser user = userBiz.login(username, password);
            if (user != null) {
                Jedis jedis = new Jedis("localhost", 6379);
                String token = tokenGenerator.generate(username, password);
                jedis.set(username, token);
                jedis.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
                jedis.set(token, username);
                jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                Long currentTime = System.currentTimeMillis();
                jedis.set(token + username, currentTime.toString());

                //用完关闭
                jedis.close();

                result.put("status", "登录成功");
                result.put("token", token);
            } else {
                result.put("status", "登录失败");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseTemplate.builder()
                .code(200)
                .message("登录接口")
                .data(result)
                .build();

    }

    @RequestMapping(value = "regist", method = RequestMethod.POST)
    @ApiOperation("用户登录接口")
    public ResponseTemplate regist(@RequestBody(required = false) JSONObject userInfo) {
        String username = userInfo.getString("username");
        String password = userInfo.getString("password");
        String phone = userInfo.getString("phone");
        JSONObject result = new JSONObject();
        try {
            TUser tUser = userBiz.registUser(username, password, phone);
            if (tUser != null) {
                Jedis jedis = new Jedis("localhost", 6379);
                String token = tokenGenerator.generate(username, password);
                jedis.set(username, token);
                jedis.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
                jedis.set(token, username);
                jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                Long currentTime = System.currentTimeMillis();
                jedis.set(token + username, currentTime.toString());
                //用完关闭
                jedis.close();
                result.put("status", "注册成功");
                result.put("token", token);
            } else {
                result.put("status", "注册失败");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseTemplate.builder()
                .code(200)
                .message("用户注册接口")
                .data(result)
                .build();

    }

    @RequestMapping(value = "Changeinfo", method = RequestMethod.POST)
    @ApiOperation("修改用户信息")
    public ResponseTemplate changeInfo(@RequestBody(required = false) JSONObject userInfo,
                                       @RequestHeader("TOKEN") String TOKEN) {
        String oldName = userInfo.getString("oldName");
        String newName = userInfo.getString("newName");
        String newPassword = userInfo.getString("newPassword");
        JSONObject result = new JSONObject();
        try {

                Jedis jedis = new Jedis("localhost", 6379);
                String token = jedis.get(oldName);
                if (token==TOKEN){
                    userBiz.changepwd(newName, newPassword);
                    result.put("status", "修改成功");
                    result.put("token", token);
                }else{

                }
                //用完关闭
//            Remote URL test failed: cannot spawn git: Exec format error
                jedis.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseTemplate.builder()
                .code(200)
                .message("用户注册接口")
                .data(result)
                .build();

    }

}
