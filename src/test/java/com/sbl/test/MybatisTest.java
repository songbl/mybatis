package com.sbl.test;



import com.sbl.dao.IUserDao;
import com.sbl.domain.QueryVo;
import com.sbl.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before//用于在测试方法执行之前执行
    public void init()throws Exception{
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After//用于在测试方法执行之后执行
    public void destroy()throws Exception{
        //提交事务
        sqlSession.commit();
        //6.释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll(){
        //5.执行查询所有方法
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println(user);
        }

    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUserName("save_test_id");
        user.setAddress("北京市");
        user.setSex("男");
        user.setBirthday(new Date());
        System.out.println("保存操作之前："+user);
        //5.执行保存方法
        userDao.saveUser(user);

        System.out.println("保存操作之后："+user);
    }


    /**
     * 更新操作
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setUserId(49);
        user.setUserName("吴猛打");
        user.setAddress("中国");

        System.out.println("保存操作之前："+user);
        //5.执行保存方法
        userDao.updateUser(user);

        System.out.println("保存操作之后："+user);
    }

    /**
     * 删除用户
     */
    @Test
    public void delUser(){
        //5.执行保存方法
        userDao.delUser(46);
    }

    /**
     * 测试查询对象
     */
    @Test
    public void findOne(){
        User user = userDao.findById(42);
        System.out.println(user);
    }

    /**
     * 测试查询对象
     */
    @Test
    public void findLikeName(){
        List<User> userList= userDao.findByLikeName("%王");
        System.out.println(userList);

//        List<User> userList= userDao.findByLikeName("王");
//          select *from user where username like '%${value}%'  xml中的第二种写法，不安全 statement

    }

    /**
     * 查询总记录数
     */
    @Test
    public void findTotalCount(){
        int totalCount = userDao.findTotalUser();
        System.out.println(totalCount);
    }


    /**
     * 通过对象中的属性来查询
     * queryVo作为一个包装类，可以封装多个不同类型的成员变量
     * QueryVo作为查询条件
     */
    @Test
    public void testFindByVo(){
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setUserName("%王");
        queryVo.setUser(user);
        List<User> userList= userDao.findUserByVo(queryVo);
        System.out.println(userList);
    }
}
