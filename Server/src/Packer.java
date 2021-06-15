import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Packer implements Runnable{
    private static BufferedWriter writer; // поток записи в сокет
    LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory;

    Packer (Socket socket, LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory) throws IOException {
        //reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.sharedMemory=sharedMemory;
    }
    private boolean isOn=true;
    @Override
    public void run() {
        try {
            try {
                generationEmulation();
                while(isOn){
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Сервер: проверяю отправки");
                    if(!sharedMemory.peekLast().isEmpty())
                        sending(sharedMemory.peekLast().remove().taskIDToString());
                }
            } finally {
                //writer.close();
            }
        }
        catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }
    private  void generationEmulation(){
        int i=10;
        while(i-->0){
            sharedMemory.peekLast().add(new Task("Task{" +
                    "clientID=" + 999 +
                    ", taskID=" + (2000+i) +
                    ", time=" + 0 +
                    ", priority=" + i +
                    '}'));
        }
    }
    private static void sending(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
    }
}
