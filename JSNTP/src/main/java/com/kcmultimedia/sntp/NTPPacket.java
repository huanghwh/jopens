package com.kcmultimedia.sntp;


class NTPPacket{

  public NTPPacket(){
    msg = new long[8];
    for(int i = 0; i < 8; i++)
      msg[i] = 0L;

  }

  public NTPPacket(byte abyte0[], int i){
    this();
    setData(abyte0, i);
  }

  private double a(){
    long l = 0L;
    int i = 4;
    for(int j = 3; i < 8; j--){
      l |= msg[i] << j * 8;
      i++;
    }
    return (double)l / 4294967296D;
  }

  private long b(){
    long l = 0L;
    int i = 0;
    for(int j = 3; i < 4; j--){
      l |= msg[i] << j * 8;
      i++;
    }
    return l;
  }

  public double getJulianDate(){
    return JOffset + getSeconds() / 86400D;
  }

  public double getSeconds(){
    String.valueOf(b());
    return (double)b() + a();
  }

  public void setData(byte abyte0[], int idx){
    int j = idx;
    for(int k = 0; k < 8; k++){
      msg[k] = abyte0[j] & 0xff;
      j++;
    }
  }

  private long msg[];
  public static final String c = "\nSNTP API\n";
  private final double JOffset = 2415020.5D;
  public static final int NTP_HEADER_LENGTH = 32;
  public static final int f = 52;
  public static final int g = 40;
}
