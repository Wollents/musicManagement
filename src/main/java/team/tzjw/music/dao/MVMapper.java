package team.tzjw.music.dao;

import org.apache.ibatis.annotations.Param;
import team.tzjw.music.pojo.MV;

import java.util.List;

public interface MVMapper {
    int deleteByPrimaryKey(String id);

    int insert(MV record);

    MV selectByPrimaryKey(String id);

    List<MV> selectAll(@Param("page") Integer page, @Param("limit") Integer limit);

    int updateByPrimaryKey(MV record);

    int updateStatus(@Param("status") Integer status, @Param("id") String id);

    MV detail(String id);

    Long countMv();

    MV editDetail(String id);

    int mvUpdate(@Param("id") String id, @Param("userid") String userid, @Param("mvdesc") String mvdesc, @Param("playnum") Long playnum, @Param("likecounts") Long likecounts);

    int getNums();
}