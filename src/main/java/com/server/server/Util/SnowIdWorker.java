package com.server.server.Util;

import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SnowIdWorker implements IdWorker{

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 2;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 2;//数据中心占用的位数
    private final static long TABLE_BIT = 6;//table bit
    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    private final static long MAX_TABLE = -1L ^ (-1L << TABLE_BIT);
    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    private final static long TABLE_LEFT = TIMESTMP_LEFT + TABLE_BIT;


    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    //private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳
    private ConcurrentHashMap<Integer,Long> sequenceMap;

    private SnowIdWorker() {
        long datacenterId = 1;
        long machineId = 2;
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
        this.sequenceMap = new ConcurrentHashMap();

        try{
            Class tableklass = Table.class;
            Method toVal = tableklass.getMethod("getVal");
            List<Object> fieldList = Arrays.asList(tableklass.getEnumConstants());
            for(Object obj:fieldList){
                //System.out.println(toVal.invoke(obj));
                sequenceMap.put((int)toVal.invoke(obj),0l);
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    /**
     * 产生下一个ID
     *
     * @return
     */

    public synchronized long nextId(Table table) {

        long sequence = sequenceMap.get(table.getVal());

        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        sequenceMap.put(table.getVal(),sequence);
        lastStmp = currStmp;
        return  table.getVal()<<TABLE_LEFT
                |(currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

}
