package com.kcmultimedia.sntp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.TimeZone;

// Referenced classes of package com.kcmultimedia.sntp:
//            SNTPCorrection, b

public class SNTPProxy{

  private double getDoubleTime()//hour 8
  {
      Calendar calendar = Calendar.getInstance();
      TimeZone timezone = calendar.getTimeZone();
      double d = (double)timezone.getRawOffset() / 3600000D;
      if(timezone.inDaylightTime(calendar.getTime()))
          d++;
      return d;
  }

  private double getDoubleTime(Calendar calendar, double d)//hour
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

  public SNTPCorrection getCorrection()
      throws Exception{
    Socket socket = null;
    double d4 = getDoubleTime();
    byte abyte0[] = new byte[48];
    for(int i = 0; i < 48; i++)
      abyte0[i] = 0;

    abyte0[0] = 11;
    NTPPacket b1 = new NTPPacket();
    NTPPacket b2 = new NTPPacket();
    double d;
    double d1;
    double d2;
    double d3;
    try{
      socket = new Socket(host, port);
      OutputStream outputstream = socket.getOutputStream();
      InputStream inputstream = socket.getInputStream();
      outputstream.write(abyte0);
      outputstream.flush();
      Calendar calendar = Calendar.getInstance();
      byte abyte1[] = new byte[48];
      int j = inputstream.read(abyte1);
      if(j != 48)
        throw new Exception("No response from proxy");
      Calendar calendar1 = Calendar.getInstance();
      d = getDoubleTime(calendar, d4);
      d1 = getDoubleTime(calendar1, d4);
      b1.setData(abyte1, NTPPacket.NTP_HEADER_LENGTH);
      d3 = b1.getJulianDate();
      b2.setData(abyte1, 40);
      if((long)b2.getSeconds() == 0L)
        throw new Exception("NTP Server is out of service.");
      d2 = b2.getJulianDate();
    }
    finally{
      try{
        socket.close();
      }
      catch(Exception _ex) { }
    }
    double d5 = d;
    double d6 = d3;
    double d7 = d2;
    double d8 = d1;
    SNTPCorrection sntpcorrection = new SNTPCorrection();
    sntpcorrection.setCorrection((((d6 - d5) + (d7 - d8)) / 2D) * 86400D);
    sntpcorrection.setDelay((d8 - d5 - (d6 - d7)) * 86400D);
    return sntpcorrection;
  }

  public SNTPProxy(String host, int port){
    this.host = host;
    this.port = port;
    System.out.println("SNTP API\n");
  }

  String host;
  int port;

/**  public static void main(String[] args) {
    SNTPProxy p = new SNTPProxy("",123);
    System.err.println(p.getDoubleTime(Calendar.getInstance(),8));
  }
*/
}
