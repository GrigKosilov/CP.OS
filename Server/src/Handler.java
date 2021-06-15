import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Handler implements Runnable {
    LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory;

    Handler(LinkedBlockingDeque<PriorityBlockingQueue<Task>> sharedMemory) {
        this.sharedMemory = sharedMemory;
    }

    private boolean isOn = true;

    @Override
    public void run() {
        try {
        while (isOn) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Сервер: проверяю обработку");
            if (!sharedMemory.peekFirst().isEmpty()) {
                Task taskBuf = sharedMemory.peekFirst().remove();
                TimeUnit.SECONDS.sleep(taskBuf.time);
                taskBuf.time=0;
                sharedMemory.peekLast().add(taskBuf);
            }
        }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*
    private static int lastId = -1;
    private final int id;
    private boolean isOn;
    private Handler nextHandler;
    public ConcurrentLinkedQueue<Task> queue = new ConcurrentLinkedQueue<>();
    public static ConcurrentLinkedQueue<Task> sharedMemory = new ConcurrentLinkedQueue<>();

    public Handler(){
        lastId++;
        this.id = lastId;
        this.isOn = true;
    }

    public int getIdHandler(){
        return id;
    }

    public boolean getIsOn(){
        return this.isOn;
    }

    public void setOff(){
        this.isOn = false;
    }


    public ConcurrentLinkedQueue<Task> getSharedMemory(){
        return sharedMemory;
    }

    public void setNextHandler(Handler nextHandler){
        this.nextHandler = nextHandler;
    }

    public void processing(Letter letter){
        if(!letter.getIsProcessed()){
            letter.setLetter(Character.toUpperCase(letter.getLetter()));
            letter.setIsProcessed();
        }
    }

    public void run() {
       while (isOn) {
           while (!this.queue.isEmpty()) {
               Task task = queue.poll();
               if (task.getCount() < task.getLetters().length) {
                   this.processing(task.getLetter(task.getCount()));
                   task.changeCount();
                   System.out.println("Обработчик №" + this.getIdHandler() + ": " + task.getCommand());
                   nextHandler.queue.offer(task);
               } else {
                   sharedMemory.offer(task);
               }
           }
       }
    }
 */
