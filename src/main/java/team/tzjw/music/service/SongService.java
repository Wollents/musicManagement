package team.tzjw.music.service;

import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.Song;

import javax.servlet.http.HttpServletRequest;

public interface SongService {


    Result getAll(Integer page, Integer limit);

    Result deleteById(String[] ids);

    Song details(String id);

    Result up(MultipartFile file, HttpServletRequest request);

    Result add(String stitle, String songpath, HttpServletRequest request);

    Result updateStatus(Integer status, String id);

    Result update(String id, String userid, Long playnum, Long likecounts);

    Result selectByIdLike(String id, Integer page, Integer limit);

    Result count();

    Song editDetail(String id);
}
