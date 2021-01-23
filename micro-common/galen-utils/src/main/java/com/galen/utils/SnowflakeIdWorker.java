package com.galen.utils;

/**
 * @Author: Galen
 * @Date: 2019/2/28-10:22
 * @Description: 雪花算法
 **/
public abstract class SnowflakeIdWorker {
// ==============================Fields===========================================
    /**
     * 开始时间截 (2015-01-01)
     */
    protected final long twepoch = 1420041600000L;

    /**
     * 序列在id中占的位数
     */
    protected final long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    protected final long workerIdShift = sequenceBits;

    /**
     * 机器id所占的位数
     */
    protected final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    protected final long datacenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    protected final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    protected final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 数据标识id向左移17位(12+5)
     */
    protected final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    protected final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;


    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    protected final long sequenceMask = -1L ^ (-1L << sequenceBits);


    /**
     * 毫秒内序列(0~4095)
     */
    protected long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    protected long lastTimestamp = -1L;


    // ==============================Methods==========================================
    protected long getTimeStamp() {
        long timestamp = timeGen();
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;
        return timestamp;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }


}
