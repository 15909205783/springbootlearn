package com.yang.io;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NioSendClient {
    private Charset charset = Charset.forName("utf-8");

    public void sendFile() throws Exception{
        String sourcePath = "";
        String srcPath ="";
        String destFile = "";
        File file = new File(srcPath);
        if (!file.exists()){
            System.out.println("文件不存在");
            return;
        }
        FileChannel fileChannel = new FileInputStream(file).getChannel();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.socket().connect(new InetSocketAddress("127.0.0.1",8080));
        socketChannel.configureBlocking(false);
        while (!socketChannel.finishConnect()){

        }
        System.out.println("成功连接服务器");
        ByteBuffer fileName = charset.encode(destFile);
        socketChannel.write(fileName);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putLong(file.length());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        int lenght =0;
        long progress = 0;
        while ((lenght=fileChannel.read(buffer))>0){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            progress+=lenght;
        }
        if (lenght==-1){
            socketChannel.shutdownOutput();

        }
    }
}
