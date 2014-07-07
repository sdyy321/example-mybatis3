package me.poplaris.mybatis;

import me.poplaris.mybatis.bean.User;
import me.poplaris.mybatis.dao.UserDao;
import me.poplaris.mybatis.dao.UserDaoWithAnno;
import me.poplaris.mybatis.datasource.MyDefaultDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangyong
 * Date: 14-7-4 下午5:38
 */
public class TestMain {

  @Test
  public void buildUseXml() throws IOException {
//    String resource = "org/mybatis/example/mybatis-config.xml";
    String resource = "mybatis-config.xml";
    //从 XML 中构建 SqlSessionFactory
    Reader reader = Resources.getResourceAsReader(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    //从 SqlSessionFactory 中获取 SqlSession
    SqlSession session = sqlSessionFactory.openSession();
    try {
//      User user = session.selectOne("me.poplaris.mybatis.dao.UserDao.getUserId",1);
      UserDao userDao = session.getMapper(UserDao.class);
      User user = userDao.getUserId(1);
      System.out.println(user);
    } finally {
      session.close();
    }
  }

  @Test
  public void buildUseAnno(){
    String url = "jdbc:mysql://127.0.0.1:3306/test?autoReconnect=true";
    String userName = "root";
    String password = "root";
    //数据源
    DataSource dataSource = new MyDefaultDataSource(url,userName,password);
    //事务工厂
    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment = new Environment("development", transactionFactory, dataSource);
    Configuration configuration = new Configuration(environment);
    //绑定DAO mapper
    configuration.addMapper(UserDaoWithAnno.class);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    SqlSession session = sqlSessionFactory.openSession();
    try {
      UserDaoWithAnno userDao = session.getMapper(UserDaoWithAnno.class);
      User user = userDao.getUserId(1);
      System.out.println(user);
      List<User> userList = userDao.getUsers();
      System.out.println(userList);
    } finally {
      session.close();
    }
  }
}
