package team.tzjw.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.pojo.MV;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.service.MVService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mv")
public class MVController {

    @Autowired
    private MVService mvService;

    @RequestMapping("/getAll")
    @ResponseBody
    public Result getAll(Integer page, Integer limit){
        Result result = mvService.getAll(page, limit);
        return result;
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public Result updateStatus(Integer status, String id){
        Result result = mvService.updateStatus(status, id);
        return result;
    }

    @RequestMapping("deleteById")
    @ResponseBody
    public Result deleteById(String[] id){
        Result result = mvService.deleteById(id);
        return result;
    }

    @RequestMapping("/details")
    @ResponseBody
    public MV details(String id){
        MV mv = mvService.details(id);
        return mv;
    }

    @RequestMapping("/up")
    @ResponseBody
    public Result up(MultipartFile file, HttpServletRequest request){
        Result result = mvService.up(file, request);
        return result;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(String mvpath, String singer, String mvdesc, HttpServletRequest request){
        Result result = mvService.add(mvpath,singer,mvdesc, request);
        return result;
    }

    @RequestMapping("/editDetail")
    @ResponseBody
    public MV editDetail(String id){
        MV mv = mvService.editDetail(id);
        return mv;
    }

    @RequestMapping("/mvUpdate")
    @ResponseBody
    public Result mvUpdate(String id, String userid, String mvdesc, Long playnum, Long likecounts){
        Result result = mvService.mvUpdate(id, userid, mvdesc,playnum, likecounts);
        return result;
    }
    @RequestMapping("/count")
    @ResponseBody
    public Result count() {

        Result result = mvService.count();
        return result;
    }

}
