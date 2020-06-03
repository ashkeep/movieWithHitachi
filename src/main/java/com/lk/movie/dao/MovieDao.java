package com.lk.movie.dao;


//import com.cinema.batis.MovieDaoMapper;
//import com.cinema.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lk.movie.entity.TGrally;
import com.lk.movie.entity.TMovie;
import com.lk.movie.mapper.MovieDaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("movieDao")
public class MovieDao{

    @Autowired
    private MovieDaoMapper mapper;

    /**
     * 获取电影封面
     * @param mid
     * @return
     */
    public byte[] getMoviePic(String mid) throws Exception{
        return mapper.getMoviePic(mid).getCover();
    }
    /**
     * 获取banner的信息
     * @return
     * @throws Exception
     */
    public List<TGrally> getGrally() throws Exception{
        return mapper.getGrally();
    }

    /**
     * 获取对应类型的电影信息
     * @param state
     * @return 对应电影信息
     */
    public List<TMovie> getMovies(int state, String sorting) throws Exception{
        List<TMovie> tMovies = null;

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("state", state);
        data.put("sorting", sorting);
        if (sorting == "score desc" || sorting == "bgdata,mname") {
            tMovies=mapper.getMovies(data);
        }else {
            tMovies=mapper.getMovies2(data);
        }
        for (TMovie tMovie:tMovies) {
            Double sealnumm = Double.parseDouble(tMovie.getSealnum());
            if (sealnumm < 10000) {
                tMovie.setSealnum(sealnumm + "");
            }else if (sealnumm < 100000000) {
                sealnumm = Math.floor(sealnumm/100)/100;
                tMovie.setSealnum(sealnumm + "万");
            }else {
                sealnumm = Math.floor(sealnumm/1000000)/100;
                tMovie.setSealnum(sealnumm + "亿");
            }

            if (tMovie.getLength() != null) {
                String lg=tMovie.getLength();
                tMovie.setLength("/"+lg+"分钟");
            }else {
                tMovie.setLength("");
            }
            tMovie.setScorenumz((int)Math.floor(tMovie.getScorenum()));
            tMovie.setScorenumx((int)Math.ceil(tMovie.getScorenum()-Math.floor(tMovie.getScorenum()))*9);
        }

        return tMovies;
    }

}
