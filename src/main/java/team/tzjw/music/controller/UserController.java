package team.tzjw.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.User;
import team.tzjw.music.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/getAll")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public Result getAll(Integer page,Integer limit){
        Result result=userService.getAll(page,limit);
        return result;
    }
    @RequestMapping("/get")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public List get(String id){
        List<User> list=userService.get(id);
        return list;
    }
    @RequestMapping("/delete")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public String delete(@RequestParam("id") String id){
        String s=userService.delete(id);
        return s;
    }
    @RequestMapping("/add")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public Result add(User user){
        Result result = new Result();
        try{
            userService.add(user);
            return result;
        }catch (Exception e){
            result.setMsg("error");
            result.setCode(400);
            return result;
        }
    }
    @RequestMapping("/update")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public Result update(User user){
        Result result = new Result();
        try{
            userService.update(user);
            return result;
        }catch (Exception e){
            result.setMsg("error");
            result.setCode(400);
            return result;
        }
    }

    @RequestMapping("/updatestate")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public Result updateStates(Integer state,String id){
        Result result=userService.updatestate(state,id);
        return result;
    }
    @RequestMapping("/updateisAdmin")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public Result updateisAdmin(Integer isadmin,String id){
        Result result=userService.updateisAdmin(isadmin,id);
        return result;
    }
    @RequestMapping("/deleteById")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public Result deleteById(String[] ids){
        Result result=userService.deleteById(ids);
        return result;
    }
    @RequestMapping("/upPic")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public Result upPic(MultipartFile file, HttpServletRequest request){
        Result result=userService.upPic(file,request);
        return result;
    }

    @RequestMapping("/userUpdate")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public Result userUpdate(String id, String name,  String phone, String password, String sex,  String email,  String pic){
        Result result=userService.userUpdate( id, name,  phone, password, sex,  email, pic);
        return result;
    }
    @RequestMapping("/editUser")
    @ResponseBody//自动将数据转换为json格式，避免被视图解析器解析
    public User editUser(String id){
        User user=userService.editUser(id);
        return user;
    }

    @RequestMapping("/count")
    @ResponseBody
    public Result count() {

        Result result = userService.count();
        return result;
    }
}
