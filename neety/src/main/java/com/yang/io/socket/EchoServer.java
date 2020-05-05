package com.yang.io.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket s = new ServerSocket(8189)) {
            try (Socket socket = s.accept()) {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                try (Scanner in = new Scanner(inputStream, "utf-8")) {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "utf-8"), true);
                    out.println("Hello!Enter BYE to exit.");
                    boolean done = false;
                    while (!done && in.hasNextLine()) {
                        String line = in.nextLine();
                        out.println("ECHO:" + line);
                        if (line.trim().equals("BYE")) done = true;
                    }
                }
            }
        }
    }
}
