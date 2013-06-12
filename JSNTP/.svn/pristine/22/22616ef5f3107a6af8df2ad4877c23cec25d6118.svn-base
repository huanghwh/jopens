package com.kcmultimedia.proxies.sntp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;

// Referenced classes of package com.kcmultimedia.proxies.sntp:
//            ProxyServer, Scavenger

class ProxyClientThread extends Thread {

  ProxyClientThread(ProxyServer proxyserver, Socket socket, InetAddress inetaddress, int port){
    super(proxyserver.threadGroup, "SNTP" + nSum);
    this.proxyserver = proxyserver;
    Socket = socket;
    this.inetaddress = inetaddress;
    this.port = port;
    num = nSum++;
    if(nSum > 9999)
      nSum = 1;
    Calendar calendar = Calendar.getInstance();
    f = getDoubleTime(calendar, 0.0D);
    try{
      dataSocket = new DatagramSocket();
    }
    catch(Exception _ex){
      return;
    }
    start();
  }

  private static double getDoubleTime(Calendar calendar, double d1){
    int k = calendar.get(2) + 1;
    int l = calendar.get(1);
    int i1 = calendar.get(5);
    int j1 = calendar.get(11);
    int k1 = calendar.get(12);
    double d2 = calendar.get(13);
    d2 += (double)calendar.get(14) / 1000D;
    if(k <= 2){
      l--;
      k += 12;
    }
    double d3 = l / 100;
    double d4 = (2D - d3) + (double)(int)(d3 / 4D);
    double d5 = (double)((int)(365.25D * (double)l) + (int)(30.600100000000001D * (double)(k + 1)) + i1) + 1720994.5D + d4;
    d5 = d5 + ((double)j1 - d1) / 24D + (double)k1 / 1440D + d2 / 86400D;
    return d5;
  }

  public boolean isOverAge(Calendar calendar){
    if(!isAlive())
      return true;
    double d1 = getDoubleTime(calendar, 0.0D);
    if(d1 - f > MAX_PROXY_TIME){
      bStop = true;
      try{
        dataSocket.close();
        Socket.close();
      }
      catch(Exception _ex) { }
      return true;
    }
    else{
      return false;
    }
  }

  public void run(){
    try{
      InputStream inputstream = Socket.getInputStream();
      OutputStream outputstream = Socket.getOutputStream();
      byte abyte0[] = new byte[48];
      inputstream.read(abyte0);
      DatagramPacket datagrampacket = new DatagramPacket(abyte0, abyte0.length, inetaddress, port);
      dataSocket.send(datagrampacket);
      dataSocket.receive(datagrampacket);
      byte abyte1[] = datagrampacket.getData();
      if(bStop)
        return;
      outputstream.write(abyte1);
      outputstream.flush();
    }
    catch(Exception _ex) { }
    finally{
      try{
        dataSocket.close();
        Socket.close();
      }
      catch(Exception _ex) { }
    }
    synchronized(proxyserver.scavenger){
      proxyserver.scavenger.notify();
    }
  }


  static final double MAX_PROXY_TIME = 0.00069999999999999999D;
  Socket Socket;
  DatagramSocket dataSocket;
  int num;
  static int nSum = 1;
  public double f;
  ProxyServer proxyserver;
  InetAddress inetaddress;
  int port;
  boolean bStop = false;

}
