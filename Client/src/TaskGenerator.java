import java.util.Random;

public class TaskGenerator {
    private static final int probability = 25;
    private static final Random random = new Random();

    private static void generateEndTask() throws Exception {
        if (random.nextInt(1000) == 666)
            throw new Exception("End task was generated.");
    }

    private static Task generate(int clientID) throws Exception {
        generateEndTask();

        if (random.nextInt(100) < probability)
            return new Task(clientID, random.nextInt(), random.nextInt());
        else
            return null;
    }

    public static String generateTaskString(int clientID) throws Exception {
        Task task = TaskGenerator.generate(clientID);

        if (task != null) {
            System.out.println("+TaskGenerator: " + "New: " + task);
            return task.toString();
        } else {
            //System.out.println("+TaskGenerator: " + "No new task");
            return null;
        }
    }
}
