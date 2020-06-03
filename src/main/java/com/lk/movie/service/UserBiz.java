package com.lk.movie.service;


import com.lk.movie.dao.UserDao;
import com.lk.movie.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.cinema.dao.UserDao;
//import com.cinema.entity.TUser;

@Service("userBiz")
public class UserBiz {
    @Autowired
    private UserDao userDao;

    /**
     * 注册用户
     * @throws Exception
     */
    public TUser registUser(String regname, String regpwd, String regphone) throws Exception{
        TUser tuser = null;

        if (regname != null && regpwd != null && regphone != null && !regname.equals("") && !regpwd.equals("") && !regphone.equals("")) {
            tuser = userDao.registUser(regname, regpwd, regphone);
        } else {
            throw new Exception("入参错误");
        }

        return tuser;
    }
    /**
     * 注册时检查用户名是否重复
     * @param regname
     * @return
     * @throws Exception
     */
    public String userName(String regname) throws Exception{
        String x = "1";
        if (regname != null && !regname.equals("")) {
            x = userDao.userName(regname);
        }else{
            throw new Exception("入参错误，请检查");
        }

        return x;
    }
    /**
     * 登录
     * @param uname
     * @param pwd
     * @throws Exception
     */
    public TUser login(String uname,String pwd) throws Exception{

        TUser tuser = null;
        if(uname != null && pwd != null && !uname.equals("") && !pwd.equals("")){
            tuser = userDao.login(uname, pwd);
        }else{
            throw new Exception("入参错误，请检查");
        }

        return tuser;
    }

    /**
     * 获取用户头像
     * @param
     * @return
     * @throws Exception
     */
    public byte[] getUserPic(String uname) throws Exception {
        byte[] pic = null;

        if(uname != null){
            pic = userDao.getUserPic(uname);
        }else{
            throw new Exception("参数mid不能为空");
        }

        return pic;
    }

    /**
     * 更改用户名
     * @param changename
     * @return
     * @throws Exception
     */
    public String changeUname(String changename,String yuaname) throws Exception{
        String x = "1";

        if(changename != null && !changename.equals("")){
            x = userDao.changeUname(changename,yuaname);
        }else{
            throw new Exception("入参错误，请检查");
        }

        return x;
    }
    /**
     * 修改密码
     * @param uname
     * @param pwd
     */
    public void changepwd(String uname,String pwd) throws Exception{
        if(pwd != null && !pwd.equals("")){
            userDao.changepwd(uname,pwd);
        }else{
            throw new Exception("入参错误，请检查");
        }
    }
    /**
     * 充值
     * @return
     * @throws Exception
     */
    public double paybalance(String name,Integer balance) throws Exception{
        double x = 0;
        if (balance != null) {
            x = userDao.paybalance(name,balance);
        }

        return x;
    }
    /**
     * 修改用户的个性签名
     * @return
     * @throws Exception
     */
    public String changetxt(String name,String txt) throws Exception{
        String x = "1";

        userDao.changetxt(name, txt);

        return x;
    }
    /**
     * 购票成功并扣钱
     * @param uname
     * @param pirce
     */
    public double paymoney(String uname,double pirce) throws Exception {
        double x = 0;

        if (uname != null && !uname.equals("") && pirce > 0) {
            x = userDao.paymoney(uname, pirce);
        }else{
            throw new Exception("入参错误");
        }

        return x;
    }
}

