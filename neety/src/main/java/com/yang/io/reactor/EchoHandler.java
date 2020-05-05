package com.yang.io.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class EchoHandler implements Runnable {
    final SocketChannel channel;
    final SelectionKey sk;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    static final int REVIEVING = 0, SENDING = 1;
    int state = REVIEVING;

    public EchoHandler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        c.configureBlocking(false);
        //仅仅取得选择键，后设置感兴趣的IO事件
        sk = channel.register(selector, 0);
        //将Handler作为选择键的附件
        sk.attach(this);
        //第二步,注册Read就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == SENDING) {
                //写入通道
                channel.write(byteBuffer);
                //写完后，准备开始从通道读，byteBuffer切换为写模式
                byteBuffer.clear();
                //写完后,注册read就绪事件
                sk.interestOps(SelectionKey.OP_READ);
                //写完后,进入接收的状态
                state = REVIEVING;
            } else if (state == REVIEVING) {
                //从通道中读取
                int length = 0;
                while ((length = channel.read(byteBuffer)) > 0) {
                    System.out.println((new String(byteBuffer.array(), 0, length)));
                }
                //读完后，准备开始写入通道,byteBuffer切换成读模式
                byteBuffer.flip();
                //读完后，注册write就绪事件
                sk.interestOps(SelectionKey.OP_WRITE);
                //读完后,进入发送的状态
                state = SENDING;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
