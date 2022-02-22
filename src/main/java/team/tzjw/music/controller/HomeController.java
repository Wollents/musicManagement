package team.tzjw.music.controller;

import cn.dsna.util.images.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/index")
public class HomeController {

    @Autowired
    private UserService userService;

    //进入登录界面
    @RequestMapping("/main")
    public String main() {
        return "login";
    }

    //验证码
    @RequestMapping("/getCode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) {
        //生成验证码对象
        ValidateCode validateCode = new ValidateCode(120, 40, 4, 100);
        request.getSession().setAttribute("code", validateCode.getCode());
        try {
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //登录
    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password, String code, HttpServletRequest request) {

        Result result = userService.login(username, password, code, request);
        return result;

    }

    //进入主页面
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/pageTest")
    public String pageTest(){ return "main";}

    //进入注册页面
    @RequestMapping("/registers")
    public String registers(){return "registers";}


    //注册
    @RequestMapping("/register")
    @ResponseBody
    public Result register(String username,String password,String phone,String code,HttpServletRequest request){
        Result result =  userService.register(username,password,phone,code,request);
        return result;
    }

    //退出
    @RequestMapping("/quit")
    @ResponseBody
    public Result quit(HttpServletRequest request){
        Result result = userService.quit(request);
        return result;
    }

}