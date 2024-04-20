package com.example.controller;

import com.example.pojo.Article;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.patientservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private patientservice patientservice;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        patientservice.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
       PageBean<Article> pb =  patientservice.list(pageNum,pageSize,categoryId,state);
       return Result.success(pb);
    }
}
