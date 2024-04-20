package com.example.mapper;

import com.example.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.Map;

import java.util.List;

@Mapper
public interface CategoryMapper {
//    //新增
//    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) " +
//            "values(#{patientName},#{patientSex},#{createUser},#{createTime},#{updateTime})")
//    void add(Category category);
//
//    //查询所有
//    @Select("select * from category where create_user = #{userId}")
//    List<Map<String, Object>>  list(Integer userId);
//
//    //根据id查询
//    @Select("select * from category where id = #{id}")
//    Category findById(Integer id);
//
//    //更新
//    @Update("update category set category_name=#{patientName},category_alias=#{patientSex},update_time=#{updateTime} where id=#{id}")
//    void update(Category category);
//
//    //根据id删除
//    @Delete("delete from category where id=#{id}")
//    void deleteById(Integer id);
//    新增
    @Insert("insert into category(user_ill_pic,patientName,creat_time,update_time) " +
            "values(#{user_ill_pic},#{creat_user},now(),now())")
    void add(Category category);

    //更新病人结果照片
    @Update("insert into category set user_ill_res=#{user_ill_res} where patientName=#{patientName}")
    void updateresule(Integer patientName,String user_ill_res);



    //查询所有
    @Select(" SELECT c.id, c.patientName, c.user_ill_pic, c.user_ill_res, c.create_time, c.update_time,c.doc_dia,c.doc_nam, u.name as extraName, u.sex as extraSex\n" +
            "        FROM category c\n" +
            "                 JOIN user u ON c.patientName = u.id\n" +
            "        WHERE c.patientName = #{userId}")
    List<Map<String, Object>>  list(Integer userId);

    //根据id查询
    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    //更新
    @Update("update category set doc_dia=#{doc_dia} doc_nam=#{doc_nam} where create_user=#{create_user}")
    void update(Category category);

    //根据id删除
    @Delete("delete from category where id=#{id}")
    void deleteById(Integer id);
}
