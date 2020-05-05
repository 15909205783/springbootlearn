package com.yang.io.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ThreadEchoHandler implements Runnable {
    private Socket socket;

    public ThreadEchoHandler(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner in = new Scanner(inputStream, "utf-8");
            PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "utf-8"), true);
            out.println("Hello! Enter BYE to exit");
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                out.println("ECHO:" + line);
                if (line.trim().equals("BYE"))
                    done = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
