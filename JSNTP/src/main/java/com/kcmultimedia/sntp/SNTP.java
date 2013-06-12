// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   SNTP.java

package com.kcmultimedia.sntp;

import java.util.Calendar;
import java.util.TimeZone;

// Referenced classes of package com.kcmultimedia.sntp:
//            a, SNTPCorrection, b

public class SNTP extends UDPBase
{

    private static double getDoubleTime()
    {
        Calendar calendar = Calendar.getInstance();
        TimeZone timezone = calendar.getTimeZone();
        double d = (double)timezone.getRawOffset() / 3600000D;
        if(timezone.inDaylightTime(calendar.getTime()))
            d++;
        return d;
    }

    private static double getDoubleTime(Calendar calendar, double d)
    {
        int i = calendar.get(2) + 1;
        int j = calendar.get(1);
        int k = calendar.get(5);
        int l = calendar.get(11);
        int i1 = calendar.get(12);
        double d1 = calendar.get(13);
        d1 += (double)calendar.get(14) / 1000D;
        if(i <= 2)
        {
            j--;
            i += 12;
        }
        double d2 = j / 100;
        double d3 = (2D - d2) + (double)(int)(d2 / 4D);
        double d4 = (double)((int)(365.25D * (double)j) + (int)(30.600100000000001D * (double)(i + 1)) + k) + 1720994.5D + d3;
        d4 = d4 + ((double)l - d) / 24D + (double)i1 / 1440D + d1 / 86400D;
        return d4;
    }

    public static SNTPCorrection getCorrectionFrom(String host, int port)
        throws Exception
    {
        double d4 = getDoubleTime();
        byte abyte0[] = new byte[48];
        for(int j = 0; j < 48; j++)
            abyte0[j] = 0;

        abyte0[0] = 11;
        NTPPacket b1 = new NTPPacket();
        NTPPacket b2 = new NTPPacket();
        Calendar calendar = Calendar.getInstance();
        SNTP sntp = new SNTP(host, port);
        byte abyte1[] = sntp.getPacket(abyte0);
        Calendar calendar1 = Calendar.getInstance();
        double d = getDoubleTime(calendar, d4);
        double d1 = getDoubleTime(calendar1, d4);
        b1.setData(abyte1, NTPPacket.NTP_HEADER_LENGTH);
        double d3 = b1.getJulianDate();
        b2.setData(abyte1, 40);
        if((long)b2.getSeconds() == 0L)
        {
            throw new Exception("NTP Server is out of service.");
        } else
        {
            double d2 = b2.getJulianDate();
            double d5 = d;
            double d6 = d3;
            double d7 = d2;
            double d8 = d1;
            SNTPCorrection sntpcorrection = new SNTPCorrection();
            sntpcorrection.setCorrection((((d6 - d5) + (d7 - d8)) / 2D) * 86400D);
            sntpcorrection.setDelay((d8 - d5 - (d6 - d7)) * 86400D);
            return sntpcorrection;
        }
    }

    public static SNTPCorrection getCorrectionFrom(String host)
        throws Exception
    {
        return getCorrectionFrom(host, SNTP_PORT);
    }


    private SNTP(String s, int i)
        throws Exception
    {
        super(s, i, 52);
        System.out.println("SNTP API");
    }

    private static final int SNTP_PORT = 123;
}
