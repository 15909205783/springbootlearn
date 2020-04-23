package com.yang.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNIOCopyDemo {
    public static void main(String[] args) {

    }

    public static void nioCopyResourceFile() {
        String sourcePath;
    }

    public static void nioCopyFile(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (!destFile.exists()) {
            try {
                destFile.createNewFile();
                long startTime = System.currentTimeMillis();
                FileInputStream fis = null;
                FileOutputStream fos = null;
                FileChannel inChannel = null;
                FileChannel ouChannel = null;
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                inChannel = fis.getChannel();
                ouChannel = fos.getChannel();
                int length = -1;
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while ((length = inChannel.read(buffer)) != -1) {
                    buffer.flip();
                    int outlength = 0;
                    while ((outlength = ouChannel.read(buffer)) != 0) {
                        System.out.println("写入的字节数："+outlength);
                    }
                    buffer.clear();
                }
                ouChannel.force(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
