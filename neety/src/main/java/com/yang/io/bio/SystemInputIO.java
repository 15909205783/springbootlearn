package com.yang.io.bio;

import java.io.*;

public class SystemInputIO {
    public static void main(String[] args) throws IOException {
//        keyInAndPrintConsole();
//        readAndWriteByteToFile();
        readAndWriteCharToFile();
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
}
