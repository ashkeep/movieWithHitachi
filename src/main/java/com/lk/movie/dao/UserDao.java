package com.lk.movie.dao;


//import com.cinema.batis.SelectDaoMapper;
//import com.cinema.batis.UserDaoMapper;
//import com.cinema.entity.TUser;

import java.util.HashMap;
import java.util.Map;

import com.lk.movie.entity.TUser;
import com.lk.movie.mapper.SelectDaoMapper;
import com.lk.movie.mapper.UserDaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao {
    @Autowired
    private UserDaoMapper userMapper;

    @Autowired
    private SelectDaoMapper selectDaoMapper;

    /**
     * 注册用户
     * @throws Exception
     */
    public TUser registUser(String regname, String regpwd, String regphone) throws Exception{
        TUser tUser = null;

        tUser = new TUser();
        tUser.setUname(regname);
        tUser.setPwd(regpwd);
        tUser.setTel(regphone);
        tUser.setRole(1);              //默认为普通用户
        userMapper.registUser(tUser);
        return tUser;
    }
    /**
     * 注册时检查用户名是否重复
     * @param regname
     * @return
     * @throws Exception
     */
    public String userName(String regname) throws Exception{
        String x = "1";
        if(userMapper.userName(regname)!=null) {
            x = "0";
        }
        return x;
    }
    /**
     * 登录监测
     * @param uname
     * @param pwd
     * @throws Exception
     */
    public TUser login(String uname,String pwd) throws Exception{
        TUser tuser = null;
        Map<String, Object> data = new HashMap();
        data.put("uname", uname);
        data.put("pwd", pwd);
        return userMapper.login(data);
    }


    /**
     * 获取电影封面
     * @param uname
     * @return
     */
    public byte[] getUserPic(String uname) throws Exception{
        return  selectDaoMapper.getSelectUserPic(uname).getUserPic();
    }

    /**
     * 更改用户名
     * @param changename
     * @return
     * @throws Exception
     */
    public String changeUname(String changename,String yuaname) throws Exception{
        String x = "1";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("changename", changename);
        data.put("yuaname", yuaname);
        if(userMapper.changeUname(data)!=0) {
            x="1";
        }

        return x;
    }
    /**
     * 修改密码
     * @param uname
     * @param pwd
     */
    public void changepwd(String uname,String pwd) throws Exception{
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("uname", uname);
        data.put("pwd", pwd);
        userMapper.changepwd(data);
    }
    /**
     * 获得用户余额
     * @param uname
     * @return
     * @throws Exception
     */
    public double getbalance(String uname) throws Exception{
        double balance = 0;

        balance = userMapper.userName(uname).getBalance();
        return balance;
    }
    /**
     * 充值
     * @return
     * @throws Exception
     */
    public double paybalance(String name,Integer balance) throws Exception{
        double balancenew = 0;
        try {
            double balanceold = getbalance(name);
            balancenew = balanceold + balance;
            Map<String, Object> data = new HashMap();
            data.put("uname", name);
            data.put("balance", balancenew);
            userMapper.paybalance(data);
        } catch (Exception e) {
            throw e;
        }
        return balancenew;
    }
    /**
     * 修改用户的个性签名
     * @return
     * @throws Exception
     */
    public void changetxt(String name,String txt) throws Exception{
        Map<String, Object> data = new HashMap();
        data.put("uname", name);
        data.put("txt", txt);
        userMapper.changetxt(data);
//		String sql = "UPDATE tuser set usertxt = ?  where uname = ?";
//		this.openConnection();
//		PreparedStatement ps = this.conn.prepareStatement(sql);
//		ps.setString(1, txt);
//		ps.setString(2, name);
//		ps.executeQuery();
//		ps.close();
    }
    /**
     * 购票成功并扣钱
     * @param uname
     * @param pirce
     * @throws Exception
     */
    public double paymoney(String uname,double pirce) throws Exception {
        double balanceold = getbalance(uname);
        double balancenew = balanceold - pirce;
        Map<String, Object> data = new HashMap();
        data.put("uname", uname);
        data.put("balance", balancenew);
        userMapper.paybalance(data);
//		String sql = "UPDATE tuser set balance = ?  where uname = ?";
//		this.openConnection();
//		PreparedStatement ps = this.conn.prepareStatement(sql);
//		ps.setDouble(1, balancenew);
//		ps.setString(2, uname);
//		ps.executeQuery();
//		ps.close();
//
        return balancenew;
    }
}














