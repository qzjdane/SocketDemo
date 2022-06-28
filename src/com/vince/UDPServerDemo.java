package com.vince;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPServerDemo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入需要传送的数据：");
        String info = input.nextLine();
        byte[] bytes = info.getBytes();
        try{
            //封装一个数据包
            DatagramPacket dp = new DatagramPacket(
                    bytes,
                    0,
                    bytes.length,
                    InetAddress.getByName("127.0.0.1"),
                    8000);
            DatagramSocket socket = new DatagramSocket(9000);
            socket.send(dp);

        }catch (UnknownHostException | SocketException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
