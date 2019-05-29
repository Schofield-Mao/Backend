package com.server.server.Util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
@Service
public class ObjMapper {

    public static Object map(Object source, Class<?> kclass){
        Object target = null;
        try{
            target = kclass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source,target);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return target;
        }
    }
}
