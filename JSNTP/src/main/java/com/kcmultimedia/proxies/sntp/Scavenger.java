
package com.kcmultimedia.proxies.sntp;

import java.util.Calendar;

// Referenced classes of package com.kcmultimedia.proxies.sntp:
//            ProxyClientThread, ProxyServer

class Scavenger extends Thread{

  Scavenger(ProxyServer proxyserver){
    super(proxyserver.threadGroup, "Scavenger");
    proxyServer = proxyserver;
    start();
  }

  public synchronized void run(){
    do{
      try{
        wait(5000L);
      }
      catch(InterruptedException _ex) { }
      synchronized(proxyServer.connections){
        for(int i = 0; i < proxyServer.connections.size(); i++){
          ProxyClientThread client = (ProxyClientThread)proxyServer.connections.elementAt(i);
          if(!client.isAlive() || client.isOverAge(Calendar.getInstance())){
            proxyServer.connections.removeElementAt(i);
            i--;
          }
        }

      }
    } while(true);
  }


  private ProxyServer proxyServer;
  static final String scavenger = "Scavenger";

}
