package com.lk.movie.mapper;



import com.lk.movie.entity.TGrally;
import com.lk.movie.entity.TMovie;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Mapper
public interface MovieDaoMapper {
    public List<TMovie> getMovies(Map<String, Object> data) throws Exception;
    public List<TMovie> getMovies2(Map<String, Object> data) throws Exception;
    public List<TGrally> getGrally() throws Exception;
    public TMovie getMoviePic(String mid) throws Exception;
}
