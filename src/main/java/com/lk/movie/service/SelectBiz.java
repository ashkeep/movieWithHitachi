package com.lk.movie.service;

//
//import com.cinema.dao.MovieDao;
//import com.cinema.dao.SelectDao;
//import com.cinema.dto.RondaInfo;
//import com.cinema.entity.*;
//import java.util.List;

import com.lk.movie.dao.MovieDao;
import com.lk.movie.dao.SelectDao;
import com.lk.movie.dto.RondaInfo;
import com.lk.movie.entity.Comments;
import com.lk.movie.entity.TCinema;
import com.lk.movie.entity.TDistrict;
import com.lk.movie.entity.TMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("selectBiz")
public class SelectBiz {
    @Autowired
    private SelectDao selectDao;
    @Autowired
    private MovieDao movieDao;

    public TMovie findMovie(String mid) throws Exception {
        return selectDao.findMovie(mid);
    }


    public List<Comments> findComments(String mid, String uname) throws Exception {
        return selectDao.findComments(mid);
    }


    public List<TDistrict> findDistrict() throws Exception {
        return selectDao.findDistrict();
    }

    public List<TCinema> findCinemas(String dno) throws Exception {
        return selectDao.findCinemas(dno);
    }

    /**
     * 获取某个影院下，与某个影片相关的所有未开始的场次
     * @param cid
     * @param mid
     * @return
     * @throws Exception
     */
    public List<RondaInfo> findRondas(String cid, String mid) throws Exception {
        return selectDao.findRondas(cid,mid);
    }

    public int commentState(String mid,String uname) throws Exception{
        return selectDao.commentState(mid,uname);
    }

    public byte[] getSelectMoviePic(String isbn) throws Exception {
        byte[] pic = null;
        if (isbn != null) {
            pic = movieDao.getMoviePic(isbn);
        } else {
            throw new Exception("参数isbn不能为空");
        }
        return pic;
    }
    public byte[] getSelectUserPicSvl(String uname) throws Exception {
        byte[] pic = null;
        if (uname != null) {
            pic = selectDao.getSelectUserPic(uname);
        } else {
            throw new Exception("参数不能为空");
        }

        return pic;
    }

    @Transactional(rollbackFor=Throwable.class)
    public void addComment(Comments comments) throws Exception {
        selectDao.addComment(comments);
    }
}
