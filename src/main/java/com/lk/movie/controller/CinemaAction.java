package com.lk.movie.controller;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lk.movie.entity.TCinema;
import com.lk.movie.entity.TDistrict;
import com.lk.movie.entity.TMovie;
import com.lk.movie.entity.TUser;
import com.lk.movie.service.SelectBiz;
import com.lk.movie.util.Log;
import com.lk.movie.util.SpringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("/Cinema")
public class CinemaAction {

    @Autowired
    SelectBiz selectBiz;

    @RequestMapping("/SelectCinema")
    public String SelectCinema(HttpServletRequest request) {
        String result;

        TUser tuser = (TUser) request.getSession().getAttribute("user");
        if(tuser == null){
            request.setAttribute("msg", "请登录之后购票");
            result = "forward:/User/login.do";
        }else{
            String mid = request.getParameter("mid");

            try {
                request.setCharacterEncoding("utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            TMovie movie = null;
            try {
                SelectBiz selectBiz = (SelectBiz) SpringFactory.getContext()
                        .getBean("selectBiz");
                movie = selectBiz.findMovie(mid);
                String sealnumString = movie.getSealnum();
                double sealnum = Integer.parseInt(sealnumString) / 100000000;
                int wang = Integer.parseInt(sealnumString) / 10000;
                String numString;
                if (sealnum > 0.1) {
                    numString = String.valueOf(sealnum) + "亿";
                } else {
                    numString = String.valueOf(wang) + "万";
                }
                movie.setSealnum(numString);
                movie.setMid(mid);
                int CommentState = selectBiz.commentState(mid, tuser.getUname());
                request.setAttribute("movie", movie);
                request.setAttribute("CommentState", CommentState);

                // 加载初始三联表
                List<TDistrict> tDistincts = selectBiz.findDistrict();
                List<TCinema> tCinemas = selectBiz.findCinemas("0");

                request.setAttribute("tDistincts", tDistincts);
                request.setAttribute("tCinemas", tCinemas);
                result = "/SelectCinema.jsp";
            } catch (Exception e) {
                Log.logger.error(e.getMessage());
                e.printStackTrace();
                result = "/error.jsp";
            }
        }

        return result;

    }

    @RequestMapping("/SelectCinemaList")
    @ResponseBody
    public List<TCinema> findCinemas(HttpServletRequest request) throws Exception {
        String dno = "0";
        return selectBiz.findCinemas(dno);
    }



}
