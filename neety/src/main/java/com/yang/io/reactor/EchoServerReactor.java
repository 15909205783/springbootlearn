package com.yang.io.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServerReactor implements Runnable {
    Selector selector;
    ServerSocketChannel serverSocket;

    public EchoServerReactor() throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8189);
        serverSocket.socket().bind(address);
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        //attach callback object, AcceptorHandler
        sk.attach(new AcceptorHandler());
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    //Reactor负责dispatch收到的事件
                    SelectionKey sk = it.next();
                    dispatch(sk);
                }
                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void dispatch(SelectionKey sk) {
        Runnable handler = (Runnable) sk.attachment();
        //调用之前attach绑定到选择键的handler处理器对象
        if (handler != null) {
            handler.run();
        }
    }
    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel != null) {
                    new EchoHandler(selector, channel).run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public static void main(String[] args) throws IOException {
        new Thread(new EchoServerReactor()).start();
    }
}
