package com.nowcoder.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UdpClient {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            // 注册
            DatagramPacket packet = new DatagramPacket(
                    new byte[]{1}, 1, InetAddress.getByName("127.0.0.1"), 9001);
            socket.send(packet);
            // 接收
            threadPool.submit(new ReceiveTask(socket));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class ReceiveTask implements Runnable {

    private DatagramSocket socket;

    public ReceiveTask(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(packet);
                String line = new String(packet.getData(), 0, packet.getLength());
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}