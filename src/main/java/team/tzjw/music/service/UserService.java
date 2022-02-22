package team.tzjw.music.service;

import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    Result getAll(Integer page, Integer limit);

    List<User> get(String id);

    String delete(String id);

    Result add(User user);

    Result updatestate(Integer state, String id);

    Result updateisAdmin(Integer isadmin, String id);

    Result deleteById(String[] ids);

    Result upPic(MultipartFile file, HttpServletRequest request);

    Result update(User user);
    Result login(String name, String password, String code, HttpServletRequest request);

    Result register(String username, String password, String phone, String code, HttpServletRequest request);


    Result quit(HttpServletRequest request);

    Result count();

    Result userUpdate(String id, String name, String phone, String password, String sex, String email, String pic);

    User editUser(String id);
}
