import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Unpacker implements Runnable{
    private static BufferedReader reader; // поток чтения из сокета
    private static BufferedWriter writer; // поток записи в сокет
    LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory;

    Unpacker (Socket socket, LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.sharedMemory=sharedMemory;
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

                    System.out.println("Read task " + request);
                    sharedMemory.peekFirst().add(new Task(request));
                    sending("Команда '" + request + "' принята");
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