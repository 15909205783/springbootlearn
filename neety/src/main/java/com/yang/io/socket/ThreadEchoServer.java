package com.yang.io.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadEchoServer {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try (ServerSocket s = new ServerSocket(8189)) {
            int i = 1;
            while (true) {
                Socket socket = s.accept();
                System.out.println("Spawning" + i);
                Runnable r = new ThreadEchoHandler(socket);
                executor.execute(r);
                i++;
            }
        }
    }
}
