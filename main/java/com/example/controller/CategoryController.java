package com.example.controller;

import com.example.pojo.Category;
import com.example.pojo.Result;
import com.example.service.patientcategoryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private patientcategoryservice patientcategoryservice;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        patientcategoryservice.update(category);
        return Result.success();
    }
    @PostMapping("create")
    public Result create(Category category){
        patientcategoryservice.add(category);
        return Result.success();
    }
    //更新结果照片
    public void update(Integer patientName,String user_ill_res){
        patientcategoryservice.updateresule(patientName,user_ill_res);




    }

    @GetMapping
    public Result<List<Map<String, Object>> > list(){
        List<Map<String, Object>>  cs = patientcategoryservice.list();
        System.out.print(cs);

        return Result.success(cs);
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category c = patientcategoryservice.findById(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        patientcategoryservice.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        patientcategoryservice.deleteById(id);
        return Result.success();
    }
    void updateresule(Integer patientName,String user_ill_res){

    };
}
