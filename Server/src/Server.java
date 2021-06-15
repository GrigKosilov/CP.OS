import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Server {
    private static Socket socket; //сокет для общения
    private static ServerSocket server; // серверсокет


    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Server started");
                socket = server.accept();
                LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory= new LinkedBlockingDeque<>(); //разделяемая память
                sharedMemory.add(new PriorityBlockingQueue<Task>());    sharedMemory.add(new PriorityBlockingQueue<Task>());

                //new Thread(new Unpacker(socket, sharedMemory)).start();
                new Thread(new Packer(socket, sharedMemory)).start();
                //new Thread(new Handler(sharedMemory)).start();
            } finally {
                System.out.println("Сервер закрыт!");
                //server.close();
                //socket.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
