package me.poplaris.mybatis.dao;

import me.poplaris.mybatis.bean.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * User: poplar
 * Date: 14-7-4 下午5:59
 */
public interface UserDaoWithAnno {

  @Select("select * from user where id= #{id}")
  public User getUserId(int id);

  @Select("select * from user")
  public List<User> getUsers();
}
