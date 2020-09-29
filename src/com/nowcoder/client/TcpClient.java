package com.nowcoder.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpClient {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
//        try {
//            Socket socket = new Socket("127.0.0.1", 9000);
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String line = br.readLine();
//            System.out.println(line);
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            Socket socket = new Socket("127.0.0.1", 9000);
            threadPool.submit(new ReadTask(socket));
            threadPool.submit(new WriteTask(socket));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class ReadTask implements Runnable {
    private Socket socket;
    private BufferedReader reader;

    public ReadTask(Socket socket) {
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class WriteTask implements Runnable {

    private Socket socket;
    private PrintStream writer;

    public WriteTask(Socket socket) {
        this.socket = socket;
        try {
            writer = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String line;
        while ((line = scanner.nextLine()) != null) {
            writer.println(line);
        }
    }
}