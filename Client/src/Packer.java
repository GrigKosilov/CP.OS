import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Packer implements Runnable {
    private static BufferedWriter writer; // поток записи в сокет
    private final int clientID;

    Packer(Socket socket, int clientID) throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.clientID = clientID;
    }

    @Override
    public void run(){
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(2);
                //System.out.println("*Packer: " + "checking");
                try {
                    sending(TaskGenerator.generateTaskString(clientID));
                } catch (Exception e) {
                    System.err.println("*Packer: " + e);
                    sending("ENDTASK");
                    break;
                }
            }
            writer.close();
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }


    private void sending(String line) throws IOException {
        if (line==null) return;

        writer.write(line);
        writer.newLine();
        writer.flush();
    }
}
