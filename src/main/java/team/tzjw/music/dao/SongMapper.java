package team.tzjw.music.dao;

import org.apache.ibatis.annotations.Param;
import team.tzjw.music.pojo.Page;
import team.tzjw.music.pojo.Song;

import java.util.List;

public interface SongMapper {
    int deleteByPrimaryKey(String id);

    int insert(Song record);

    Song selectByPrimaryKey(String id);

    List<Song> selectAll();

    int updateByPrimaryKey(Song record);

    List<Song> selectAll(Page page);

    Long countSong();

    Song detail(String id);

    int updateStatus(@Param("status") Integer status, @Param("id") String id);

    int update(@Param("id") String id, @Param("userid") String userid, @Param("playnum") Long playnum, @Param("likecounts") Long likecounts);


    List<Song> selectByIdLike(@Param("id") String id, @Param("page") Integer page, @Param("limit") Integer limit);

    int getNums();

    Song editDetail(String id);
}