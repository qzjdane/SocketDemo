package com.vince.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器端
 */
public class Server {
    public static void main(String[] args) {
        Vector<UserThread> vector = new Vector<>();
        ExecutorService es = Executors.newFixedThreadPool(5);

        try {
            ServerSocket server = new ServerSocket(8888);
            System.out.println("服务器已启动！");
            while (true){
                Socket socket = server.accept();
                UserThread user  = new UserThread(socket,vector);
                es.execute(user);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

/**
 * 客户端处理线程
 */

class UserThread implements Runnable{
    private String name;
    private  Socket socket;
    private Vector<UserThread> vector;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean flag =true;

    public UserThread(Socket socket,Vector<UserThread> vector){
        this.socket = socket;
        this.vector = vector;
        vector.add(this);
    }
    @Override
    public void run() {
        try {
            System.out.println("客户端"+socket.getInetAddress().getHostAddress()+"已连接");
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            while (flag){
                //读取消息对象
                Message msg = (Message) ois.readObject();
                int type = msg.getType();
                switch (type){
                    case MessageType.TYPE_LOGIN:
                        name = msg.getFrom();
                        msg.setInfo("欢迎您：");
                        oos.writeObject(msg);
                        break;

                    case MessageType.TYPE_SEND:
                        String to = msg.getTo();
                        UserThread ut;
                        int size = vector.size();
                        for (int i = 0; i < size; i++) {
                            ut = vector.get(i);
                            if(to.equals(ut.name) && ut!=this){
                                ut.oos.writeObject(msg);
                                break;
                            }
                        }
                        break;
                }
            }
            ois.close();
            oos.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}