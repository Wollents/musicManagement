package team.tzjw.music.dao;

import org.apache.ibatis.annotations.Param;
import team.tzjw.music.pojo.Type;

import java.util.List;

public interface TypeMapper {
    int deleteByPrimaryKey(String tid);

    int insert(Type record);

    Type selectByPrimaryKey(String tid);

    List<Type> selectAll(@Param("page") Integer page, @Param("limit") Integer limit);

    int updateByPrimaryKey(Type record);

    int getNums();

    List<String> getSongtype();

    List<Integer> getSongtypeNums();
}