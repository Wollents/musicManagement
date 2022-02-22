package team.tzjw.music.service;

import team.tzjw.music.pojo.Result;
import team.tzjw.music.pojo.Type;

public interface TypeService {

    public Result getAll(Integer page, Integer limit);

    Type selectById(String tid);

    Result deleteById(String[] tid);

    Result upload(String tname, String tdesc);

    Result edit(String tid, String tname, String tdesc);

    Result count();

    Result getSongtype();

    Result getSongtypeNums();
}
