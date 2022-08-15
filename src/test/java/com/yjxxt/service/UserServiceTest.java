package com.yjxxt.service;

import com.yjxxt.pojo.User;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {
    private UserService userService=null;


    @Before
    public void init(){
        System.out.println("测试方法执行前执行.......");
        userService=new UserService();
    }

    @Test
    public void addUser(){
        userService.addUser(new User(3,"yjh","123456","mengya","",""));
    }


    @Test
    public void listUser(){
        userService.listUser();
    }


    @Test
    public void updateUser(){
        System.out.println("更新之前.....");
        userService.listUser();
        System.out.println("更新之后.....");
        userService.updateUser(new User(2,"test","123456","test","happy",""));
        userService.listUser();
    }


    @Test
    public void delUser(){
        userService.delUser(2);
        System.out.println("删除后的记录...");
        userService.listUser();
    }

    @Test
    public void login(){
        userService.login("admin","123456");
    }

}
