import java.io.*;
import java.net.Socket;

public class Client {
    static int lastClientID = 1;

    public static void main(String[] args) {
        final int clientID = lastClientID++;
        try {
            //сокет для общения
            Socket socket = new Socket("localhost", 4004);
            new Thread(new Packer(socket, clientID)).start();
            new Thread(new Unpacker(socket)).start();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
