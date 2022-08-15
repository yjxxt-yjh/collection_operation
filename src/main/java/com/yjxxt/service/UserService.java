package com.yjxxt.service;

import com.yjxxt.pojo.User;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Consumer;

/**
 * 云日记-用户管理模块
 *    用户登录
 *    用户注册
 *    用户列表展示
 *    用户信息更新
 *    用户信息删除
 */
public class UserService {
    /**
     * 用户记录如何存储???
     *    List-->ArrayList<User>
     *    Map-->HashMap<Integer,User>
     *    Set-->HashSet<User>
     *    List<Map<k,v>>
     */
    private List<User> users;

    public UserService() {
        users =new ArrayList<User>();
        users.add(new User(1,"admin","123456","admin","",""));
        users.add(new User(2,"test","123456","test","",""));
    }

    /**
     * 添加用户记录
     * @param user
     */
    public void addUser(User user){
        /**
         * 1.参数合法校验
         *     用户名 密码 昵称 非空
         * 2.用户名唯一  & 昵称唯一
         * 3.执行添加 返回结果
         */
        if(null == user){
            throw  new RuntimeException("用户记录为空!");
        }
        if(StringUtils.isBlank(user.getUserName())){
            throw  new RuntimeException("用户名称不能为空!");
        }
        if(StringUtils.isBlank(user.getUserPwd())){
            throw  new RuntimeException("用户密码不能为空!");
        }

        /**
         * 用户名唯一校验
         */
        for (User temp : users) {
            if(temp.getUserName().equals(user.getUserName())){
                throw  new RuntimeException("用户名已存在!");
            }
        }
        if(users.contains(user)){
            throw  new RuntimeException("用户名已存在!");
        }
       /*long count = users.stream()
                            .filter(u->u.getUserName().equals(user.getUserName()))
                            .count();
       if(count==1){
           throw  new RuntimeException("用户名已存在!");
       }*/
       users.add(user);
    }


    public void listUser(){
       /* for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }*/
       /* for (User user : users) {
            System.out.println(user);
        }*/
        /*Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }*/
        ListIterator<User> userListIterator = users.listIterator();
        while (userListIterator.hasNext()){
            System.out.println(userListIterator.next());
        }
        /*users.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user);
            }
        });*/
        /*users.forEach(u->{
            System.out.println(u);
        });*/
    }

    /**
     * 更新用户记录
     * @param user
     */
    public void updateUser(User user){
        /**
         * 1.校验
         *    用户名 密码 昵称 非空
         * 2.根据id 查询用户记录是否存在
         *    不存在->抛异常(更新记录不存在)
         * 3.记录存在，判断用户名 昵称是否出现重复
         *      用户名唯一校验
         *      昵称唯一校验
         * 4.执行更新 判断结果
         */
        if (null == user){
            throw  new RuntimeException("用户记录为空!");
        }
        if (StringUtils.isBlank(user.getUserName())){
            throw  new RuntimeException("用户名称不能为空!");
        }
        if(StringUtils.isBlank(user.getUserPwd())){
            throw  new RuntimeException("用户密码不能为空!");
        }
        if(StringUtils.isBlank(user.getNick())){
            throw  new RuntimeException("用户昵称不能为空!");
        }
        if(user.getId()==null|| null == findUserByUserId(user.getId())){
            throw  new RuntimeException("待更新的记录不存在!");
        }

        /**
         * 用户名改动
         *   改动前:test
         *   改动后:
         *      abc  count=0
         *      test  count=1
         *      admin  count=1
         */
        long count = users.stream()
                .filter(u -> u.getUserName().equals(user.getUserName()))
                .filter(u->!(u.getId().equals(user.getId())))
                .count();
        if(count==1 ){
            throw  new RuntimeException("用户名已存在!");
        }

        count = users.stream()
                .filter(u -> u.getNick().equals(user.getNick()))
                .filter(u->!(u.getId().equals(user.getId())))
                .count();

        if(count==1 ){
            throw  new RuntimeException("用户昵称已存在!");
        }

        // 执行更新  根据id 查找
        users.set(users.indexOf(findUserByUserId(user.getId())),user);
    }

    private User findUserByUserId(Integer id) {
        Optional<User> optionalUser = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }


    public void delUser(Integer userId){
        if (null == userId || null == findUserByUserId(userId)){
            throw new RuntimeException("待删除记录不存在！");
        }
        users.remove(findUserByUserId(userId));
    }

    public void login(String userName,String userPwd){
        if (StringUtils.isBlank(userName)){
            throw new RuntimeException("用户名不能为空！");
        }
        if (StringUtils.isBlank(userPwd)){
            throw new RuntimeException("用户密码不能为空！");
        }
        Integer index = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(userName)){
                index = i;
                break;
            }
        }
        if (index == null){
            throw  new RuntimeException("用户名不存在！");
        }
        if (users.get(index).getUserPwd().equals(userPwd)){
            System.out.println("登陆成功！");
        }else {
            throw new RuntimeException("用户密码错误！");
        }
    }

    public List<Integer> queryAllId() {
        List<Integer> list = new ArrayList<>();
        for (User user : users) {
            list.add(user.getId());
        }
        return list;
    }
}
