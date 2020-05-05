package com.yang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
    //通道管理器
    private Selector selector;

    /**
     * 获得一个serverSocket通道，并对该通道做一些初始化的工作
     *
     * @param port 绑定的端口号
     * @throws IOException
     */
    public void initServer(int port) throws IOException {
        //获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //设置为非阻塞
        serverChannel.configureBlocking(false);
        //将该通道对应的ServerSocket绑定到port端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        //获得一个通道管理器
        this.selector = Selector.open();
        //将通道管理器和通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件后
        //当该事件到达时，selector.select()会返回，如果该事件没有调用selector.select会一直阻塞
        //reark：当有客户端连接8000这个端口时，将这个chanel上的所有初始连接都设置为OP_ACCEPT
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     */
    public void listen() throws IOException {
        System.out.println("服务端启动成功");
        //轮询访问selector
        while (true) {
            //当注册事件到达时（有新的连接或连接传输数据通过8000端口），方法返回，否则，该方法会一直阻塞，调用操作系统的底层东西
            selector.select();
            //select方法返回后会返回一个selectKeys
            //获得selector中选中项的迭代器，选中的项为注册事件
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                //channel绑定的key
                SelectionKey key = (SelectionKey) ite.next();
                //删除已选的key，以防重复处理
                ite.remove();
                //客户端请求连接事件
                //第一次客户端创建连接事件会将是accept，即init里面的OP_ACCEPT
                if (key.isAcceptable()) {
                    handleAccept(key);
                } else if (key.isReadable()) {
                    handleRead(key);
                }
            }
        }
    }

    /**
     * 处理可读的服务器通道
     *
     * @param key SelectionKey
     * @throws IOException
     */
    public void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        //获得和客户端的连接通道
        SocketChannel channel = server.accept();
        //设置为非阻塞的
        channel.configureBlocking(false);
        //telnet输出了这句话表示已经和服务器连接上了
        channel.write(ByteBuffer.wrap("send message to server:".getBytes()));
        //在和客户端连接成功后，为了可以接受客户端的信息，需要给通道设置读的权限
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    public void handleRead(SelectionKey key) throws IOException {
        //服务器可读消息，得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        //创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("服务端收到消息：" + msg);
        ByteBuffer outBuffer = ByteBuffer.wrap("ok".getBytes());
        //将消息会送给客户端
        channel.write(outBuffer);
    }

    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.initServer(8000);
        server.listen();
    }
}
