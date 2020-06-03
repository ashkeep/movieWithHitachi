package com.lk.movie.mapper;


import java.util.List;

import com.lk.movie.dto.RondaInfo;
import com.lk.movie.dto.RondaSeatInfo;
import com.lk.movie.entity.ToutTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
@Mapper
public interface RondaMapper {

    /**
     * 根据场次号，获得该场次下座位信息
     * @param rid
     * @return
     * @throws Exception
     */
    public List<RondaSeatInfo>  getRondaSeatList(int rid) throws Exception;

    /**
     * 根据场次号，获得该场次电影等相关信息
     * @param rid
     * @return
     * @throws Exception
     */
    public RondaInfo getRondaInfo(int rid) throws Exception;

    /**
     * 更新指定场次下座位的状态为已选定（3 ）
     * @param rid      场次号
     * @param seatSZ   选定的座位序号
     * @throws Exception
     */
    public void orderTicket(@Param("rid")int rid,@Param("seatSZ")String[] seatSZ) throws Exception;

    /**
     * 输入的座位，如果库中状态不为1，则为占座冲突
     * @param rid
     * @param seatSZ  选定的座位
     * @return 冲突座位编号
     * @throws Exception
     */
    public List<String> checkInflictSeat(@Param("rid")int rid,@Param("seatSZ")String[] seatSZ) throws Exception;

    /**
     * 使用定时器，定期执行这个操作
     *   查找所有状态值为3的场次下的座位，如果当前时间减去选座时间 ，大于间隔时间spaceTime，则状态恢复为1（可选态）
     *   3是选定未付款的状态，2是已付款状态
     * @param spaceTime  表示的间隔时间,单位秒
     * @throws Exception
     */
    public void recoverSeatState(int spaceTime) throws Exception;

    /**
     * 添加付款记录
     *   把锁定座位(3)的状态修改为“已付款(2)”
     * @param rid
     * @param seatSZ
     * @throws Exception
     */
    public int payTicket(@Param("rid")int rid,@Param("seatSZ")String[] seatSZ) throws Exception;


    /**
     * 添加付款记录
     * @param outTicket
     * @throws Exception
     */
    public void addPay(ToutTicket outTicket) throws Exception;

}
