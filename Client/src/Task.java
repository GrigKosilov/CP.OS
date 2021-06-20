public class Task {
    static int lastTaskID = 1000;
    private final int clientID;
    private final int taskID;
    public int time;
    public final int priority;

    public Task(int clientID, int time, int priority) {
        this.taskID=lastTaskID++;
        this.clientID=clientID;
        this.time=Math.abs(time%15)+1;
        this.priority=Math.abs(priority%10);
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
}
