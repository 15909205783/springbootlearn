package com.yang.io.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class EchoClient {

    public void start() throws IOException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8189);
        SocketChannel socketChannel = SocketChannel.open(address);
        socketChannel.configureBlocking(false);
        while (!socketChannel.finishConnect()) {
        }
        System.out.println("客户端启动成功！");
        //启动线程
        //启动接受线程
        Processer processer = new Processer(socketChannel);
        new Thread(processer).start();
    }

    static class Processer implements Runnable {
        final Selector selector;
        final SocketChannel channel;

        public Processer(SocketChannel channel) throws IOException {
            selector = Selector.open();
            this.channel = channel;
            channel.register(selector,
                    SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        SelectionKey sk = it.next();
                        if (sk.isWritable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("请输入发送内容:");
                            if (scanner.hasNext()) {
                                SocketChannel socketChannel = (SocketChannel) sk.channel();
                                String next = scanner.next();
                                next.getBytes();
                                buffer.flip();
                                // 操作三：通过DatagramChannel数据报通道发送数据
                                socketChannel.write(buffer);
                                buffer.clear();
                            }
                            if (sk.isReadable()) {
                                //若选择键的IO是可读的，则读取数据
                                SocketChannel socketChannel = (SocketChannel) sk.channel();
                                //读取数据
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                int length = 0;
                                while ((length = socketChannel.read(byteBuffer)) > 0) {
                                    byteBuffer.flip();
                                    System.out.println(("server echo:" + new String(byteBuffer.array(), 0, length)));
                                    byteBuffer.clear();
                                }
                            }
                        }
                    }
                    selected.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoClient().start();
    }
}
