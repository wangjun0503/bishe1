package com.example.service;

import com.example.pojo.User;

public interface UserService {
    //根据用户名查询用户
    User findByUserPho(Integer user_pho);

    //注册
    void register(Integer phone, String password,Integer sex,String name);

    //更新
    void update(User user);

    //更新头像
    void updateAvatar(String avatarUrl);

    //更新密码
    void updatePwd(String newPwd);

    User findById(Integer id);
}
