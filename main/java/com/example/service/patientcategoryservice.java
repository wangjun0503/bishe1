package com.example.service;

import com.example.pojo.Category;
import java.util.Map;

import java.util.List;

public interface patientcategoryservice {
    //新增分类
    void add(Category category);

    //列表查询
    List<Map<String, Object>>  list();

    //根据id查询分类信息
    Category findById(Integer id);


    void updateresule(Integer patientName,String user_ill_res);

    //更新分类
    void update(Category category);

    //删除分类
    void deleteById(Integer id);
}
