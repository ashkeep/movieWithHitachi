package com.lk.movie.service;


import com.lk.movie.dao.MovieDao;
import com.lk.movie.entity.TGrally;
import com.lk.movie.entity.TMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("movieBiz")
public class MovieBiz {
    @Autowired
    private MovieDao movieDao;


    /**
     * 获取电影封面
     * @param mid
     * @return
     * @throws Exception
     */
    public byte[] getMoviePic(String mid) throws Exception {
        byte[] moviePic = null;

        if(mid != null){
            moviePic = movieDao.getMoviePic(mid);
        }else{
            throw new Exception("参数mid不能为空");
        }

        return moviePic;
    }

    /**
     * 获取首页banner的信息
     * @return
     * @throws Exception
     */
    public List<TGrally> getGrally() throws Exception{
        return movieDao.getGrally();
    }

    /**
     * 获取对应状态的电影信息
     * @param state   1正在上映   2即将上映  3已上映
     * @param sorting 结果集的排序条件
     * @return 对应电影信息
     * @throws Exception
     */
    public List<TMovie> getMovies(int state, String sorting) throws Exception{
        return movieDao.getMovies(state,sorting);
    }

}
