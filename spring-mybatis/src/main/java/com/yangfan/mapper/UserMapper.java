package com.yangfan.mapper;

import com.yangfan.model.User;
import com.yangfan.model.UserSexEnum;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    @Select("select * from users")
    @Results({
            @Result(property = "serSex", column = "ser_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    List<User> getAll();

    @Select("select * from users where id=#{id}")
    @Results({
            @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nick_name", column = "nick_name")
    })
    User getOne(Long id);


    @Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
    void insert(User user);

    @Delete("DELETE FROM users WHERE ID=#{ID}")
    void delete(Long id);
}
