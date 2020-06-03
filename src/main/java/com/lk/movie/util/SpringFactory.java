package com.lk.movie.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFactory {
    private static ApplicationContext context;

    static{
        context = new ClassPathXmlApplicationContext("beans.xml");
    }

    public static ApplicationContext getContext(){
        return context;
    }

}
