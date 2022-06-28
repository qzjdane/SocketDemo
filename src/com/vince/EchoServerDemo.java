package com.vince;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerDemo {
    public static void main(String[] args) {
        //创建一个服务器的Socket
        try {
            ServerSocket server = new ServerSocket(6666);
            System.out.println("服务器已启动，正在等待客户端链接。。。。");
            //等待客户端连接，造成阻塞，如果有，返回对象。
            Socket socket = server.accept();
            System.out.println("客户端连接成功"+server.getInetAddress().getHostAddress());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            //通过输入流读取网络数据
            String info = br.readLine();
            System.out.println(info);
            //向客户端输出返回消息
            PrintStream ps = new PrintStream(
                    new BufferedOutputStream(socket.getOutputStream()));
            ps.println("echo:"+info);
            ps.flush();
            //关闭流
            ps.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
