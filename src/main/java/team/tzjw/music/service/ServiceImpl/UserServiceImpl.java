package team.tzjw.music.service.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.dao.UserMapper;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.User;
import team.tzjw.music.service.UserService;
import team.tzjw.music.util.Md5Utils;
import team.tzjw.music.util.ImgUpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getAll(Integer page,Integer limit) {
        page=(page-1)*limit;
        List<User> list = userMapper.selectAll(page,limit);
        long count=userMapper.countUser();
        Result result=new Result();
        result.setCode(0);
        result.setData(list);
        result.setCount(count);
        return result;
    }

    @Override
    public List<User> get(String id) {
        List<User> list = (List<User>) userMapper.selectByPrimaryKey(id);
        return list;
    }
    @Override
    public Result count() {
        Result result = new Result();
        int i = userMapper.getNums();
        try {
            result.setCode(i);
            result.setMsg("成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("出现未知错误");
            result.setCode(500);
            return result;
        }

    }

    @Override
    public String delete(String id) {
        Integer i = userMapper.deleteByPrimaryKey(id);
        if(i>0){
            return "SUCCESS";
        }
        else{
            return "ERROR";
        }
    }

    @Override
    public Result add(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setPic("img/"+user.getPic());
        String password = user.getPassword();
        user.setPassword(Md5Utils.encryption(user.getName(),password));
        userMapper.insert(user);
        Result result = new Result();
        try {
            int i = userMapper.insert(user);
            if (i > 0) {
                result.setCode(200);
            } else {
                result.setCode(500);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setCode(500);
            return result;
        }
    }

    @Override
    public Result updatestate(Integer state, String id) {
        int i = userMapper.updatestate(state, id);
        Result result = new Result();
        try {
            if (i > 0) {
                result.setMsg("SUCCESS");
            } else {
                result.setMsg("ERROR");
            }
            return result;
        } catch (Exception e) {
            //在控制台
            e.printStackTrace();
            result.setMsg("系统繁忙，请刷新页面尝试");
            result.setCode(500);
            //设置手动回滚 或者在controller try catch
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return result;
        }
    }

    @Override
    public Result updateisAdmin(Integer isadmin, String id) {
        int i = userMapper.updateisAdmin(isadmin, id);
        Result result = new Result();
        try {
            if (i > 0) {
                result.setMsg("SUCCESS");
            } else {
                result.setMsg("ERROR");
            }
            return result;
        } catch (Exception e) {
            //在控制台
            e.printStackTrace();
            result.setMsg("系统繁忙，请刷新页面尝试");
            result.setCode(500);
            //设置手动回滚 或者在controller try catch
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return result;
        }
    }

    @Transactional
    @Override
    public Result deleteById(String[] ids) {
        Result result = new Result();
        try {
            for (int i = 0; i < ids.length; i++) {
                int p = userMapper.deleteByPrimaryKey(ids[i]);
            }
            result.setMsg("SUCCESS");
            result.setCode(200);
            return result;
        } catch (Exception e) {
            //在控制台
            e.printStackTrace();
            result.setMsg("系统繁忙，请刷新页面尝试");
            result.setCode(500);
            //设置手动回滚 或者在controller try catch
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return result;
        }
    }

    @Override
    public Result upPic(MultipartFile file, HttpServletRequest request) {
        String pic = ImgUpUtils.upfile(file, request);
        Result result = new Result();
        User user=new User();
        user.setPic("pic/"+pic);
        pic=user.getPic();
        result.setMsg(pic);
        return result;
    }

    @Override
    public Result update(User user) {
        Result result = new Result();
        try {
            int i = userMapper.updateByPrimaryKey(user);
            if (i > 0) {
                result.setCode(200);
            } else {
                result.setCode(500);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setCode(500);
            return result;
        }

    }

    @Transactional
    @Override
    public Result login(String username, String password, String code, HttpServletRequest request) {
        Result result=new Result();
        //确认验证码正确与否
        String codeValue=(String)request.getSession().getAttribute("code");
        if(codeValue.equalsIgnoreCase(code)){
            User user=userMapper.selectByUserName(username);
            if(user==null||!user.getPassword().equals(Md5Utils.encryption(username,password))){
                result.setCode(500);
                result.setMsg("账号或密码错误！");
            }else{
                user.setState(1);
                try {
                    int i=userMapper.updateByPrimaryKey(user);
                    if(i>0){
                        result.setCode(200);
                        result.setMsg("success");
                        result.setCode(200);
                        request.getSession().setAttribute("user",user);
                    }else{
                        result.setCode(500);
                        result.setMsg("错误！");
                    }
                    return  result;
                }catch (Exception e){
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    result.setCode(500);
                    return  result;
                }
            }
        }else{
            result.setCode(500);
            result.setMsg("验证码错误！");
        }
        return result;
    }

    @Transactional
    @Override
    public Result register(String username, String password, String phone, String code,HttpServletRequest request) {
        Result result =new Result();
        User user=new User();
        String codeValue=(String)request.getSession().getAttribute("code");
        if(codeValue.equalsIgnoreCase(code)) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            user.setName(username);
            user.setPassword(Md5Utils.encryption(username,password));
            user.setState(0);
            user.setSex("男");
            user.setPhone(phone);
            user.setEmail("xxx@qq.com");
            user.setPic("pic/IMG_0006.jpg");
            user.setIsadmin(0);
            try {
                int i = userMapper.insert(user);
                if(i>0){
                    result.setCode(200);
                }else{
                    result.setCode(500);
                    result.setMsg("错误！");
                }
                return  result;
            }catch (Exception e){
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                result.setCode(500);
                return  result;
            }
        }else{
            result.setCode(500);
            result.setMsg("验证码错误！");
        }
        return result;
    }

    @Transactional
    @Override
    public Result quit(HttpServletRequest request) {
        Result result =new Result();
        User users= (User) request.getSession().getAttribute("user");
        users.setState(0);
        try {
            int i = userMapper.updateByPrimaryKey(users);
            if(i>0){
                result.setMsg("已退出");
                result.setCode(200);
            }else{
                result.setCode(500);
                result.setMsg("错误！");
            }
            return  result;
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setCode(500);
            return  result;
        }

    }

    @Override
    public Result userUpdate(String id, String name,  String phone, String password, String sex,  String email,  String pic) {
        Result result = new Result();
        try {
            User user=new User();
            user.setPassword(Md5Utils.encryption(name,password));
            password=user.getPassword();
            int i = userMapper.userUpdate(id, name,  phone, password, sex, email, pic);
            if(i > 0){
                result.setMsg("SUCCESS");
                result.setCode(200);
            }else{
                result.setCode(500);
                result.setMsg("ERROR");
            }
            return result;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            result.setCode(500);
            result.setMsg("ERROR");
            return result;
        }
    }

    @Override
    public User editUser(String id) {
        User user=userMapper.editUser(id);
        return user;
    }

}
