package team.tzjw.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.Type;
import team.tzjw.music.service.TypeService;

@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @RequestMapping("/getAll")
    @ResponseBody
    public Result getAll(Integer page, Integer limit){
        Result result = typeService.getAll(page,limit);
        return result;
    }
    @RequestMapping("/details")
    @ResponseBody
    public Type selectById(String tid){
        Type type = typeService.selectById(tid);
        return type;
    }
    @RequestMapping("/deleteById")
    @ResponseBody
    public Result deleteById(String[] tid){

        Result result = typeService.deleteById(tid);
        return result;
    }
    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(String tname, String tdesc){

        Result result = typeService.upload(tname,tdesc);
        return result;
    }
    @RequestMapping("/edit")
    @ResponseBody
    public Result edit(String tid,String tname, String tdesc){

        Result result = typeService.edit(tid,tname,tdesc);
        return result;
    }
    @RequestMapping("/count")
    @ResponseBody
    public Result count(){

        Result result = typeService.count();
        return result;
    }
    @RequestMapping("/getSongtype")
    @ResponseBody
    public Result getSongtype(){

        Result result = typeService.getSongtype();
        return result;
    }
    @RequestMapping("/getSongtypeNums")
    @ResponseBody
    public Result getSongtypeNums(){

        Result result = typeService.getSongtypeNums();
        return result;
    }
}
