import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Packer implements Runnable {
    private static BufferedWriter writer; // поток записи в сокет
    LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory;

    Packer(Socket socket, LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory) throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.sharedMemory = sharedMemory;
    }

    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(1);
                //System.out.println("*Packer: " + "checking");

                if (!sharedMemory.peekLast().isEmpty()){
                    Task taskBuf = sharedMemory.peekLast().remove();
                    if (taskBuf.priority == Integer.MAX_VALUE)
                        break;

                    sending("Server: " + "done ID " + taskBuf.taskIDToString());
                }
            }
            sending("Server: " + "done with all of them");
            writer.close();
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }

    private static void sending(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
    }
}
