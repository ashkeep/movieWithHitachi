package com.lk.movie.mapper;


import com.lk.movie.dto.RondaInfo;
import com.lk.movie.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
@Mapper
public interface SelectDaoMapper {
    public TMovie findMovie(String mid) throws Exception;

    public List<Comments> findComments(String mid) throws Exception;

    public List<TDistrict> findDistrict() throws Exception;

    public List<TCinema> findCinemas(String dno) throws Exception;

    public List<TCinema> findAllCinemas() throws Exception;


    /**
     * 获取某个影院下，与某个影片相关的所有未开始的场次
     * @param cid
     * @param mid
     * @return
     * @throws Exception
     */
    public List<RondaInfo> findRondas(@Param("cid")String cid,
                                      @Param("mid")String mid, @Param("now")Date now) throws Exception;



    public List<Comments> commentState(Map<String, Object> data) throws Exception;

    public TUser getSelectUserPic(String uname) throws Exception;

    public void addComment(Comments comments) throws Exception;

}
