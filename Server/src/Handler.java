import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Handler implements Runnable {
    LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory;

    Handler(LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory) {
        this.sharedMemory = sharedMemory;
    }

    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(1);
                //System.out.println("%Handler: " + "checking");

                if (!sharedMemory.peekFirst().isEmpty()) {
                    Task taskBuf = sharedMemory.peekFirst().remove();
                    if (taskBuf.priority == Integer.MAX_VALUE)
                        break;

                    System.out.println("    %Handler: " + "start: " + taskBuf);
                    TimeUnit.SECONDS.sleep(taskBuf.time);
                    taskBuf.time = 0;
                    System.out.println("    %Handler: " + "done: " + taskBuf);
                    sharedMemory.peekLast().add(taskBuf);
                }
            }
            sharedMemory.peekLast().add(new Task());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}