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

    public Task() {
        clientID=0;
        taskID=0;
        time=0;
        priority=Integer.MAX_VALUE;
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
        return Integer.compare(this.priority, other.priority);
    }
}