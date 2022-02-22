package team.tzjw.music.service;

import org.springframework.web.multipart.MultipartFile;
import team.tzjw.music.pojo.MV;
import team.tzjw.music.pojo.Result;

import javax.servlet.http.HttpServletRequest;

public interface MVService {

    Result getAll(Integer page, Integer limit);

    Result updateStatus(Integer status, String id);

    Result deleteById(String[] id);

    MV details(String id);

    Result up(MultipartFile file, HttpServletRequest request);

    Result add(String mvpath, String singer, String mvdesc, HttpServletRequest request);

    MV editDetail(String id);

    Result mvUpdate(String id, String userid, String mvdesc, Long playnum, Long likecounts);

    Result count();
}
