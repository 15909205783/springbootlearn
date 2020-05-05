package com.yang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {
    //通道管理器
    private Selector selector;

    /**
     * 获得一个socket通道，并对该通道做一些初始化的工作
     *
     * @param ip
     * @param port
     * @throws IOException
     */
    public void initClient(String ip, int port) throws IOException {
        //获得一个Socket通道
        SocketChannel channel = SocketChannel.open();
        //设置通道为非租塞
        channel.configureBlocking(false);
        //获得一个通道管理器
        this.selector = Selector.open();
        //客户端连接服务器，其实并没有实现连接，需要listen()方法中调
        // 用channel.finishConnect();才完成连接
        channel.connect(new InetSocketAddress(ip, port));
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws IOException {
        //轮询访问selector
        while (true) {
            //阻塞在这里直到消息到达
            selector.select();
            //获得Selector中选中的迭代器
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                //删除已选的key，以防重复处理
                ite.remove();
                //连接事件发生
                if (key.isConnectable()) {
                    //获得连接的通道
                    SocketChannel channel = (SocketChannel) key.channel();
                    //如果正在连接，则完成连接
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    //设置成非阻塞的
                    channel.configureBlocking(false);
                    //在这里可以给服务器发送消息哦
                    channel.write(ByteBuffer.wrap(new String("向服务器发送了一条消息").getBytes()));
                    channel.register(this.selector, SelectionKey.OP_READ);
                    //获得了可读事件
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    public void read(SelectionKey key) throws IOException {
        //服务器可读消息，得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        //创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("服务端收到消息：" + msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        //将消息会送给客户端
        channel.write(outBuffer);
    }

    /**
     * 启动客户端测试
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        NIOClient client = new NIOClient();
        client.initClient("localhost", 8000);
        client.listen();
    }
}
