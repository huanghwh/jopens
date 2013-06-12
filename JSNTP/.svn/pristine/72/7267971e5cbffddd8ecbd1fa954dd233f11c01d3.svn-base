
package com.kcmultimedia.proxies.sntp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Vector;

// Referenced classes of package com.kcmultimedia.proxies.sntp:
//            ProxyClientThread, Scavenger

public class ProxyServer extends Thread{

  public static void main(String args[]){
    System.out.println("SNTP Proxy Server Version 1.1\n");
    if(args.length < 2){
      System.out.println("Usage:\n\njava com.kcmultimedia.sntp.ProxyServer <NTP Server> <Local Port>");
      System.exit(1);
    }
    System.out.println("NTP Server:" + args[0]);
    System.out.println("Local Port:"+ args[1]);
    int k = 0;
    try{
      k = Integer.parseInt(args[1]);
    }
    catch(Exception _ex){
      k = 0;
    }
    finally{
      if(k <= 1024)
        System.out.println("ERROR: Invalid Port. Must be a positive integer > 1024");
    }
    new ProxyServer(args[0], k);
  }

  public ProxyServer(String host, int port){
    super("SNTP_PROXY");
    this.port = port;
    threadGroup = new ThreadGroup("CONNECTIONS");
    connections = new Vector();
    scavenger = new Scavenger(this);
    try{
      ServerSocket = new ServerSocket(port);
      address = InetAddress.getByName(host);
    }
    catch(Exception exception){
      if(exception instanceof UnknownHostException)
        System.out.println("Unknown host error:" + exception.getMessage());
      else
      if(exception instanceof IOException)
        System.out.println("Error creating ServerSocket:" + exception.getMessage());
      else
        System.out.println("\n" + exception.toString());
      System.out.println("Shutting down SNTP proxy server.");
      System.exit(1);
    }
    start();
  }

  public void run(){
    try{
      while(true){
        java.net.Socket socket = ServerSocket.accept();
        ProxyClientThread thread = new ProxyClientThread(this, socket, address, SNTP_PORT );
        synchronized(connections){
          connections.addElement(thread);
        }
      }
    }
    catch(IOException ioexception){
      System.out.println(ioexception.toString());
    }
  }


  protected Vector connections;
  InetAddress address;
  ServerSocket ServerSocket;
  int port;
  Scavenger scavenger;
  public static final int SNTP_PORT = 123;
  protected ThreadGroup threadGroup;
}
