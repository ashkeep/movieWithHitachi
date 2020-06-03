package com.lk.movie.service;


import java.util.List;

import com.lk.movie.dao.RondaDao;
import com.lk.movie.dto.RondaInfo;
import com.lk.movie.dto.RondaSeatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("rondaBiz")
public class RondaBiz {

    @Autowired
    private RondaDao rondaDao;

    /**
     * 根据场次id，读取该场次下的选座信息
     * @param rid
     * @return
     */
    public List<RondaSeatInfo>  getRondaSeatList(int rid) throws Exception{
        return rondaDao.getRondaSeatList(rid);
    }

    /**
     * 根据场次id，读取该场次的详细信息
     * @param rid
     * @return
     * @throws Exception
     */
    public RondaInfo getRondaInfo(int rid) throws Exception{
        return rondaDao.getRondaInfo(rid);
    }

    /**
     * 更新指定场次下座位的状态为已选定（3 ）
     * 注意： 在并发环境下，你选定的座位可能被人提前下单，所以当前提交可能失败
     * @param rid      场次号
     * @param seatSZ   选定的座位序号
     * @throws Exception
     */
    public List<String > orderTicket(int rid,String[] seatSZ) throws Exception{

        List<String > seatList = rondaDao.checkInflictSeat(rid, seatSZ);
        if(seatList != null && seatList.size()==0){
            rondaDao.orderTicket(rid, seatSZ);
        }

        return seatList;
    }

    /**
     * 添加付款记录
     *   把锁定座位(3)的状态修改为“已付款(2)”
     * @param rid
     * @param seatSZ
     * @throws Exception
     * @return int  0表示更新失败，付款超时      1表示付款成功
     */
    @Transactional(rollbackFor=Throwable.class)
    public int payTicket(String uname,int rid,String[] seatSZ) throws Exception{
        return rondaDao.payTicket(uname, rid, seatSZ);
    }


    /**
     * 使用定时器，定期执行这个操作
     *   查找所有状态值为3的场次下的座位，如果当前时间减去选座时间 ，大于间隔时间spaceTime，则状态恢复为1（可选态）
     *   3是选定未付款的状态，2是已付款状态
     * @param spaceTime  表示的间隔时间,单位秒
     * @throws Exception
     */
    public void recoverSeatState(int spaceTime) throws Exception{
        rondaDao.recoverSeatState(spaceTime);
    }

}
