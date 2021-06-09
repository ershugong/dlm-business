package com.macro.mall.tiny.modules.dlm.util;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class CpuUsage{
    private static final int CPUTIME = 500;

    private static final int PERCENT = 100;

    private static final int FAULTLENGTH = 10;

    //获取内存使用率
    public static String getMemory()
    {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存+虚拟内存
        long totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        Double compare = (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
        String str = "内存已使用:" + compare.intValue() + "%";
        return str;
    }
}
