package team.tzjw.music.service.ServiceImpl;

import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.dao.SongMapper;
import team.tzjw.music.pojo.Page;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.Song;
import team.tzjw.music.pojo.User;
import team.tzjw.music.service.SongService;
import team.tzjw.music.util.AudioUpUtils;
import team.tzjw.music.util.AudioUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;

    @Override
    public Result getAll(Integer page, Integer limit) {
        Page page1 = new Page(page, limit);
        List<Song> list = songMapper.selectAll(page1);
        Long count = songMapper.countSong();
        Result result = new Result();
        result.setCode(0);
        result.setData(list);
        result.setCount(count);
        return result;
    }

    @Override
    @Transactional
    public Result deleteById(String[] ids) {
        Result result = new Result();
        try {
            for (int i = 0; i < ids.length; i++) {
                int p = songMapper.deleteByPrimaryKey(ids[i]);
            }
            result.setMsg("success");
            result.setCode(200);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("系统繁忙，请刷新页面后再试！");
            result.setCode(500);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return result;
        }

    }

    @Override
    public Song details(String id) {
        Song song = songMapper.detail(id);
        return song;
    }

    @Override
    public Result up(MultipartFile file, HttpServletRequest request) {
        String path = AudioUpUtils.upfile(file, request);
        Result result = new Result();
        result.setMsg(path);
        return result;
    }

    @Override
    @Transactional
    public Result add(String stitle, String songpath, HttpServletRequest request) {
        Song song = new Song();
        String id = UUID.randomUUID().toString();
        song.setId(id);
        song.setStitle(stitle);
        song.setSongpath("audio/" + songpath);
        User user = (User)request.getSession().getAttribute("user");
        String userId = user.getId();
        song.setUserid(userId);
        song.setLikecounts(0l);
        song.setStatus(0);
        song.setPlaynum(0l);
        song.setTid("0");
        song.setLyricpath("lyic/default.png");
        song.setAlbumpic("pic/default.jpg");
        song.setSinger("AAAAA");
        song.setAlbumname("XXXXX");
        song.setSize(5.00);
        Date date = new Date();
        song.setCreatetime(date);
        MultimediaInfo info = AudioUtils.analysis(songpath, request);
        Long duration = info.getDuration() / 1000;
        Float sTime = Float.valueOf(duration);
        song.setSongtime(sTime);
        Result result = new Result();
        try {
            int i = songMapper.insert(song);
            if (i > 0) {
                result.setMsg("success");
                result.setCode(200);
            } else {
                result.setMsg("error");
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
    public Result updateStatus(Integer status, String id) {
        int i = songMapper.updateStatus(status, id);
        Result result = new Result();
        if (i > 0) {
            result.setMsg("success");
        } else {
            result.setMsg("error");
        }
        return result;
    }

    @Transactional
    @Override
    public Result update(String id,String userid, Long playnum, Long likecounts) {
        Result result = new Result();
        try {
            int i = songMapper.update(id,userid,playnum,likecounts);
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
    public Result selectByIdLike(String id, Integer page, Integer limit) {
        Result result = new Result();
        page = (page-1)*10;
//        Page page1 = new Page(page, limit);
        List<Song> list = songMapper.selectByIdLike(id, page,limit);
        if(list==null){
            result.setMsg("暂时无数据");
        }else{
            result.setCode(0);
            result.setTotal(list.size());
            result.setData(list);
        }
        return result;
    }
    @Override
    public Result count() {
        Result result = new Result();
        int i = songMapper.getNums();
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
    public Song editDetail(String id) {
        Song song = songMapper.editDetail(id);
        return song;
    }
}
