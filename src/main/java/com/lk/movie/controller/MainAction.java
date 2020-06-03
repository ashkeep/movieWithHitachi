package com.lk.movie.controller;



import javax.servlet.http.HttpServletRequest;

import com.lk.movie.entity.TGrally;
import com.lk.movie.entity.TMovie;
import com.lk.movie.service.MovieBiz;
import com.lk.movie.util.Log;
import com.lk.movie.util.SpringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@CrossOrigin
public class MainAction {
    @Autowired
    MovieBiz movieBiz;

    @RequestMapping("/test")
    public String getMain(HttpServletRequest request) {
//        MovieBiz movieBiz = (MovieBiz) SpringFactory.getContext().getBean("movieBiz");
        String result = null;
        try {
            List<TMovie> symovies = movieBiz.getMovies(1,"score desc");             //正在上映
            request.getSession().setAttribute("symovies", symovies);
            List<TMovie> pfmovies = movieBiz.getMovies(1,"sealnum desc");           //票房排行榜
            request.setAttribute("pfmovies", pfmovies);
            List<TMovie> wsmovies = movieBiz.getMovies(2,"bgdata,mname");           //即将上映
            request.setAttribute("wsmovies", wsmovies);
            List<TMovie> xqmovies = movieBiz.getMovies(2,"wantosee desc");          //最受期待
            request.setAttribute("xqmovies", xqmovies);
            List<TGrally> tGrallies = movieBiz.getGrally();
            request.setAttribute("tGrallies", tGrallies);
            result = "test.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            Log.logger.error(e.getMessage());
            request.setAttribute("msg", "出现了一些意料之外的错误,请联系网管！");
            result = "/error.jsp";
        }
        return result;
    }

    @RequestMapping("/main")
    @ResponseBody
    protected Map service(HttpServletRequest request) {
//        MovieBiz movieBiz = (MovieBiz)SpringFactory.getContext().getBean("movieBiz");
        String result = null;
        Map map  = new HashMap();
        try {
            //正在上映
            List<TMovie> symovies = movieBiz.getMovies(1,"score desc");
            map.put("symovies",symovies);

            //票房排行榜
            List<TMovie> pfmovies = movieBiz.getMovies(1,"sealnum desc");
            map.put("pfmovies", pfmovies);

            //即将上映
            List<TMovie> wsmovies = movieBiz.getMovies(2,"bgdata,mname");
            map.put("wsmovies", wsmovies);

            //最受期待
            List<TMovie> xqmovies = movieBiz.getMovies(2,"wantosee desc");
            map.put("xqmovies", xqmovies);
            List<TGrally> tGrallies = movieBiz.getGrally();
            map.put("tGrallies", tGrallies);
        } catch (Exception e) {
            e.printStackTrace();
            Log.logger.error(e.getMessage());
            request.setAttribute("msg", "出现了一些意料之外的错误,请联系网管！");
            result = "/error.jsp";
        }
        return map;
    }

}

