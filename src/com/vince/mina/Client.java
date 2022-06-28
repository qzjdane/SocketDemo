package com.vince.mina;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        NioSocketConnector connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        //chain.addLast("myChin",new ProtocolCodecFilter(new TextLineCodecFactory()));
        chain.addLast("objectFilter",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        connector.setHandler(new MinaClientHandler());
        connector.setConnectTimeoutMillis(10000);

        ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", 9999));
        cf.awaitUninterruptibly();

        Scanner input = new Scanner(System.in);
        while (true){
//            System.out.println("请输入：");
//            String info = input.nextLine();
//
//            cf.getSession().write(info);
            Message msg = new Message();
            System.out.println("from");
            msg.setFrom(input.nextLine());
            System.out.println("to:");
            msg.setTo(input.nextLine());
            System.out.println("info:");
            msg.setInfo(input.nextLine());
            msg.setType("send");

            cf.getSession().write(msg);
        }

    }
}
