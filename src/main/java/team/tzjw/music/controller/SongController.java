package team.tzjw.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.Song;
import team.tzjw.music.service.SongService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;

    @RequestMapping("/getAll")
    @ResponseBody
    public Result getAll(Integer page, Integer limit) {
        Result result = songService.getAll(page, limit);
        return result;
    }

    @RequestMapping("deleteById")
    @ResponseBody
    public Result deleteById(String[] ids) {
        Result result = songService.deleteById(ids);
        return result;
    }

    @RequestMapping("/details")
    @ResponseBody
    public Song details(String id) {
        Song song = songService.details(id);
        return song;
    }

    @RequestMapping("/up")
    @ResponseBody
    public Result up(MultipartFile file, HttpServletRequest request) {
        Result result = songService.up(file, request);
        return result;
    }

//    @RequestMapping("/add")
//    @ResponseBody
//    public Result add(String stitle, String songpath, HttpServletRequest request) {
//        Result result = songService.add(stitle, songpath, request);
//        return result;
//    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public Result updateStatus(Integer status, String id) {
        Result result = songService.updateStatus(status, id);
        return result;
    }
    @RequestMapping("/editDetail")
    @ResponseBody
    public Song editDetail(String id){
        Song song = songService.editDetail(id);
        return song;
    }
    @RequestMapping("/update")
    @ResponseBody
    public Result mvUpdate(String id,String userid, Long playnum, Long likecounts){
        Result result = songService.update(id,userid, playnum, likecounts);
        return result;
    }

    @RequestMapping("/selectByIdLike")
    @ResponseBody
    public Result selectByIdLike(String id, Integer page, Integer limit,HttpServletRequest request) {
        Result result=songService.selectByIdLike(id, page, limit);
        return result;
    }
    @RequestMapping("/count")
    @ResponseBody
    public Result count() {

        Result result = songService.count();
        return result;
    }
}
