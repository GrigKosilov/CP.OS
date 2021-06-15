import java.util.Random;

public class TaskGenerator {
    private static final int probability = 25;
    public static Task generate(int clientID){
        Random random = new Random();

        if (random.nextInt(100) < probability)
            return new Task(clientID, random.nextInt(), random.nextInt());
        else
            return null;
    }
}
