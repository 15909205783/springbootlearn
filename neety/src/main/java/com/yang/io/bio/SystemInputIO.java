package com.yang.io.bio;

import java.io.*;

public class SystemInputIO {
    public static void main(String[] args) throws IOException {
//        keyInAndPrintConsole();
//        readAndWriteByteToFile();
//        readAndWriteCharToFile();
//        piped();
        dataInputStream();
    }

    public static void keyInAndPrintConsole() throws IOException {
        PrintWriter out = null;
        BufferedReader br = null;
        try {
            System.out.println("请输入：");
            out = new PrintWriter(System.out, true);
            br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.equals("exit")) {
                    System.exit(1);
                }
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            br.close();
        }
    }

    /**
     * 文件输入流
     *
     * @throws IOException
     */
    public static void readAndWriteByteToFile() throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            File file = new File("E:\\FileOutputStream.txt");
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
            }
            is = new FileInputStream("E:\\fileInputStream.txt");
            os = new FileOutputStream(file);
            byte[] buf = new byte[4];
            int hashRead = 0;
            while ((hashRead = is.read(buf)) > 0) {
                os.write(buf, 0, hashRead);
            }
            System.out.println("write success");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
            is.close();
        }
    }

    public static void readAndWriteCharToFile() throws IOException {
        Reader reader = null;
        Writer writer = null;
        //https://www.cnblogs.com/CQqf/p/10795656.html
        try {
            File readFile = new File("E:\\fileInputStream.txt");
            reader = new FileReader(readFile);
            File writeFile = new File("E:\\FileOutputStream.txt");
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeFile), "UTF-8"));
            char[] byteArray = new char[(int) readFile.length()];
            int size = reader.read(byteArray);
            System.out.println("大小:" + size + "个字符;内容:" + new String(byteArray));
            writer.write(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
    }

    public static void piped() throws IOException {
        final PipedOutputStream output = new PipedOutputStream();
        final PipedInputStream input = new PipedInputStream(output);
        Thread thread1 = new Thread(() -> {
            try {
                output.write("Hello world, piped!".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                int data = input.read();
                while (data != -1) {
                    System.out.println((char) data);
                    data = input.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
    }

    public static void dataInputStream() throws IOException {
        File fileIn = new File("e:\\test.txt");
        File fileOut = new File("e:\\out.txt");
        if (!fileOut.exists()) {
            fileOut.createNewFile();
        }
        FileInputStream fileInputStream = new FileInputStream(fileIn);
        DataInputStream in = new DataInputStream(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(fileOut);
        DataOutputStream out = new DataOutputStream(fileOutputStream);
        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        String count;
        while ((count = d.readLine()) != null) {
            String u = count.toUpperCase();
            System.out.println(u);
            out.writeBytes(u + ",");
        }
        d.close();
        out.close();
    }
}
