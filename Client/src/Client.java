import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;

public class Client {
    static int lastClientID = 1;
    private static Socket socket; //сокет для общения
    private static BufferedReader reader; // поток чтения из сокета
    private static BufferedWriter writer; // поток записи в сокет
    private final static int countOfConnections = 50;

    public static void main(String[] args) {
        final int clientID = lastClientID++;
            try {
                try {
                    socket = new Socket("localhost", 4004);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                   // new Thread(new Packer(socket, clientID)).start();
                    new Thread(new Unpacker(socket)).start();
                } finally {
                    System.out.println("Client terminated");
                    //socket.close();
                    //reader.close();
                    //writer.close();
                }
            } catch (IOException e) {
                System.err.println(e);
            }
    }



    static void sending(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
    }
}
