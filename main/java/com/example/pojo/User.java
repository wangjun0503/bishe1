package com.example.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
//lombok  在编译阶段,为实体类自动生成setter  getter toString
// pom文件中引入依赖   在实体类上添加注解
@Data
public class User {
    @NotNull
    private Integer id;//主键ID

    private Integer user_pho;//手机号

    @JsonIgnore//让springmvc把当前对象转换成json字符串的时候,忽略password,最终的json字符串中就没有password这个属性了
    private String password;//密码
    private Integer sex;//性别 1男 2女


    private String name;//用户名
    private Integer role_id;//0超管，1医生，2病人
//    @NotEmpty
//    @Pattern(regexp = "^\\S{1,10}$")
//    private String nickname;//昵称

//    @NotEmpty
//    @Email
//    private String email;//邮箱
//    private String user_pic;//用户头像地址

    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
