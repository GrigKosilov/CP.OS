import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Packer implements Runnable{
    private static BufferedWriter writer; // поток записи в сокет
    private final int clientID;

    Packer (Socket socket, int clientID) throws IOException {
        //reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.clientID=clientID;
    }

    private boolean isOn=true;
    @Override
    public void run() {
        try {
            try {
                while(isOn){
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Клиент: проверяю отправки");
                    taskGeneration();
                }
            } finally {
                writer.close();
            }
        }
        catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }
    private void taskGeneration() throws IOException {
        System.out.print("*Task Generation* ");
        Task task = TaskGenerator.generate(clientID);
        if (task != null) {
            System.out.println("Task is: " + task);
            sending(task.toString());
        } else
            System.out.println("No task generated");
    }
    private void sending(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
    }
}
