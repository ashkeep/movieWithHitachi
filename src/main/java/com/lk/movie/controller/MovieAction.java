package com.lk.movie.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lk.movie.dto.RondaInfo;
import com.lk.movie.entity.Comments;
import com.lk.movie.entity.TMovie;
import com.lk.movie.entity.TUser;
import com.lk.movie.service.MovieBiz;
import com.lk.movie.service.SelectBiz;
import com.lk.movie.util.Log;
import com.lk.movie.util.SpringFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author lk56054
 */
@Controller
@RequestMapping("/Movie")
public class MovieAction {

    @RequestMapping("/Details")
    public String Details(HttpServletRequest request) {
        String result = null;
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        TMovie movie = null;
        SelectBiz selectBiz = (SelectBiz) SpringFactory.getContext().getBean(
                "selectBiz");
        TUser tUser = (TUser) request.getSession().getAttribute("user");
        String uname;
        if (tUser == null) {
            uname = "游客";
        } else {
            uname = tUser.getUname();
        }
        String mid = request.getParameter("mid");

        try {
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
            List<Comments> comments = selectBiz.findComments(mid, uname);
            int CommentState = 0;
            if (uname == "游客") {
                CommentState = 3;
            } else {
                CommentState = selectBiz.commentState(mid, uname);
            }
            request.setAttribute("comments", comments);
            request.setAttribute("movie", movie);
            request.setAttribute("CommentState", CommentState);

            result = "/Details.jsp";
        } catch (Exception e) {
            Log.logger.error(e.getMessage());
            e.printStackTrace();
            result = "/error.jsp";
        }
        return result;
    }

    @RequestMapping("/Moviepic")
    protected String Moviepic(HttpServletRequest request,
                              HttpServletResponse response) {
        String result = null;
        String midString = request.getParameter("mid");
        if (midString != null) {
            MovieBiz movieBiz = (MovieBiz) SpringFactory.getContext().getBean(
                    "movieBiz");
            try {
                byte[] pic = movieBiz.getMoviePic(midString);
                if (pic != null) {
                    ServletOutputStream outputStream = response
                            .getOutputStream();
                    outputStream.write(pic);
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                Log.logger.error(e.getMessage());
                request.setAttribute("msg", "出现了一些意料之外的错误,别联系管理员");
                result = "/error.jsp";
            }
        } else {
            request.setAttribute("msg", "参数出现了一些意料之外的错误,别联系管理员");
            result = "/error.jsp";
        }
        return result;

    }

    @RequestMapping("/SelectMoviepic")
    public void SelectMoviepic(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {

        String isbn = request.getParameter("isbn");
        ServletOutputStream out = response.getOutputStream();
        if (isbn != null) {
            SelectBiz selectBiz = (SelectBiz) SpringFactory.getContext()
                    .getBean("selectBiz");
            try {
                byte[] pic = selectBiz.getSelectMoviePic(isbn);
                if (pic != null) {
                    out.write(pic);
                }
            } catch (Exception e) {
                request.setAttribute("msg", "网络异常，请和管理员联系");
            }
        } else {
            request.setAttribute("msg", "参数丢失");
        }
        out.flush();
        out.close();
    }

    @RequestMapping("/SelectMoiveList")
    @ResponseBody
    public List<RondaInfo> SelectMoiveList(String cid, String mid)
            throws Exception {

        SelectBiz selectBiz = (SelectBiz) SpringFactory.getContext().getBean(
                "selectBiz");

        return selectBiz.findRondas(cid, mid);

    }

    @RequestMapping("/Comment")
    public void Comment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String mid = request.getParameter("mid");
        Object obj = request.getSession().getAttribute("user");
        TUser userinfo = (TUser) obj;
        String uname = userinfo.getUname();
        Date today = new Date();
        String scoresString = request.getParameter("score");
        String essay = request.getParameter("comments");
        Comments comments = new Comments();
        comments.setUname(uname);
        comments.setEssay(essay);
        comments.setMid(mid);
        if (scoresString == null || scoresString.trim().equals("")) {
            scoresString = "4"; // 默认为4分
        }
        comments.setScore(Integer.parseInt(scoresString));
        comments.setTime(today);
        try {
            SelectBiz selectBiz = (SelectBiz) SpringFactory.getContext()
                    .getBean("selectBiz");
            selectBiz.addComment(comments);
        } catch (Exception e) {
            Log.logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @RequestMapping("/Latest")
    public String latest(Model model) {
        String result=null;
        MovieBiz movieBiz = (MovieBiz) SpringFactory.getContext().getBean("movieBiz");
        List<TMovie> symovies;
        try {
            symovies = movieBiz.getMovies(1,"score desc");
            model.addAttribute("symovies", symovies);
            result ="/latest.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    @RequestMapping("/Soar")
    public String Soar(HttpServletRequest request) {
        String result = null;
        MovieBiz movieBiz = (MovieBiz) SpringFactory.getContext().getBean(
                "movieBiz");
        List<TMovie> pfmovies;
        try {
            pfmovies = movieBiz.getMovies(1, "sealnum desc");
            request.setAttribute("pfmovies", pfmovies);
            result = "/Soar.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            result = "/error.jsp";
        }
        return result;

    }

    @RequestMapping("/Upcoming")
    public String Upcoming(HttpServletRequest request) {
        String result = null;
        MovieBiz movieBiz = (MovieBiz) SpringFactory.getContext().getBean(
                "movieBiz");
        try {
            List<TMovie> wsmovies = movieBiz.getMovies(2, "bgdata,mname");
            request.setAttribute("wsmovies", wsmovies);
            result = "/Upcoming.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            result = "/error.jsp";
        }
        return result;

    }

    @RequestMapping("/Expected")
    public String Expected(HttpServletRequest request) {
        String result = null;
        MovieBiz movieBiz = (MovieBiz) SpringFactory.getContext().getBean(
                "movieBiz");
        try {
            List<TMovie> xqmovies = movieBiz.getMovies(2, "wantosee desc");
            request.setAttribute("xqmovies", xqmovies);
            result = "/Expected.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            result = "/error.jsp";
        }

        return result;

    }

}
