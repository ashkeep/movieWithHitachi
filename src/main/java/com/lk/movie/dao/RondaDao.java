package com.lk.movie.dao;


import java.util.Date;
import java.util.List;

import com.lk.movie.dto.RondaInfo;
import com.lk.movie.dto.RondaSeatInfo;
import com.lk.movie.entity.ToutTicket;
import com.lk.movie.mapper.RondaMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("rondaDao")
public class RondaDao {



    @Autowired
    private RondaMapper rondaMapper;



    /**
     * 根据场次id，读取该场次下的选座信息
     * @param rid
     * @return
     */
    public List<RondaSeatInfo>  getRondaSeatList(int rid) throws Exception{
        return rondaMapper.getRondaSeatList(rid);
    }

    /**
     * 根据场次id，读取该场次的详细信息
     * @param rid
     * @return
     * @throws Exception
     */
    public RondaInfo getRondaInfo(int rid) throws Exception{
        return rondaMapper.getRondaInfo(rid);
    }

    /**
     * 更新指定场次下座位的状态为已选定（3 ）
     * @param rid      场次号
     * @param seatSZ   选定的座位序号
     * @throws Exception
     */
    public void orderTicket(int rid,String[] seatSZ) throws Exception{
        rondaMapper.orderTicket(rid, seatSZ);
    }

    /**
     * 输入的座位，如果库中状态不为1，则为占座冲突
     * @param rid
     * @param seatSZ  选定的座位
     * @return 冲突座位编号
     * @throws Exception
     */
    public List<String> checkInflictSeat(@Param("rid")int rid,@Param("seatSZ")String[] seatSZ) throws Exception{
        return rondaMapper.checkInflictSeat(rid, seatSZ);
    }

    /**
     * 使用定时器，定期执行这个操作
     *   查找所有状态值为3的场次下的座位，如果当前时间减去选座时间 ，大于间隔时间spaceTime，则状态恢复为1（可选态）
     *   3是选定未付款的状态，2是已付款状态
     * @param spaceTime  表示的间隔时间,单位秒
     * @throws Exception
     */
    public void recoverSeatState(int spaceTime) throws Exception{
        rondaMapper.recoverSeatState(spaceTime);
    }

    /**
     * 添加付款记录
     *   把锁定座位(3)的状态修改为“已付款(2)”
     * @param rid
     * @param seatSZ
     * @throws Exception
     */
    public int payTicket(String uname,int rid,String[] seatSZ) throws Exception{
        int iRet = rondaMapper.payTicket(rid, seatSZ);
        if(iRet>0){
            for(int i=0;i<seatSZ.length;i++){
                ToutTicket outTicket = new ToutTicket();
                outTicket.setUname(uname);
                outTicket.setPayTime(new Date());
                outTicket.setRealpay(60);
                outTicket.setRid(rid);
                outTicket.setSeatno(seatSZ[i]);
                outTicket.setState(0);                   //默认为0，未出票
                rondaMapper.addPay(outTicket);
            }
        }

        return iRet;
    }


}
