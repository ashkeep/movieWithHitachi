package com.lk.movie.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lk.movie.dto.RondaInfo;
import com.lk.movie.dto.RondaSeatInfo;
import com.lk.movie.entity.TUser;
import com.lk.movie.service.RondaBiz;
import com.lk.movie.util.SpringFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth/ronda")
public class RondaAction {

    @RequestMapping("/selectSeat")
    public void SelectSeat(int aid, HttpSession session,
                           HttpServletResponse response) throws Exception {
        RondaBiz rondaBiz = (RondaBiz) SpringFactory.getContext().getBean(
                "rondaBiz");
        RondaInfo rondaInfo = rondaBiz.getRondaInfo(aid);
        List<RondaSeatInfo> rsList = rondaBiz.getRondaSeatList(aid);
        JSONArray json = JSONArray.fromObject(rsList);
        JSONObject objData = new JSONObject();
        objData.put("states", json);
        objData.put("aid", aid);
        objData.put("mid", rondaInfo.getMid());
        objData.put("movie_name", rondaInfo.getMname());
        objData.put("movie_time", rondaInfo.getLength());
        objData.put("cinema", rondaInfo.getCname());
        objData.put("price_sale",rondaInfo.getPrice());
        objData.put("movie_optime", rondaInfo.getBegintime());
        objData.put("movie_language", rondaInfo.getLanguage());
        objData.put("hno", rondaInfo.getHno());
        objData.put("screens_name",rondaInfo.getHno());
        objData.put("row", rondaInfo.getAllrow());
        objData.put("col", rondaInfo.getAllcol());
        TUser tuser = (TUser) session.getAttribute("user");
        if(tuser != null){
            objData.put("user_tel", tuser.getTel());
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(objData.toString());
        System.out.println("*************" + objData.toString());

    }

    /**
     * 选定座位，然后进入付款确认页
     * 在并发环境下，你选定的座位可能被人提前下单，所以当前提交可能失败
     * @param aid
     * @param seats
     * @param model
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/order")
    @ResponseBody
    protected String Order(int aid,String seats,Model model,HttpSession session) throws Exception{

        JSONObject objData = new JSONObject();
        String[] seatSZ = seats.split(",");

        RondaBiz biz = (RondaBiz)SpringFactory.getContext().getBean("rondaBiz");
        List<String> seatList = biz.orderTicket(aid, seatSZ);
        if(seatList != null && seatList.size()>0){
            objData.put("ret",0);                                        //冲突
            String allseat = "";
            for(String seatno : seatList){
                String[] sz = seatno.split("-");
                allseat = allseat  + sz[sz.length-1] + ",";
            }
            objData.put("seats", allseat);
        }else{
            objData.put("ret",1);                                       //正常，无冲突
        }


        return objData.toString();
    }

    @RequestMapping(value="/pay",method=RequestMethod.GET)
    public String pay(int aid,String seats, Model model) throws Exception{
        RondaBiz biz = (RondaBiz)SpringFactory.getContext().getBean("rondaBiz");
        RondaInfo rondaInfo = biz.getRondaInfo(aid);
        model.addAttribute("ronda", rondaInfo);
        String[] seatSZ = seats.split(",");
        model.addAttribute("ticketSize", seatSZ.length);
        return "/Order.jsp";
    }

    @RequestMapping(value="/pay",method=RequestMethod.POST)
    @ResponseBody
    public String payConfirm(int aid, String seats,Model model,HttpSession session){
        String strRet;

        RondaBiz biz = (RondaBiz)SpringFactory.getContext().getBean("rondaBiz");
        TUser tuser = (TUser) session.getAttribute("user");
        if(tuser != null){
            try {
                String[] seatSZ = seats.split(",");
                int iRet = biz.payTicket(tuser.getUname(), aid, seatSZ);
                if(iRet >= 1 ){
                    strRet = "1";	  //付款成功
                }else{
                    strRet = "2";	  //付款失败，检查是未及时付款？还是重复支付了？
                }

            } catch (Exception e) {
                e.printStackTrace();
                strRet = "0";        //系统异常，请检查
            }
        }else{
            strRet = "0";
        }

        return strRet;
    }

    @RequestMapping("/seat/select")
    public String selectSeat(int aid) {
        return "/SelectSeat.jsp";
    }


}
