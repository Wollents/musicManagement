package team.tzjw.music.dao;

import org.apache.ibatis.annotations.Param;
import team.tzjw.music.pojo.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll(@Param("page") Integer page, @Param("limit") Integer limit);

    int updateByPrimaryKey(User record);

    int updatestate(@Param("state") Integer state, @Param("id") String id);

    int updateisAdmin(@Param("isadmin") Integer isadmin, @Param("id") String id);

    long countUser();

    List<User> selectAll();

    User selectByUserName(String username);

    int userUpdate(@Param("id") String id, @Param("name") String name, @Param("phone") String phone, @Param("password") String password, @Param("sex") String sex, @Param("email") String email, @Param("pic") String pic);

    User editUser(String id);

    int getNums();
}