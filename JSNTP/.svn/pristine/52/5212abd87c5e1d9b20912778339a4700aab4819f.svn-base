package com.kcmultimedia.sntp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPBase{

  public UDPBase(String hostName, int port, int len)
      throws Exception{
    this.port = port;
    this.length = len;
    address = InetAddress.getByName(hostName);
    dataSocket = new DatagramSocket();
  }

  public byte[] getPacket(byte[] buf)
      throws Exception{
    DatagramPacket datagrampacket = new DatagramPacket(new byte[length], length);
    DatagramPacket datagrampacket1 = new DatagramPacket(buf, buf.length, address, port);
    dataSocket.send(datagrampacket1);
    dataSocket.receive(datagrampacket);
    return datagrampacket.getData();
  }

  DatagramSocket dataSocket;
  InetAddress address;
  DatagramPacket dataPacket;
  int length;
  int port;
}
