import java.util.Arrays;

public class Task implements Comparable<Task> {
    private final int clientID;
    private final int taskID;
    public int time;
    public final int priority;

    public Task(String data) {
        String dataBuf = data.substring(data.indexOf("=")+1);
        data=dataBuf.trim();
        clientID=Integer.parseInt(dataBuf.substring(0, data.indexOf(",")).trim());
        dataBuf = data.substring(data.indexOf("=")+1);
        data=dataBuf.trim();
        taskID=Integer.parseInt(dataBuf.substring(0, data.indexOf(",")).trim());
        dataBuf = data.substring(data.indexOf("=")+1);
        data=dataBuf.trim();
        time=Integer.parseInt(dataBuf.substring(0, data.indexOf(",")).trim());
        dataBuf = data.substring(data.indexOf("=")+1);
        data=dataBuf.trim();
        priority=Integer.parseInt(dataBuf.substring(0, data.indexOf("}")).trim());
    }

    @Override
    public String toString() {
        return "Task{" +
                "clientID=" + clientID +
                ", taskID=" + taskID +
                ", time=" + time +
                ", priority=" + priority +
                '}';
    }
    public String taskIDToString(){
        return "Task " + taskID;
    }

    @Override
    public int compareTo(Task other) {
        if (this.priority == other.priority) {
            return 0;
        } else if (this.priority < other.priority) {
            return -1;
        } else {
            return 1;
        }
    }
}

/*
    private Letter[] command;
    private boolean block; // блокировка, выставляемая обработчиком
    private int count; // количество обработанных символов

    public Task(String word){
        this.command = new Letter[word.length()];
        for (int i = 0; i < word.length(); i++){
            this.command[i] = new Letter(word.charAt(i));
        }
        this.block = false;
        this.count = 0;
    }


 */
