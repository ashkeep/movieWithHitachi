package com.lk.movie.mapper;



import com.lk.movie.entity.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;
@Mapper
public interface UserDaoMapper {
    public void registUser(TUser tUser) throws Exception;
    public TUser userName(String regname) throws Exception;
    public TUser login(Map<String, Object> data) throws Exception;
    public int changeUname(Map<String, Object> data) throws Exception;
    public void changepwd(Map<String, Object> data) throws Exception;
    public void paybalance(Map<String, Object> data) throws Exception;
    public void changetxt(Map<String, Object> data) throws Exception;
}
