package com.lk.movie.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lk.movie.entity.Comments;
import com.lk.movie.entity.TUser;
import com.lk.movie.service.SelectBiz;
import com.lk.movie.service.UserBiz;
import com.lk.movie.util.Log;
import com.lk.movie.util.SpringFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/User")
public class UserAction {
    @RequestMapping("/Comment")
    public void Comment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String mid = request.getParameter("mid");
        Object obj = request.getSession().getAttribute("user");
        TUser userinfo = (TUser)obj;
        String uname = userinfo.getUname();
        Date today = new Date();
        String scoresString = request.getParameter("score");
        String essay = request.getParameter("comments");

        Comments comments = new Comments();
        comments.setUname(uname);
        comments.setEssay(essay);
        comments.setMid(mid);
        comments.setScore(Integer.parseInt(scoresString));
        comments.setTime(today);
        try {
            SelectBiz biz = (SelectBiz) SpringFactory.getContext().getBean("selectBiz");
            biz.addComment(comments);
        } catch (Exception e) {
            Log.logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    @RequestMapping("/UserVaild")
    protected void UserVaild(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String returnText = "1";
        String regname = request.getParameter("regname");
        if(regname != null && !regname.equals("")){
            UserBiz userBiz = (UserBiz)SpringFactory.getContext().getBean("userBiz");
            try {
                returnText = userBiz.userName(regname);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        out.println(returnText);

        out.flush();
        out.close();

    }
    @RequestMapping("/login")
    protected String login()  {
        return "/Login.jsp";
    }

    @RequestMapping("/Login")
    protected String Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String unameString = request.getParameter("uname");
        String pwdString = request.getParameter("upwd");
        UserBiz userBiz = (UserBiz)SpringFactory.getContext().getBean("userBiz");
        try {
            TUser user = userBiz.login(unameString, pwdString);
            if(user!=null){
                request.getSession().setAttribute("user",user);    //用户数据必须要放到session对象中
                return "redirect:/main.do";
            }else{
                request.setAttribute("msg", "用户名或密码输入错误");
                return "/Login.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errMsg", "网络异常，请和管理员联系");
            return "/error.jsp";
        }
    }
    @RequestMapping("/Regist")
    protected String Regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String regname = request.getParameter("regname");
        String regpwd = request.getParameter("regpwd");
        String regphone = request.getParameter("regphone");
        UserBiz userBiz = (UserBiz)SpringFactory.getContext().getBean("userBiz");
        try {
            TUser tUser = userBiz.registUser(regname,regpwd,regphone);
            request.getSession().setAttribute("user",tUser);    //用户数据必须要放到session对象中
            return "redirect:/main.do";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errMsg", "网络异常，请和管理员联系");
            return "/error.jsp";
        }
    }
    @RequestMapping("/Changeinfo")
    protected String Changeinfo(HttpServletRequest request) throws ServletException, IOException {
        String result= null;
        String pwd = request.getParameter("pwdtxt");
        String username = request.getParameter("user");
        UserBiz userBiz = (UserBiz)SpringFactory.getContext().getBean("userBiz");
        try {
            userBiz.changepwd(username, pwd);
        } catch (Exception e) {

        }
        request.getSession().invalidate();
        result = "redirect:login.do";
        return result;
    }
    @RequestMapping("/UserPic")
    protected String UserPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = null;
        String uname = request.getParameter("uname");
        if (uname != null) {
            UserBiz userBiz = (UserBiz)SpringFactory.getContext().getBean("userBiz");
            try {
                byte[] pic = userBiz.getUserPic(uname);
                if (pic != null) {
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(pic);
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                Log.logger.error(e.getMessage());
                request.setAttribute("msg", "出现了一些意料之外的错误,别联系管理员");
                result = "/error.jsp";
            }
        }else{
            request.setAttribute("msg", "参数出现了一些意料之外的错误,别联系管理员");
            result = "/error.jsp";
        }
        return result;

    }
    @RequestMapping("/Logout")
    protected String Logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        return "forward:/main.do";
    }
    @RequestMapping("/ChangeInformation")
    protected void ChangeInformation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String returnText = "1";
        String changename = request.getParameter("regname");
        String yuaname = request.getParameter("yuaname");
        if(changename != null && !changename.equals("")){
            UserBiz userBiz = (UserBiz)SpringFactory.getContext().getBean("userBiz");
            try {
                returnText = userBiz.changeUname(changename,yuaname);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        out.println(returnText);
        out.flush();
        out.close();
    }
    @RequestMapping("/ChangeBalance")
    protected void ChangeBalance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        TUser tUser = (TUser)request.getSession().getAttribute("user");
        String returnText = "1";
        double x = 0;
        String name = request.getParameter("name");
        String balances = request.getParameter("balance");
        Integer balance = Integer.valueOf(balances);
        UserBiz userBiz = (UserBiz)SpringFactory.getContext().getBean("userBiz");
        try {
            x = userBiz.paybalance(name, balance);
            tUser.setBalance(x);
            request.getSession().setAttribute("balance", x);
        } catch (Exception e) {
            returnText = "0";
            e.printStackTrace();
        }
        out.println(returnText);

        out.flush();
        out.close();
    }
    @RequestMapping("/ChangeTxt")
    protected void ChangeTxt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String returnText = "1";
        String name = request.getParameter("name");
        String txt = request.getParameter("txt");
        UserBiz userBiz = (UserBiz)SpringFactory.getContext().getBean("userBiz");
        try {
            userBiz.changetxt(name, txt);
        } catch (Exception e) {
            returnText = "0";
            e.printStackTrace();
        }
        out.println(returnText);

        out.flush();
        out.close();
    }

    @RequestMapping("/SelectUserPic")
    public void SelectUserPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uname =  request.getParameter("uname");
        ServletOutputStream out = response.getOutputStream();
        if(uname != null){
            SelectBiz biz = (SelectBiz)SpringFactory.getContext().getBean("selectBiz");
            try {
                byte[] pic = biz.getSelectUserPicSvl(uname);
                if(pic != null){
                    out.write(pic);
                }
            } catch (Exception e) {
                request.setAttribute("msg","网络异常，请和管理员联系");
            }
        }else{
            request.setAttribute("msg","参数丢失");
        }
        out.flush();
        out.close();
    }



}
