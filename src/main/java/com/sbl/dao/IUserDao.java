package com.sbl.dao;

import com.sbl.domain.QueryVo;
import com.sbl.domain.User;

import java.util.List;

/**
 * 用户的持久层接口
 *
 * 可以后来创建----代理对象-----，用于数据库的操作
 */
public interface IUserDao {

    /**
     * 查询所有的数据
     * @return
     *
     * 可以使用注解的方式
     * @Seclct ("select *from user")
     */
    List<User> findAll();

    //用于保存用户信息
    void saveUser(User user);


    //更新用户操作
    void updateUser(User user);

    //删除用户操作
    void delUser(int id);

    /**
     * 根据id查询用户信息
     */
    User findById(int id);


    /**
     * 根据名称模糊查询
     */
    List<User> findByLikeName(String name);

    /**
     * 聚合函数
     * 查询总的用户数
     */
    int findTotalUser();


    List<User> findUserByVo(QueryVo vo);
}
