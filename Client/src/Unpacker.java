import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Unpacker implements Runnable{
    private static BufferedReader reader; // поток чтения из сокета
    private static BufferedWriter writer; // поток записи в сокет

    Unpacker (Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private boolean isOn=true;
    @Override
    public void run() {
        try {
            try {
                while(isOn){
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Сервер: проверяю входящие");
                    String request = reader.readLine();
                    if (request == null)
                        continue;

                    if (!request.equals("NO TASK"))
                        break;

                    System.out.println("Server answer is: " + reader.readLine());
                }
            } finally {
                //reader.close();
                //writer.close();
            }
        }
        catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }
    private static void sending(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
    }
}