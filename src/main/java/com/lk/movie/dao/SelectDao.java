package com.lk.movie.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lk.movie.dto.RondaInfo;
import com.lk.movie.entity.Comments;
import com.lk.movie.entity.TCinema;
import com.lk.movie.entity.TDistrict;
import com.lk.movie.entity.TMovie;
import com.lk.movie.mapper.SelectDaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lk56054
 */
@Repository("selectDao")
public class SelectDao {
    @Autowired
    private SelectDaoMapper selectDaoMapper;

    public TMovie findMovie(String mid) throws Exception {
        return  selectDaoMapper.findMovie(mid);
    }

    public List<Comments> findComments(String mid) throws Exception {
        return  selectDaoMapper.findComments(mid);
    }

    //DistinctFind CinemasFind
    public List<TDistrict> findDistrict() throws Exception {
        return  selectDaoMapper.findDistrict();
    }

    public List<TCinema> findCinemas(String dno) throws Exception {
        List<TCinema> tCinemas = null;

        if (dno.equals("0")) {
            tCinemas = selectDaoMapper.findAllCinemas();         	//提取所有
        }else {
            tCinemas = selectDaoMapper.findCinemas(dno);
        }

        return tCinemas;
    }




    /**
     * 获取某个影院下，与某个影片相关的所有未开始的场次
     * @param cid
     * @param mid
     * @return
     * @throws Exception
     */
    public List<RondaInfo> findRondas(String cid, String mid) throws Exception {

        return selectDaoMapper.findRondas(cid,mid,new Date());
    }



    public int commentState(String mid,String uname) throws Exception {
        int i = 0;
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("uname", uname);
        data.put("mid", mid);
        if(!selectDaoMapper.commentState(data).isEmpty()) {
            i=1;
        }
        return i;
    }





    public byte[] getSelectUserPic(String uname) throws Exception{
        return  selectDaoMapper.getSelectUserPic(uname).getUserPic();
    }

    public void addComment(Comments comments) throws Exception {
        selectDaoMapper.addComment(comments);
//		String sql = "insert into COMMENTS values(?,?,?,?,?)";
//		this.openConnection();
//
//		PreparedStatement ps = this.conn.prepareStatement(sql);
//		ps.setString(1, comments.getUname());
//		ps.setString(2, comments.getMid());
//		ps.setDouble(3, comments.getScore());
//		ps.setString(4, comments.getEssay());
//		ps.setDate(5, new java.sql.Date(comments.getTimes().getTime()));////????????????????????????????????????????????????
//		ps.executeUpdate();
//
//		ps.close();
    }
}
