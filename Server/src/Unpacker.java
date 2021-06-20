import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Unpacker implements Runnable {
    private static BufferedReader reader; // поток чтения из сокета
    //private static BufferedWriter writer; // поток записи в сокет
    LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory;

    Unpacker(Socket socket, LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.sharedMemory = sharedMemory;
    }


    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(1);
                //System.out.println("#Unacker: " + "checking");

                String request = reader.readLine();
                if (request == null)
                    continue;
                if (request.equals("ENDTASK")){
                    sharedMemory.peekFirst().add(new Task());
                    break;
                }

                System.out.println("#Unacker: " + "new " + request);
                //sending("Server: " + "accepted " + request);
                sharedMemory.peekFirst().add(new Task(request));
            }
            reader.close();
            //writer.close();
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }

    //private static void sending(String line) throws IOException {
    //    writer.write(line);
    //    writer.newLine();
    //    writer.flush();
    //}
}