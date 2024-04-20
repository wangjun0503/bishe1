package com.example.mapper;

import  com.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
//    @Select("select * from user where user_pho=#{user_pho}")
//    User findByuser_pho(String user_pho);

//    //添加
//    @Insert("insert into user(user_pho,password,create_time,update_time)" +
//            " values(#{user_pho},#{password},now(),now())")
//    void add(String user_pho, String password);

//    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
//    void update(User user);
//
//    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
//    void updateAvatar(String avatarUrl,Integer id);
//
//    @Update("update user set password=#{md5String},update_time=now() where id=#{id}")
//    void updatePwd(String md5String, Integer id);
    @Select("select  * from user where id =#{id}")
    User findById(Integer id);

    @Select("select  * from user where user_pho=#{user_pho}")
    User findByUserPho(Integer user_pho);

    @Insert("insert into user(user_pho,password,sex,name,createTime,updateTime,role_id)"+
    "values (#{user_pho},#{password},#{sex},#{name},now(),now(),2)")
    void add(Integer user_pho,String password,Integer sex,String name);
    @Update("update user set nickname=#{nickname},email=#{email},updateTime=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avatarUrl},updateTime=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update user set password=#{md5String},updateTime=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);
    @Update("update user set role_id={role_id} where id=#{id}")
    void updateRole(Integer role_id,Integer id);

}
