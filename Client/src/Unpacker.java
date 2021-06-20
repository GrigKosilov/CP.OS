import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Unpacker implements Runnable {
    private static BufferedReader reader; // поток чтения из сокета

    Unpacker(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(1);
                //System.out.println("#Unpacker: " + "checking");

                String request = reader.readLine();
                if (request == null)
                    continue;
                if (request.equals("ENDTASK"))
                    break;

                System.out.println("#Unpacker: " + "Server answer: " + reader.readLine());
            }
            reader.close();
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }
}