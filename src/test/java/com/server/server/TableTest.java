package com.server.server;

import com.server.server.Util.Table;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TableTest {
    @Test
    public void reflectionTest() throws NoSuchMethodException{
        Class tableklass = Table.class;
        Method toVal = tableklass.getMethod("getVal");
        List<Object> fieldList = Arrays.asList(tableklass.getEnumConstants());
        try{
            for(Object obj:fieldList){
                System.out.println(toVal.invoke(obj));
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }
}
