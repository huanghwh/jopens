package com.kcmultimedia.sntp;


public class NativeSetTime
{

    public NativeSetTime()
    {
    }

    public native boolean setTime(double Time);


    private static String os_name;
    private static String os_arch;

    static 
    {
        os_arch = System.getProperty("os.name");
        os_name = System.getProperty("os.arch");
        if(os_name.equals("x86") && os_arch.startsWith("W"))
            System.loadLibrary("settime");
        else
        if(!os_name.equals("x86") && os_arch.startsWith("S"))
            System.loadLibrary("settime");
        else
        if(os_name.equals("x86") && os_arch.startsWith("S"))
            System.loadLibrary("settime86");
        else
        if(os_arch.startsWith("M"))
            System.loadLibrary("settime");
        else
        if(os_name.equals("x86") && os_arch.startsWith("L"))
            System.loadLibrary("lnxsettime");
    }
}
